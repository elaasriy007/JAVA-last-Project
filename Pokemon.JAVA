class Pokemon {

    private int numero;
    private String nom;
    private int type1;
    private int type2;
    private int pv;
    private int attaque;
    private int defense;
    private int vitesse;

    public Pokemon() {
        this.numero = 1;
        this.nom = Type.getEspece(1);
        this.type1 = Type.PLANTE;
        this.type2 = Type.POISON;
        this.pv = 45;
        this.attaque = 49;
        this.defense = 49;
        this.vitesse = 45;
    }

    public Pokemon(int numero, String nom, int type1, int type2, int pv, int attaque, int defense, int vitesse) {
        this.numero = numero;
        this.nom = nom;
        this.type1 = type1;
        this.type2 = type2;
        this.pv = pv;
        this.attaque = attaque;
        this.defense = defense;
        this.vitesse = vitesse;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public int getType1() {
        return type1;
    }

    public void setType1(int type1) {
        this.type1 = type1;
    }

    public int getType2() {
        return type2;
    }

    public void setType2(int type2) {
        this.type2 = type2;
    }

    public int getPv() {
        return pv;
    }

    public void setPv(int pv) {
        this.pv = pv;
    }

    public int getAttaque() {
        return attaque;
    }

    public void setAttaque(int attaque) {
        this.attaque = attaque;
    }

    public int getDefense() {
        return defense;
    }

    public void setDefense(int defense) {
        this.defense = defense;
    }

    public int getVitesse() {
        return vitesse;
    }

    public void setVitesse(int vitesse) {
        this.vitesse = vitesse;
    }

    public boolean estVivant() {
        return pv > 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Pokemon autre = (Pokemon) o;
        return numero == autre.numero
                && type1 == autre.type1
                && type2 == autre.type2
                && pv == autre.pv
                && attaque == autre.attaque
                && defense == autre.defense
                && vitesse == autre.vitesse
                && nom.equals(autre.nom);
    }

    @Override
    public String toString() {
        String typeTexte = Type.getNomType(type1);
        if (type2 != Type.SANS) {
            typeTexte += "/" + Type.getNomType(type2);
        }
        return Type.getEspece(numero) + " (" + nom + ") - n°" + numero
                + " - Type: " + typeTexte
                + " - PV: " + pv
                + " - Att: " + attaque
                + " - Def: " + defense
                + " - Vit: " + vitesse;
    }

    public void attaque(Pokemon adversaire) {
        Pokemon premier = this;
        Pokemon second = adversaire;
        if (adversaire.getVitesse() > this.getVitesse()) {
            premier = adversaire;
            second = this;
        }

        int degats = premier.getAttaque() - second.getDefense();
        if (degats < 0) {
            degats = 1;
        }
        second.setPv(second.getPv() - degats);
        System.out.println(premier.getNom() + " attaque " + second.getNom() + " et inflige " + degats + " dégâts.");

        if (second.estVivant()) {
            int degatsRiposte = second.getAttaque() - premier.getDefense();
            if (degatsRiposte < 0) {
                degatsRiposte = 1;
            }
            premier.setPv(premier.getPv() - degatsRiposte);
            System.out.println(second.getNom() + " riposte contre " + premier.getNom() + " et inflige " + degatsRiposte + " dégâts.");
        } else {
            System.out.println(second.getNom() + " est K.O. !");
        }
    }
}
