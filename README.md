# **Javazonia - Version Finale - 13 janvier 2025**

## **Informations de développement**

- **Développé par** : Baptiste Lavogiez  
- **Contact** :  
  - Mail : [baptiste.lavogiez.etu@univ-lille.fr](mailto:baptiste.lavogiez.etu@univ-lille.fr) / [baptiste.lavogiez@proton.me](mailto:baptiste.lavogiez@proton.me)  
  - Page GitHub : [blavogiez](https://github.com/blavogiez)  

*Ce projet était initialement prévu pour être réalisé en binôme, mais il a été développé seul en raison de la réorientation de l'autre membre.*  

Le langage utilisé est *iJava*, une version allégée du langage de programmation *Java*, conçue et utilisée pour le premier semestre du BUT Informatique dispensé à l'Université de Lille.  
Cette version simplifiée et ludique permet d'apprendre rapidement *Java* en "masquant" les concepts de programmation orientée objet, normalement essentiels au langage.  

Cependant, *iJava* reste limité et fermé dans ses fonctionnalités, ce qui impose parfois de recourir à des méthodes complexes pour des tâches qui seraient plus simples à réaliser dans d'autres langages.  

Un effort particulier a été apporté à la lisibilité et à la modularité du code, de sorte que presque tout le monde puisse le modifier facilement !  

---

## **Présentation de Javazonia**  

**Javazonia** est un jeu qui allie culture et géographie. Le joueur doit répondre à des questions sur un pays aléatoire et le placer correctement sur une carte interactive. En cas de succès, il peut créer un nouveau pays en remplaçant l'ancien, mettant ainsi sa créativité au premier plan !  

### **Fonctionnalités**  

- **Carte interactive** : Plus qu'un simple quiz, le joueur doit placer le pays sur une carte.  
- **Classement dynamique** : Les résultats sont enregistrés et un classement permet de voir les statistiques de tous les joueurs.  
- **Interface intuitive** : Conçue pour être simple d'utilisation, quel que soit l'âge.  

### **Ressources disponibles**  

- **Répertoire "shots"** : Captures d'écran illustrant le jeu.  
- **Sous-répertoire "sample" dans "ressources"** : Données d'exemple pour illustrer l'affichage des statistiques.  
- **Exemple de partie** : [Trace texte](https://docs.google.com/document/d/18PxcgYjN95tU1LpVmWXv7amlVq6zhwb25OhHv7NDO3Y/edit?tab=t.0)  

---

## **Recommandations de paramètres**  

Pour bien voir la carte lors de la partie géographie, il est recommandé de :  

- Régler la taille du terminal sur 7 ou 8.  
- Utiliser un terminal compatible avec les normes ANSI pour bien afficher les couleurs prévues.  

---

## **Utilisation de Javazonia**  

Pour utiliser le projet, exécutez les commandes suivantes dans un terminal :  

```bash
./compile.sh
```
- Compile les fichiers présents dans `src` et génère les fichiers `.class` dans `classes` ("Prépare le jeu").  

```bash
./run.sh
```
- Lance le jeu.  

### **Menu Principal**  

| **Option** | **Description** |
|------------|----------------|
| **1** | Jouer au jeu |
| **2** | Réinitialiser les données des pays à partir d'une copie originale stockée dans un dossier annexe |
| **3** | Réinitialiser l'intégralité des fichiers de données grâce à des copies originales dans un dossier annexe |
| **4** | Quitter le jeu |

Ces options permettent une maintenance simple du jeu en cas de dysfonctionnement dû à la modification des fichiers de données par l'utilisateur.
