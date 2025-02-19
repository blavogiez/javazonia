# **Javazonia - Version Finale - 13 janvier 2025**

## **Informations de développement**

- **Développé par** : Baptiste Lavogiez
- **Contact** :
  - Mail : [baptiste.lavogiez.etu@univ-lille.fr](mailto:baptiste.lavogiez.etu@univ-lille.fr) / [baptiste.lavogiez@proton.me](mailto:baptiste.lavogiez@proton.me)
  - Page Github : [blavogiez](https://github.com/blavogiez)

*Ce projet est prévu pour être réalisé par binôme, mais il s'est déroulé seul en raison de la réorientation de l'autre membre.*

Le langage utilisé est le *iJava*, une version allégée du langage de programmation *Java*, conçue et utilisée pour le premier semestre de BUT Informatique, dispensé à l'Université de Lille.
Cette version est simple et ludique, et a pour but de rendre accessible le *Java* très rapidement en "masquant" les fonctionnalités de programmation objet normalement essentielles au langage Java.
Malgré cela, ce langage est assez limité et fermé dans ses fonctionnalités, forçant parfois à réaliser des méthodes complexes pour des tâches résolubles simplement dans d'autres langages.

Ce langage reste assez simple à comprendre et un effort particulier a été mis dans la lisibilité et modularité du code, ainsi, quasiment tout le monde pourrait le modifier simplement !

---

## **Présentation de Javazonia**

**Javazonia** est un jeu qui allie culture et géographie. Le joueur doit répondre à des questions sur un pays aléatoire et le placer correctement sur une carte interactive. S'il réussit, il peut créer un nouveau pays en remplaçant l'ancien, mettant ainsi sa créativité au premier plan !

### **Fonctionnalités**

- **Carte de mouvement interactive** : Plus qu'un simple quiz, le joueur doit placer le pays sur la carte.
- **Classement animé** : Les résultats sont enregistrés et un classement permet de voir les statistiques de tous les joueurs.
- **Interface intuitive** : Conçue pour être simple d'utilisation pour tout âge.

### **Ressources disponibles**

- **Répertoire "shots"** : Captures d'écran illustrant le jeu.
- **Sous-répertoire "sample" dans "ressources"** : Données exemples pour démontrer l'affichage des statistiques.
- **Exemple d'une partie** : [Trace texte](https://docs.google.com/document/d/18PxcgYjN95tU1LpVmWXv7amlVq6zhwb25OhHv7NDO3Y/edit?tab=t.0)

---

## **Recommandation de paramètres**

Pour bien voir la carte lors de la partie géographie, il est recommandé de :

- Mettre le terminal en taille 7 ou 8.
- Utiliser un terminal avec les normes ANSI pour bien voir les couleurs prévues.

---

## **Utilisation de Javazonia**

Pour utiliser le projet, exécutez les commandes suivantes dans un terminal :

```bash
./compile.sh

 - Compile les fichiers présents dans src et crée les fichiers .class dans classes ("Prépare le jeu").

```bash
./run.sh

- Lance le jeu.

### **Menu Principal**
   **Option** | **Description**                                                                 |
 |------------|---------------------------------------------------------------------------------|
 | **1**      | Jouer au jeu                                                                   |
 | **2**      | Réinitialiser les données des pays, grâce à une copie originale dans un dossier annexe |
 | **3**      | Réinitialiser les données de tous les fichiers, grâce à des copies originales dans un dossier annexe |
 | **4**      | Quitter le jeu |

Ces contrôles permettent une maintenance simple du jeu en cas de dysfonctionnement suite à des modifications de fichiers de données par l'utilisateur.
