=========================================
 Javazonia - Version Finale - 13/01/2025
=========================================

# Informations de développement

Développé par Baptiste Lavogiez
Contact mail : baptiste.lavogiez.etu@univ-lille.fr / baptiste.lavogiez@proton.me 
Page Github : https://github.com/blavogiez

# Présentation de Javazonia

Javazonia est un jeu autant de culture que de géographie, permettant au joueur, s'il répond assez bien à des questions sur un pays aléatoires et à son placement, de créer un nouveau pays en remplaçant l'ancien afin de mettre sa créativité au premier plan.

La carte de mouvement interactive fait de ce jeu plus qu'un quiz (difficulté principale), car il devra placer le pays.

Ses résultats sont ensuite enregistrés et un classement animé permet de voir les statistiques de tous les joueurs.
En bref, tout est expliquée dans l'exécution qui est pensée pour être propre et simple d'utilisation pour tout âge.
Le répertoire "shots" fournit des captures d'écran pouvant mieux illustrer que des mots. 
Le sous-répertoire "sample" dans "ressources" fournit des données exemples afin de démontrer l'affichage des statistiques sur un groupe d'une dizaine de joueurs.

Un exemple de jeu, au format texte ("trace texte"), comme pensé au début du projet est disponible ici :
https://docs.google.com/document/d/18PxcgYjN95tU1LpVmWXv7amlVq6zhwb25OhHv7NDO3Y/edit?tab=t.0
(textes non identiques, mais l'idée reste la même)

# Recommandation de paramètres

!Pour bien voir la carte lors de la partie géographie, il faut mettre son terminal en taille 7 ou 8.
Clic droit espace vide / Profils / Préférences du Profil / Décocher utiliser police à chasse / Cliquer sur "Monospace Regular"/ Changer la taille dans la fenêtre

Un terminal utilisant les normes ANSI est recommandé afin de bien voir les couleurs prévues.

# Utilisation de Javazonia

Afin d'utiliser le projet, il suffit de taper les commandes suivantes dans un terminal :

```
./compile.sh
```
Permet la compilation des fichiers présents dans 'src' et création des fichiers '.class' dans 'classes' ("Prépare le jeu")

```
./run.sh
```
Permet le lancement du jeu

Un menu s'ouvre, et différents contrôles s'affichent.

Option 1 :
    Jouer au jeu
Option 2 :
    Réinitialiser les données des pays, grâce à une copie original dans un dossier annexe
Option 3 :
    Réinitialiser les données de tous les fichiers, grâce à des copies originales dans un dossier annexe
Option 4 :
    Quitte le jeu

Ces contrôles sont présents pour permettre une maintenance simple du jeu en cas de dysfonctionnement suite à des modifications de fichiers de données par l'utilisateur
