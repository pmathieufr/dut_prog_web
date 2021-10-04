# TP : Paramètres

## Introduction à l'usage des paramètres

### Utiliser la méthode GET

__Palette__

1. Reprenez la servlet Palette. Nous allons permettre de passer la composante de rouge en paramètre de la servlet.  
http://localhost:8080/vide/Palette?r=8  
`r` est un entier représentant la quantité de rouge, entre 0 et 15 inclus.
L'exemple affichera les 256 variations possibles de couleur lorsque la composante de rouge est à '8'. 

1. Il doit être possible d'appeler votre servlet lorsque le paramètre n'est pas fourni, ou qu'il est vide. Vérifiez également que r est entre 0 et 15. Dans tous ces cas proposez une palette avec 0 en rouge.
    * non fourni : http://localhost:8080/vide/Palette
    * vide : http://localhost:8080/vide/Palette?r=
    * hors limite : http://localhost:8080/vide/Palette?r=99  

1. Ajoutez 16 liens avant le tableau de couleurs permettant de naviguer à travers toutes les palettes possibles.  

__Ascii__

1. Reprenez la servlet Ascii permettant d'afficher dans un tableau des caractères 32 à 255 de cette table des caractères. Ajoutez à cette Servlet un paramètre nbcol permettant d’afficher les codes sur plusieurs colonnes à la demande. http://localhost:8080/vide/Ascii?nbCol=5 affichera par exemple les codes sur 5 colonnes.  

1. Ajoutez quelques contrôle sur ce paramètre. Si il n'est pas présent on prendra la valeur 1 par défaut. Ce paramètre ne devra pas être inférieur à 1 et supérieur à 14.  

1. Avant l'affichage du tableau, affichez quelques liens pointant vers des valeurs différentes de 'nbCol'

### Utiliser la méthode POST

1. Modifiez la servlet précédente pour qu'elle ait un formulaire en tête d'affichage. Ce formulaire contiendra une balise `<SELECT>` permettant de lister différentes valeurs de `nbcol` (balise `<option>`). Ce formulaire a pour objectif d'appeler cette même servlet, mais en utilisant la méthode POST. N'oubliez pas de mettre un bouton pour valider et envoyer les données de ce formulaire.  
  
Dans une servlet, que le paramètre soit transmis en GET ou en POST, il peut être récupéré avec la même méthode `getParameter()` de l'objet HttpServletRequest.  
Il ne sera donc pas nécessaire de changer cette partie du code.  

1. A chaque fois que vous valider le formulaire, une nouvelle requête arrive au serveur avec une valeur de nbcol.
Donc, une nouvelle page html est générée et le selecteur est remis sur sa première valeur : 1.  
Vous pouvez essayer de placer l'attribut `selected` sur l'option qui correspond à la valeur du parametre nbcol que vous avez reçu. C'est sans doute plus ergonomique.

## Gestion de rencontres sportives

Une fédération sportive organise un championnat entre des clubs. Afin de gérer efficacement ce travail, elle décide de réaliser une application WEB qui s’appuie sur une simple table qui contiendra au fur et à mesure, l’ensemble des résultats de chaque rencontre.  

Le fichier sql fourni dans le cours précédent contient déjà cette table et quelques lignes à l'intérieur.

table _rencontres_

| colonne   | type | description |
|-----------|------|-------------|
| num_match | int  | identifiant de la rencontre |
| eq1       | int  | identifiant de la première équipe, celle qui reçoit |
| eq2       | int  | identifiant de la deuxième équipe, en déplacement |
| jour      | date | jour de la rencontre |
| sc1       | int  | nombre de but inscrits par l'équipe 1 |
| sc2       | int  | nombre de but inscrits par l'équipe 2 |


### Une servlet simple

1. Ecrire une servlet `ListeRencontres` qui affiche l’ensemble des informations de la table _rencontres_ dans une table HTML.
Cette page est donc appelée par la requête HTTP : http://localhost:8080/vide/ListeRencontres
On pourra lui associer la même feuille CSS que pour `ListeJoueurs` de la séance précédente.

### Saisir et insérer des données

1. Créer une servlet `CreerRencontre` qui permet de saisir et d'insérer une nouvelle rencontre dans la base. L'affichage du formulaire pourra se faire dans le `doGet` et la gestion de l'insertion dans le `doPost`. Si l'insertion s'est bien passée, vous pouvez rediriger l'utilisateur vers `ListeRencontres`.  

