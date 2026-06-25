class Plateau {
    public static final int TAILLE = 9;
    private Case[][] grille;

    public Plateau() {
        grille = new Case[TAILLE][TAILLE];
        for (int i = 0; i < TAILLE; i++) {
            for (int j = 0; j < TAILLE; j++) {
                grille[i][j] = new Case();
            }
        }
        initialiser();
    }

    private void initialiser() {
        int[][] dispositionLigne1 = {4, 7, 1, 145, 150, 146, 3, 7, 4};
        int[][] dispositionLigne2 = {6, 9, 3, 149, 131, 149, 3, 9, 6};

        for (int col = 0; col < TAILLE; col++) {
            int num1 = dispositionLigne1[col];
            Pokemon pVert1 = new Pokemon(num1, Type.getEspece(num1));
            grille[0][col] = new Case(pVert1, 1);

            int num2 = dispositionLigne2[col];
            Pokemon pVert2 = new Pokemon(num2, Type.getEspece(num2));
            grille[1][col] = new Case(pVert2, 1);

            int num8 = dispositionLigne2[col];
            Pokemon pRouge8 = new Pokemon(num8, Type.getEspece(num8));
            grille[7][col] = new Case(pRouge8, 2);

            int num9 = dispositionLigne1[col];
            Pokemon pRouge9 = new Pokemon(num9, Type.getEspece(num9));
            grille[8][col] = new Case(pRouge9, 2);
        }
    }

    public Case getCase(int ligne, int col) {
        return grille[ligne][col];
    }

    public void deplacer(int ligneDepart, int colDepart, int ligneDest, int colDest) {
        grille[ligneDest][colDest] = grille[ligneDepart][colDepart];
        grille[ligneDepart][colDepart] = new Case();
    }

    public void attaquer(int lignAtt, int colAtt, int ligneDef, int colDef) {
        Case caseAtt = grille[lignAtt][colAtt];
        Case caseDef = grille[ligneDef][colDef];
        Pokemon attaquant = caseAtt.getPokemon();
        Pokemon defenseur = caseDef.getPokemon();

        int degats = attaquant.getAttaque() - defenseur.getDefense();
        if (degats < 1) degats = 1;

        double efficacite = Type.getEfficacite(attaquant.getType1(), defenseur.getType1());
        if (defenseur.getType2() != Type.SANS) {
            efficacite *= Type.getEfficacite(attaquant.getType1(), defenseur.getType2());
        }
        degats = (int)(degats * efficacite);
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
