
# TP : Utilisation de session

## Objectifs

* Comprendre l’intérêt et l’usage des Sessions 


## Les compteurs

On souhaite réaliser une page web qui affiche pour chacun des utilisateurs le message "Vous avez accédé 12 fois à
cette page sur les 53 accès au total". Réalisez la servlet `Compteur.java` qui permet de d'afficher cette phrase. 

* Pour le compteur local on utilisera une session
* Pour le compteur global on utilisera un attribut de la servlet

Pour vérifier le comportement correct de votre servlet, vous utiliserez deux navigateurs différents ou éventuellement une fenêtre de navigation privée.

Depuis Tomcat6 les sessions sont sérialisées lors de l’arrêt du serveur, stockées dans le fichier `SESSIONS.ser` de `work/Catalina/localhost/vide`, c’est pourquoi l’arrêt du serveur ne perd plus les sessions. Pour tout réinitialiser lors de vos tests, détruidez ce fichier `SESSION.ser`avant de redémarrer Tomcat.

## Listes en session

Les sessions permettent essentiellement de conserver de l'information propre à chaque utilisateur, sans avoir à refaire une requête à la base de données ou à maintenir soi-même un état.  

_rappel du schéma des tables_

* equipes( num_equipe integer not null, nom_equipe varchar(80) );
* joueurs( num_joueur integer not null, nom_joueur varchar(100), pays varchar(80), poste varchar(10), maillot integer, date_naissance date, club integer, salaire integer );
* rencontres( num_match integer not null, eq1 integer, eq2 integer, jour date, sc1 integer, sc2 integer );

On souhaite dans cet exercice que chaque visiteur du site puisse préparer la composition d'une "dreamTeam". Pour cela nous allons ranger la liste des joueurs sélectionnés dans une session afin que chaque visiteur ait sa propre liste.

1. Ecrire une page `ListerJoueur` qui prend en paramètre un nom de poste (GAR, DEF, ATT, ...). Si le paramètre 'poste' n'est pas fourni on affichera les attaquants. Pour chaque joueur, on pourra se contenter d'afficher le numéro de maillot et le nom de chacun des joueurs.
1. Sur chaque nom, mettre un lien vers une servlet `ChoisirJoueur` avec le nom de ce joueur en paramètre. Ecrire ensuite la servlet `ChoisirJoueur` qui ajoute dans une liste (type `ArrayList`) le nom du joueur sélectionné et qui affiche la liste de tous les joueurs déjà sélectionnés (donc présents dans cette liste). Cette liste sera placée en session. Vous veillerez à bien initialiser l'`ArrayList` à sa première utilisation. On ajoutera en bas de page un lien permettant de revenir à la page `ListerJoueur`.
1. Sur la page `ListerJoueur`, modifier la génération de la liste des joueurs disponibles en ne faisant maintenant apparaitre que les joueurs non encore sélectionnés.
1. Vérifiez que vous êtes capables d'ajouter une dizaine de joueurs dans votre liste à l'aide de cette page.

Dans cet exercice, les deux servlets `ListerJoueur` et `ChoisirJoueur` partagent de l'information au moyen de la session. Ici il s'agit de la liste des noms de joueurs mais cela pourrait être bien évidemment n'importe quel autre objet java, ce principe s'applique dans de nombreux cas.

### S'il vous reste du temps pour améliorer votre programme.

1. Ajouter des liens sur `ListerJoueur` pour passer d'un poste à un autre facilement.  
1. On souhaite maintenant permettre à l'utilisateur de tout réinitialiser, et donc de supprimer tous les joueurs de la liste. Ajouter un lien pointant sur `ChoisirJoueur` et permettant de réaliser cette action.


### Interaction avec d'autres mécanismes de maintien d'état

1. On souhaite restreindre l'affichage des joueurs à l'équipe favorite de l'utilisateur. Modifier `ListerJoueur` pour rechercher l'équipe dans un cookie. Si ce cookie n'est pas présent, effectuer une redirection vers `ChoisirEquipe`. S'il est présent, utilisez sa valeur pour modifier la requête de selection des joueurs en conséquence.  
