# DAO : Data Acess Object

## Objectif :

Un objet d’accès aux données est un patron de conception qui permet de regrouper les accès aux données persistantes dans des
classes à part plutôt que de les disperser dans le code. C’est aussi ce que l’on appelle un "mapping objet-relationnel". L’idée générale
consiste à regrouper tous les accès à la base de données dans ces objets afin de favoriser une conception type MVC (détails
[Wikipedia](http://fr.wikipedia.org/wiki/Objet_d%27acc%C3%A8s_aux_donn%C3%A9es) ou [oracle](http://www.oracle.com/technetwork/java/dataaccessobject-138824.html)).

## Réutilisabilité d'un DAO

1. Créez une table `joueur(jno,pseudo,email,pwd,elo)` (jno et elo étant des int) et alimentez cette table de quelques données quelconques (on pourra s'inspirer de [wikipedia](https://fr.wikipedia.org/wiki/Grand_ma%C3%AEtre_international) ou prendre le fichier `joueur.sql`).
Créez un objet POJO `Joueur.java` qui permettra de recevoir les données de ce joueur. Ajoutez une méthode `toString` à cet objet qui affiche toutes les caractéristiques du joueur sauf son mot de passe.  
Ecrire un programme java classique `AfficherJoueur.java` avec un `main` qui prend en argument un identifiant, effectue classiquement une requête select, charge les données dans un objet `Joueur` et affiche les informations de cet objet grâce à son `toString`.
1. Si on souhaite créer une servlet ou une JSP qui affiche un joueur il est nécessaire de réécrire une seconde fois le même code. Ce qui n'est pas profitable pour la maintenance, l'évolution et la correction des différents programmes. Il est préférable de factoriser le code servant à obtenir un Joueur.  
Créez un objet `JoueurDao.java` dans lequel vous implémenterez une méthode `Joueur findById(int id)`. Dans un premier temps on placera le code de connexion dans cette méthode. Modifier votre programme `AfficherJoueur.java` pour utiliser votre Dao.
1. Créez une JSP qui affiche un joueur `AfficherJoueur.jsp`. Pour cela importez le Dao, recherchez le joueur dont l'identifiant est passé en paramètre de la requête et affichez les informations du joueur via `toString`. On ne ne s'occupe pas de la mise en forme dans un premier temps. Votre code est maintenant utilisable dans différentes situation (programme, jsp, servlet ...)

## Factorisation de code

1. Ajoutez une méthode `void create(Joueur joueur)` dans le Dao qui permet d'ajouter les caractéristiques du joueur en base via un `insert`.  
1. Créer un formulaire html (dans un fichier html ou jsp) permettant de saisir les informations du joueur. Créer une servlet `AjouterJoueur` qui utilisera votre Dao pour insérer les données saisies dans le formulaire. Votre servlet doit donc avoir une méthode `doPost` ou `service` qui récupère les paramètres de la requête pour créer une instance de `Joueur` et y mettre les attributs `jno, pseudo, email, pwd` et `elo`. Vous pouvez vérifier au passage la validité de certains paramètres (par exemple que elo est bien numérique et positif).
1. Le code permettant de vous connecter à la base est probablement dupliqué dans votre `create` et `findById`. Il est préférable de mettre ce code dans un objet. Réutilisez pour cela l'objet `DS` réalisé lors du TP2 dans le DAO pour obtenir la connexion. Depuis java 7, une nouvelle forme de try/catch nommée [try-with-resource](https://docs.oracle.com/javase/tutorial/essential/exceptions/tryResourceClose.html) permet de gérer plus facilement la fermeture des ressources telles que les connexions JDBC. Le code suivant est l'équivalent d'un try/catch/finally et close sur la connexion :
```java
try (Connection conn = ds.getConnection()) {
  // ... requêtes en utilisant conn
} catch( SQLException e ) {
  // ... afficher un message d'erreur
}
```
Lorsque l'on sort du bloc try, la ressource indiquée entre parenthèse (de type `AutoCloseable`) est automatiquement fermée par un appel à la méthode `close`. Les éventuels blocks catch et finally sont ensuite appelés. Utiliser ce modèle de code dans votre find et votre create.

## Vers un 'CRUD' complet

*rappel*: On nomme 'CRUD' les opérations élémentaires : Create, Retrieve, Update et Delete. Cela correspond aux clauses SQL `INSERT`, `SELECT`, `UPDATE`, `DELETE`.

1. Ajouter une méthode `List<Joueur> findAll()` qui construit une liste d'objets Joueur avec toutes les données de la table.  
  Tester cette méthode dans une jsp `listerJoueurs.jsp` qui affiche tous les joueurs.
1. Ajouter une méthode `delete( int id )` dans le Dao qui permet de supprimer un joueur. Modifier la jsp `listerJoueurs.jsp` pour permettre d'effacer n'importe quel joueur de la liste. Vous pouvez pour cela ajouter un lien `effacer` au bout de chaque ligne.


## Indépendance du DAO

1. Supposons que l'on souhaite brancher notre application à une autre base (H2 ou Postgres), que faut-il modifier ? JDBC se voulant indépendant de la base, vous ne devriez pas avoir grand chose à modifier. Essayer de faire fonctionner tout votre code avec une base H2.
1. Si l'on souhaite pouvoir créer un DAO qui fonctionne avec un fichier csv plutôt qu'une base de données, il faut par contre changer une partie de votre application. L'avantage du pattern DAO est que  **toutes** les modifications à réaliser sont uniquement dans le code de cette classe. Plutôt que de la modifier, nous allons créer deux implémentations différentes d'un même DAO. Renommez `JoueurDao` en `JoueurJdbcDao` et créez une interface `JoueurDao` avec les signatures des méthodes `findById`, `findAll` etc ... Modifier la classe `JoueurJdbcDao` pour qu'elle implémente `JoueurDao`. Enfin, créer un nouveau DAO nommé `JoueurCsvDao`. Implémentez le `findById` et le `findAll` en utilisant les données contenues dans un fichier CSV (on pourra par exemple prendre `joueur.csv`).
Modifier `listerJoueur.jsp` pour utiliser cette implémentation du DAO ... normalement votre JSP n'a pas changé et continue de fonctionner.


**Conclusion**. Le pattern DAO permet de factoriser le code d'accès aux données d'une application et de ré-utiliser ce code à différents endroit. Il fait le lien entre le monde "Objet" et le mode de persistance (JDBC, fichier ...). Il permet aussi de faciliter la maintenance et l'évolution sur le long terme en regroupant en un même endroit l'ensemble des opérations CRUD des données.


