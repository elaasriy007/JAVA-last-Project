import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

public class SelectionPokemon extends JDialog {

    private int[] selection;
    private List<JToggleButton> boutons = new ArrayList<>();
    private List<Integer> choisis = new ArrayList<>();
    private JLabel labelInfo;
    private int joueur;
    private static final int NB_POKEMON = 9;

    public SelectionPokemon(JFrame parent, int joueur, int[] defaut) {
        super(parent, "Joueur " + joueur + " — Choisissez vos 9 Pokémon", true);
        this.joueur = joueur;
        this.selection = defaut.clone();

        setLayout(new BorderLayout());

        Color couleurJoueur = joueur == 1 ? new Color(0, 180, 0) : Color.RED;
        String nomJoueur = joueur == 1 ? "vert" : "rouge";

        JLabel titre = new JLabel("Joueur " + nomJoueur + " — Choisissez exactement 9 Pokémon", SwingConstants.CENTER);
        titre.setFont(new Font("Arial", Font.BOLD, 16));
        titre.setForeground(couleurJoueur);
        titre.setBorder(BorderFactory.createEmptyBorder(10, 0, 5, 0));
        add(titre, BorderLayout.NORTH);

        labelInfo = new JLabel("0 / 9 sélectionnés", SwingConstants.CENTER);
        labelInfo.setFont(new Font("Arial", Font.PLAIN, 14));

        JPanel grillePokemon = new JPanel(new GridLayout(13, 12, 2, 2));
        grillePokemon.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        for (int num = 1; num <= 151; num++) {
            final int numero = num;
            String cheminImage = "images/" + num + ".png";
            ImageIcon icone = new ImageIcon(cheminImage);
            Image img = icone.getImage().getScaledInstance(55, 50, Image.SCALE_SMOOTH);

            JToggleButton btn = new JToggleButton();
            btn.setIcon(new ImageIcon(img));
            btn.setToolTipText("#" + num + " " + Type.getEspece(num));
            btn.setPreferredSize(new Dimension(60, 65));
            btn.setMargin(new Insets(0, 0, 0, 0));

            btn.addActionListener(e -> {
                if (btn.isSelected()) {
                    if (choisis.size() < NB_POKEMON) {
                        choisis.add(numero);
                    } else {
                        btn.setSelected(false);
                    }
                } else {
                    choisis.remove(Integer.valueOf(numero));
                }
                labelInfo.setText(choisis.size() + " / 9 sélectionnés");
            });

            boutons.add(btn);
            grillePokemon.add(btn);
        }

        JScrollPane scroll = new JScrollPane(grillePokemon);
        scroll.setPreferredSize(new Dimension(780, 450));
        add(scroll, BorderLayout.CENTER);

        JPanel bas = new JPanel(new BorderLayout());
        bas.setBorder(BorderFactory.createEmptyBorder(5, 10, 10, 10));
        bas.add(labelInfo, BorderLayout.NORTH);

        JPanel boutonsBas = new JPanel(new FlowLayout());

        JButton btnDefaut = new JButton("Utiliser la sélection par défaut");
        btnDefaut.addActionListener(e -> {
            dispose();
        });

        JButton btnValider = new JButton("Valider ma sélection");
        btnValider.setBackground(couleurJoueur);
        btnValider.setForeground(Color.WHITE);
        btnValider.addActionListener(e -> {
            if (choisis.size() == NB_POKEMON) {
                for (int i = 0; i < NB_POKEMON; i++) {
                    selection[i] = choisis.get(i);
                }
                dispose();
            } else {
                JOptionPane.showMessageDialog(this,
                    "Veuillez sélectionner exactement 9 Pokémon.",
                    "Sélection incomplète",
                    JOptionPane.WARNING_MESSAGE);
            }
        });

        boutonsBas.add(btnDefaut);
        boutonsBas.add(btnValider);
        bas.add(boutonsBas, BorderLayout.CENTER);
        add(bas, BorderLayout.SOUTH);

        pack();
        setLocationRelativeTo(parent);
    }

    public int[] getSelection() {
        return selection;
    }
}
