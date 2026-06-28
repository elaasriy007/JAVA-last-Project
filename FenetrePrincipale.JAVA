import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

public class FenetrePrincipale extends JFrame {

    private Jeu jeu;
    private JPanel panneauPlateau;
    private JLabel labelTour;
    private CasePanel[][] casesPanels;
    private int ligneSelectionnee = -1;
    private int colSelectionnee = -1;
    private List<int[]> actionsDisponibles = new ArrayList<>();

    public FenetrePrincipale() {
        int[] vl1 = demanderSelection(1, Plateau.DEFAUT_LIGNE1);
        int[] vl2 = demanderSelection(1, Plateau.DEFAUT_LIGNE2);
        int[] vl3 = demanderSelection(1, Plateau.DEFAUT_LIGNE3);
        int[] rl1 = demanderSelection(2, Plateau.DEFAUT_LIGNE1);
        int[] rl2 = demanderSelection(2, Plateau.DEFAUT_LIGNE2);
        int[] rl3 = demanderSelection(2, Plateau.DEFAUT_LIGNE3);

        jeu = new Jeu(vl1, vl2, vl3, rl1, rl2, rl3);

        setTitle("Echecs Pokemon");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        labelTour = new JLabel("Au joueur vert de jouer", SwingConstants.CENTER);
        labelTour.setFont(new Font("Arial", Font.BOLD, 28));
        labelTour.setForeground(new Color(0, 180, 0));
        labelTour.setOpaque(true);
        labelTour.setBackground(new Color(100, 180, 255));
        labelTour.setPreferredSize(new Dimension(900, 70));
        add(labelTour, BorderLayout.NORTH);

        panneauPlateau = new JPanel(new GridLayout(Plateau.TAILLE, Plateau.TAILLE));
        casesPanels = new CasePanel[Plateau.TAILLE][Plateau.TAILLE];

        for (int i = 0; i < Plateau.TAILLE; i++) {
            for (int j = 0; j < Plateau.TAILLE; j++) {
                CasePanel cp = new CasePanel(i, j);
                casesPanels[i][j] = cp;
                panneauPlateau.add(cp);
            }
        }

        add(panneauPlateau, BorderLayout.CENTER);
        rafraichir();
        pack();
        setSize(900, 1000);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private int[] demanderSelection(int joueur, int[] defaut) {
        int choix = JOptionPane.showConfirmDialog(
            this,
            "Joueur " + (joueur == 1 ? "vert" : "rouge") + " : voulez-vous choisir vos Pokémon ?",
            "Sélection Pokémon",
            JOptionPane.YES_NO_OPTION
        );
        if (choix == JOptionPane.YES_OPTION) {
            SelectionPokemon dialog = new SelectionPokemon(this, joueur, defaut);
            dialog.setVisible(true);
            return dialog.getSelection();
        }
        return defaut.clone();
    }

    private void rafraichir() {
        for (int i = 0; i < Plateau.TAILLE; i++) {
            for (int j = 0; j < Plateau.TAILLE; j++) {
                casesPanels[i][j].rafraichir();
            }
        }
        if (!jeu.isPartieTerminee()) {
            if (jeu.getJoueurActuel() == 1) {
                labelTour.setText("Au joueur vert de jouer");
                labelTour.setForeground(new Color(0, 180, 0));
            } else {
                labelTour.setText("Au joueur rouge de jouer");
                labelTour.setForeground(Color.RED);
            }
        } else {
            String couleur = jeu.getGagnant() == 1 ? "vert" : "rouge";
            labelTour.setText("Le joueur " + couleur + " a gagné !");
            labelTour.setForeground(jeu.getGagnant() == 1 ? new Color(0, 180, 0) : Color.RED);
        }
    }

    private void gererClic(int ligne, int col) {
        if (jeu.isPartieTerminee()) return;

        for (int[] action : actionsDisponibles) {
            if (action[0] == ligne && action[1] == col) {
                if (action[2] == 0) {
                    jeu.deplacer(ligneSelectionnee, colSelectionnee, ligne, col);
                } else {
                    jeu.attaquer(ligneSelectionnee, colSelectionnee, ligne, col);
                }
                ligneSelectionnee = -1;
                colSelectionnee = -1;
                actionsDisponibles.clear();
                rafraichir();
                return;
            }
        }

        actionsDisponibles.clear();
        ligneSelectionnee = -1;
        colSelectionnee = -1;

        Case caseCliquee = jeu.getPlateau().getCase(ligne, col);
        if (!caseCliquee.estVide() && caseCliquee.getJoueur() == jeu.getJoueurActuel()) {
            ligneSelectionnee = ligne;
            colSelectionnee = col;

            int[] dl = {-1, -1, -1, 0, 0, 1, 1, 1};
            int[] dc = {-1, 0, 1, -1, 1, -1, 0, 1};

            for (int d = 0; d < 8; d++) {
                int nl = ligne + dl[d];
                int nc = col + dc[d];
                if (jeu.peutDeplacer(ligne, col, nl, nc)) {
                    actionsDisponibles.add(new int[]{nl, nc, 0});
                } else if (jeu.peutAttaquer(ligne, col, nl, nc)) {
                    actionsDisponibles.add(new int[]{nl, nc, 1});
                }
            }
        }

        rafraichir();
    }

    private boolean estActionDisponible(int ligne, int col) {
        for (int[] action : actionsDisponibles) {
            if (action[0] == ligne && action[1] == col) return true;
        }
        return false;
    }

    class CasePanel extends JPanel {
        private int ligne;
        private int col;
        private JLabel labelImage;
        private JLabel labelPv;

        public CasePanel(int ligne, int col) {
            this.ligne = ligne;
            this.col = col;
            setLayout(new BorderLayout());
            setBorder(BorderFactory.createLineBorder(Color.GRAY));
            setPreferredSize(new Dimension(100, 100));

            labelImage = new JLabel("", SwingConstants.CENTER);
            labelPv = new JLabel("", SwingConstants.CENTER);
            labelPv.setFont(new Font("Arial", Font.BOLD, 14));

            add(labelImage, BorderLayout.CENTER);
            add(labelPv, BorderLayout.SOUTH);

            addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent e) {
                    gererClic(ligne, col);
                }
            });
        }

        public void rafraichir() {
            Case c = jeu.getPlateau().getCase(ligne, col);
            boolean action = estActionDisponible(ligne, col);

            if (ligne == ligneSelectionnee && col == colSelectionnee) {
                setBackground(new Color(255, 255, 150));
            } else if (action) {
                setBackground(new Color(200, 230, 255));
            } else {
                setBackground(new Color(220, 220, 220));
            }

            if (c.estVide()) {
                labelImage.setIcon(null);
                labelImage.setText(action ? "○" : "");
                if (action) {
                    labelImage.setFont(new Font("Arial", Font.PLAIN, 40));
                    labelImage.setForeground(Color.DARK_GRAY);
                }
                labelPv.setText("");
            } else {
                Pokemon p = c.getPokemon();
                String cheminImage = "images/" + p.getNumero() + ".png";
                ImageIcon icone = new ImageIcon(cheminImage);
                Image img = icone.getImage().getScaledInstance(80, 70, Image.SCALE_SMOOTH);
                labelImage.setIcon(new ImageIcon(img));
                labelImage.setText("");
                labelPv.setText(String.valueOf(p.getPv()));
                labelPv.setForeground(c.getJoueur() == 1 ? new Color(0, 150, 0) : Color.RED);
            }

            repaint();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new FenetrePrincipale());
    }
}
