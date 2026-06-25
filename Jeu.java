class Jeu {
    private Plateau plateau;
    private int joueurActuel;
    private boolean partieTerminee;
    private int gagnant;

    public Jeu() {
        plateau = new Plateau();
        joueurActuel = 1;
        partieTerminee = false;
        gagnant = 0;
    }

    public Jeu(int[] vl1, int[] vl2, int[] vl3, int[] rl1, int[] rl2, int[] rl3) {
        plateau = new Plateau(vl1, vl2, vl3, rl1, rl2, rl3);
        joueurActuel = 1;
        partieTerminee = false;
        gagnant = 0;
    }

    public Plateau getPlateau() {
        return plateau;
    }

    public int getJoueurActuel() {
        return joueurActuel;
    }

    public boolean isPartieTerminee() {
        return partieTerminee;
    }

    public int getGagnant() {
        return gagnant;
    }

    public void deplacer(int ligneDepart, int colDepart, int ligneDest, int colDest) {
        if (partieTerminee) return;
        plateau.deplacer(ligneDepart, colDepart, ligneDest, colDest);
        changerTour();
    }

    public void attaquer(int ligneAtt, int colAtt, int ligneDef, int colDef) {
        if (partieTerminee) return;
        plateau.attaquer(ligneAtt, colAtt, ligneDef, colDef);
        verifierFin();
        if (!partieTerminee) changerTour();
    }

    private void changerTour() {
        joueurActuel = (joueurActuel == 1) ? 2 : 1;
    }

    private void verifierFin() {
        if (!plateau.mewtwoVivant(1)) {
            partieTerminee = true;
            gagnant = 2;
        } else if (!plateau.mewtwoVivant(2)) {
            partieTerminee = true;
            gagnant = 1;
        }
    }

    public boolean peutDeplacer(int ligneDepart, int colDepart, int ligneDest, int colDest) {
        if (!plateau.estDansGrille(ligneDest, colDest)) return false;
        if (!plateau.getCase(ligneDest, colDest).estVide()) return false;
        int dl = Math.abs(ligneDest - ligneDepart);
        int dc = Math.abs(colDest - colDepart);
        return dl <= 1 && dc <= 1 && (dl + dc > 0);
    }

    public boolean peutAttaquer(int ligneAtt, int colAtt, int ligneDef, int colDef) {
        if (!plateau.estDansGrille(ligneDef, colDef)) return false;
        Case cible = plateau.getCase(ligneDef, colDef);
        if (cible.estVide()) return false;
        if (cible.getJoueur() == joueurActuel) return false;
        int dl = Math.abs(ligneDef - ligneAtt);
        int dc = Math.abs(colDef - colAtt);
        return dl <= 1 && dc <= 1 && (dl + dc > 0);
    }
}
