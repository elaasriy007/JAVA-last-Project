# Échecs Pokémon — Projet Java 

Projet en Java divisé en deux parties :
- **Partie 1** : Moteur de combat Pokémon
- **Partie 2** : Jeu d'échecs Pokémon avec interface graphique Swing

---

## Structure du projet

```
projet/
├── README.md
├── Type.java               # Types et efficacités (Partie 1)
├── Pokemon.java            # Classe Pokémon (Partie 1)
├── Main.java               # Combat simple en terminal (Partie 1)
├── Case.java               # Une case du plateau (Partie 2)
├── Plateau.java            # Grille 9x9 et logique de placement (Partie 2)
├── Jeu.java                # Tour par tour et fin de partie (Partie 2)
├── SelectionPokemon.java   # Écran de sélection des Pokémon (Partie 2 - Bonus)
├── FenetrePrincipale.java  # Interface graphique Swing (Partie 2)
├── pokedex_gen1.csv        # Données des 151 Pokémon Gen 1 _  ## ce fichier n<est pas inclus dans le git
└── images/           ##  les images sont pas toute importer au git a cause de limitation de 100 image dans cette version de git
    ├── 1.png
    ├── 2.png
    └── ... (151 sprites)
```

---

## Prérequis

Java JDK 8 ou supérieur doit être installé sur votre machine.

Vérifier que Java est bien installé :
```bash
java -version
javac -version
```

Si Java n'est pas installé, téléchargez-le ici : https://www.oracle.com/java/technologies/downloads/

---

## Installation

1. Cloner ou télécharger le projet :
```bash
git clone <url-du-repo>
cd projet
```

2. Vérifier que le dossier `images/` est bien présent à la racine avec les 151 sprites PNG.  ### les images sont pas toute importer au git a cause de limitation de 100 image dans cette version de git

3. Vérifier que `pokedex_gen1.csv` est bien présent à la racine.  ### ce fichier n<est pas inclus dans le git

---

## Compilation

Depuis le dossier racine du projet, compiler tous les fichiers Java en une seule commande :

```bash
javac *.java
```

Cela génère les fichiers `.class` dans le même dossier.

---

## Exécution

### Partie 1 — Combat Pokémon (terminal)

```bash
java Main
```

Deux Pokémon s'affrontent tour par tour dans le terminal jusqu'à ce que l'un soit K.O. Le résultat s'affiche avec les dégâts infligés à chaque tour.

Exemple de sortie :
```
--- Tour 1 ---
Gustave attaque Robert et inflige 5 dégâts.
Robert riposte contre Gustave et inflige 3 dégâts.

--- Tour 2 ---
...
Gustave remporte le combat !
```

### Partie 2 — Jeu d'échecs Pokémon (interface graphique)

```bash
java FenetrePrincipale
```

Une fenêtre graphique s'ouvre. Au démarrage :
1. Il vous sera demandé si le **joueur vert** veut choisir ses Pokémon
2. Puis si le **joueur rouge** veut choisir les siens
3. Si vous répondez **Non**, la disposition par défaut est utilisée
4. Le plateau s'affiche et la partie commence

---

## Comment jouer (Partie 2)

**Objectif** : tuer le Mewtwo adverse.

1. Le joueur dont c'est le tour est indiqué en haut de la fenêtre
2. **Cliquer** sur un de vos Pokémon pour le sélectionner (la case devient jaune)
3. Les cases disponibles s'affichent en bleu avec un cercle `○`
4. **Cliquer sur un cercle** pour déplacer ou attaquer :
   - Case vide → **déplacement**
   - Case occupée par un ennemi → **attaque**
5. Le tour passe automatiquement à l'adversaire

**Règles :**
- Plateau de 9x9 cases
- Chaque joueur démarre avec 3 lignes de Pokémon (haut pour le vert, bas pour le rouge)
- Un Pokémon peut se déplacer d'une case dans les 8 directions
- Les dégâts tiennent compte de l'efficacité des types Pokémon
- La partie s'arrête quand un Mewtwo est éliminé

---

## Bonus — Sélection personnalisée des Pokémon

Au lancement, chaque joueur peut composer son équipe parmi les 151 Pokémon de la Gen 1. Une fenêtre s'ouvre avec tous les sprites. Sélectionner exactement **9 Pokémon** par ligne (3 lignes = 27 Pokémon par joueur). Cliquer sur **Valider** pour confirmer ou **Utiliser la sélection par défaut** pour ignorer.

---

## YASSINE ELAASRI

Projet réalisé dans le cadre du cours de Java — LPDIM 2025-2026
