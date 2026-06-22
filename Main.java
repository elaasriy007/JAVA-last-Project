class Main {
    public static void main(String[] args) {
        Pokemon p1 = new Pokemon(57, "Robert");
        Pokemon p2 = new Pokemon(94, "Gustave");

        System.out.println(p1);
        System.out.println(p2);
        System.out.println("Le combat commence !");
        System.out.println();

        int tour = 1;
        while (p1.estVivant() && p2.estVivant()) {
            System.out.println("--- Tour " + tour + " ---");
            p1.attaque(p2);
            System.out.println("PV " + p1.getNom() + ": " + p1.getPv() + " | PV " + p2.getNom() + ": " + p2.getPv());
            System.out.println();
            tour++;
        }

        if (p1.estVivant()) {
            System.out.println(p1.getNom() + " remporte le combat !");
        } else {
            System.out.println(p2.getNom() + " remporte le combat !");
        }
    }
}
