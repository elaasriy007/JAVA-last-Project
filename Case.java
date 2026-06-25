class Case {
    private Pokemon pokemon;
    private int joueur;

    public Case() {
        this.pokemon = null;
        this.joueur = 0;
    }

    public Case(Pokemon pokemon, int joueur) {
        this.pokemon = pokemon;
        this.joueur = joueur;
    }

    public Pokemon getPokemon() {
        return pokemon;
    }

    public void setPokemon(Pokemon pokemon) {
        this.pokemon = pokemon;
    }

    public int getJoueur() {
        return joueur;
    }

    public void setJoueur(int joueur) {
        this.joueur = joueur;
    }

    public boolean estVide() {
        return pokemon == null;
    }
}
