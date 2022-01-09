# Clément Trens - Dorian Grasset SAE 1.01: Scrabble

Vous trouverez dans ce repo tous nos éfforts fournis pour produire ce Scrabble.

Liens Utiles

| Version de base | Version avec Extensions |
| ----------- | ----------- |
| https://github.com/Atomoox/Scrabble  | https://github.com/Atomoox/Scrabble-Extensions |

	

## Extensions et avancé du scrabble.

- [x] Version De Base 
- [X] Prise en compte d’un dictionnaire de référence
- [ ] Ajout des jetons joker
- [X] Détermination du joueur qui commence
- [ ] Prise en compte des jetons qui "touchent" le mot transversalement
- [ ] Initialisation du plateau
- [ ] Version IA
- [ ] Interface graphique

## Comment installer notre production ?
Notre production a été entièrement posté sur ce github libre d'accès. Vous pouvez donc cloner notre projet en utilisant la commande ci-dessous dans votre CMD (avec git installé).

| Version de base | Version avec Extensions |
| ----------- | ----------- |
| ``` $ git clone https://github.com/Atomoox/Scrabble.git Scrabble``` | ``` $ git clone https://github.com/Atomoox/Scrabble-Extensions.git Scrabble``` |


## Comment compiler le projet ?

Notre projet est codé en JAVA. Vous pouvez donc le complier comme un Projet JAVA classique. Ici, le fichier principal est ` MainScrabble.java`

    $ cd Scrabble
    $ javac MainScrabble.java

## Exécuter et jouer au jeu

Une fois le fichier principal compilé vous pouvez lancer une partie de scrabble en éxecutant le fichier de cette manière

    $ java MainScrabble

Si certains fichier ne se sont pas compilés automatiquement lors du premier javac que vous avez éxécuté au dessus, compilez les 1 à 1.

    $ javac {fichier}.java

## Tester notre scrabble

Lancer et jouer une partie sérieusement avec un ou plusieurs amis (si par chance vous en avez) est le meilleur moyen de tester notre Scrabble.
Si vous rencontrez des bugs, ne nous enlevez pas de points, ouvrez seulement un rapport de bug sur le Github, nous ne les lirons probablement pas.

## Trouver les extensions

| Détermination du joueur qui commence | Prise en compte d’un dictionnaire de référence |
| ----------- | ----------- |
| getStarter (Scrabble.java:40) | initDico (Plateau.java:37) |
| this.getStarter(); (Scrabble.java:73) | estDico (Plateau.java:49) |
| / | this.estDico(mot); (Plateau.java:126) |
