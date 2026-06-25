class Plateau {
    public static final int TAILLE = 9;
    private Case[][] grille;

    public static final int[] DEFAUT_LIGNE1 = {4, 7, 1, 145, 150, 146, 1, 7, 4};
    public static final int[] DEFAUT_LIGNE2 = {6, 9, 3, 149, 131, 149, 3, 9, 6};
    public static final int[] DEFAUT_LIGNE3 = {5, 8, 2, 148, 130, 148, 2, 8, 5};

    public Plateau() {
        this(DEFAUT_LIGNE1, DEFAUT_LIGNE2, DEFAUT_LIGNE3,
             DEFAUT_LIGNE1, DEFAUT_LIGNE2, DEFAUT_LIGNE3);
    }

    public Plateau(int[] vertLigne1, int[] vertLigne2, int[] vertLigne3,
                   int[] rougeLigne1, int[] rougeLigne2, int[] rougeLigne3) {
        grille = new Case[TAILLE][TAILLE];
        for (int i = 0; i < TAILLE; i++) {
            for (int j = 0; j < TAILLE; j++) {
                grille[i][j] = new Case();
            }
        }
        initialiser(vertLigne1, vertLigne2, vertLigne3,
                    rougeLigne1, rougeLigne2, rougeLigne3);
    }

    private void initialiser(int[] vl1, int[] vl2, int[] vl3,
                              int[] rl1, int[] rl2, int[] rl3) {
        for (int col = 0; col < TAILLE; col++) {
            grille[0][col] = new Case(new Pokemon(vl1[col], Type.getEspece(vl1[col])), 1);
            grille[1][col] = new Case(new Pokemon(vl2[col], Type.getEspece(vl2[col])), 1);
            grille[2][col] = new Case(new Pokemon(vl3[col], Type.getEspece(vl3[col])), 1);
            grille[6][col] = new Case(new Pokemon(rl3[col], Type.getEspece(rl3[col])), 2);
            grille[7][col] = new Case(new Pokemon(rl2[col], Type.getEspece(rl2[col])), 2);
            grille[8][col] = new Case(new Pokemon(rl1[col], Type.getEspece(rl1[col])), 2);
        }
    }

    public Case getCase(int ligne, int col) {
        return grille[ligne][col];
    }

    public void deplacer(int ligneDepart, int colDepart, int ligneDest, int colDest) {
        grille[ligneDest][colDest] = grille[ligneDepart][colDepart];
        grille[ligneDepart][colDepart] = new Case();
    }

    public void attaquer(int ligneAtt, int colAtt, int ligneDef, int colDef) {
        Case caseAtt = grille[ligneAtt][colAtt];
        Case caseDef = grille[ligneDef][colDef];
        Pokemon attaquant = caseAtt.getPokemon();
        Pokemon defenseur = caseDef.getPokemon();

        double efficacite = Type.getEfficacite(attaquant.getType1(), defenseur.getType1());
        if (defenseur.getType2() != Type.SANS) {
            efficacite *= Type.getEfficacite(attaquant.getType1(), defenseur.getType2());
        }

        int degats = (int)((attaquant.getAttaque() - defenseur.getDefense()) * efficacite);
        if (degats < 1) degats = 1;

        defenseur.setPv(defenseur.getPv() - degats);

        if (!defenseur.estVivant()) {
            grille[ligneDef][colDef] = new Case();
        }
    }

    public boolean mewtwoVivant(int joueur) {
        for (int i = 0; i < TAILLE; i++) {
            for (int j = 0; j < TAILLE; j++) {
                Case c = grille[i][j];
                if (!c.estVide() && c.getJoueur() == joueur && c.getPokemon().getNumero() == 150) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean estDansGrille(int ligne, int col) {
        return ligne >= 0 && ligne < TAILLE && col >= 0 && col < TAILLE;
    }
}
