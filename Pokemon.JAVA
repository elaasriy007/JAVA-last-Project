import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

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

    public Pokemon(int numero, String nom) {
        this.numero = numero;
        this.nom = nom;
        this.type1 = Type.SANS;
        this.type2 = Type.SANS;
        this.pv = 1;
        this.attaque = 1;
        this.defense = 1;
        this.vitesse = 1;

        String chemin = "pokedex_gen1.csv";
        try (BufferedReader lecteur = new BufferedReader(new FileReader(chemin))) {
            String ligne = lecteur.readLine();
            while ((ligne = lecteur.readLine()) != null) {
                String[] colonnes = ligne.split(";", -1);
                int numeroLigne = Integer.parseInt(colonnes[0]);
                if (numeroLigne == numero) {
                    this.type1 = Type.getIndiceType(colonnes[2]);
                    if (colonnes[3] == null || colonnes[3].isEmpty()) {
                        this.type2 = Type.SANS;
                    } else {
                        this.type2 = Type.getIndiceType(colonnes[3]);
                    }
                    this.pv = Integer.parseInt(colonnes[4]);
                    this.attaque = Integer.parseInt(colonnes[5]);
                    this.defense = Integer.parseInt(colonnes[6]);
                    this.vitesse = Integer.parseInt(colonnes[7]);
                    break;
                }
            }
        } catch (IOException e) {
            System.out.println("Erreur de lecture du fichier pokedex : " + e.getMessage());
        }
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

    private double efficaciteContre(Pokemon adversaire) {
        double eff1 = Type.getEfficacite(this.type1, adversaire.getType1());
        double eff2 = Type.NEUTRE;
        if (adversaire.getType2() != Type.SANS) {
            eff2 = Type.getEfficacite(this.type1, adversaire.getType2());
        }
        return eff1 * eff2;
    }

    public void attaque(Pokemon adversaire) {
        Pokemon premier = this;
        Pokemon second = adversaire;
        if (adversaire.getVitesse() > this.getVitesse()) {
            premier = adversaire;
            second = this;
        }

        double efficacite = premier.efficaciteContre(second);
        int degats = (int) ((premier.getAttaque() - second.getDefense()) * efficacite);
        if (degats < 1) {
            degats = 1;
        }
        second.setPv(second.getPv() - degats);
        System.out.println(premier.getNom() + " attaque " + second.getNom() + " et inflige " + degats + " dégâts.");

        if (second.estVivant()) {
            double efficaciteRiposte = second.efficaciteContre(premier);
            int degatsRiposte = (int) ((second.getAttaque() - premier.getDefense()) * efficaciteRiposte);
            if (degatsRiposte < 1) {
                degatsRiposte = 1;
            }
            premier.setPv(premier.getPv() - degatsRiposte);
            System.out.println(second.getNom() + " riposte contre " + premier.getNom() + " et inflige " + degatsRiposte + " dégâts.");
        } else {
            System.out.println(second.getNom() + " est K.O. !");
        }
    }
}
