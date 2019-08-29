# TP : Applications JDBC

## Objectifs

- Comprendre la manière dont fonctionne JDBC. 
- Utiliser les méthodes principales de cette API.
- Utiliser un driver.

Ces exercices sont supposés être réalisés sous Windows. Bien que les outils utilisés soient disponibles sous Linux ou MacOS, certaines explications sont ici propres à Windows. 

## Lancer un serveur de données H2

Tous les programmes de ce TP seront écrits en utilisant le driver JDBC `org.h2.Driver`.  
On utilise des programmes Java comme client et H2 comme serveur de données.

* Téléchargez la librairie [h2-1.4.197.jar](http://central.maven.org/maven2/com/h2database/h2/1.4.197/h2-1.4.197.jar) et placez là dans votre répertoire contenant vos sources java.

Cette librairie contient à la fois les classes utiles au serveur de données (écrit en Java), et les classes implémentant le driver JDBC pour s'y connecter.

* Ouvrez une console à cet endroit et lancez le serveur H2 à l'aide de la commande `java -jar h2-1.4.197.jar`.

Une fenêtre de navigateur s'ouvre, il s'agit d'une application web inclue dans la librairie permettant d'administrer le serveur que nous venons de lancer. Laissons cela de côté pour le moment, votre serveur H2 est prêt à être utilisé. Pour l'arrêter il suffira de faire `CTRL+C` dans la console où vous l'avez lancé, ou de fermer la console.

Dans vos programmes java, pour se connecter à ce serveur à l'aide du Driver JDBC, l'url à utiliser est de la forme `jdbc:h2:tcp://localhost:9092/chemin/tp1`.  
`localhost:9092` correspond à l'adresse et au port sur lesquelles se connecter.  
`chemin` est l'endroit où la base sera créée.  
`tp1` est le nom de la base.

Il n'existe qu'un seul utilisateur pour se connecter : `sa`  
Vous n'avez pas besoin de mot de passe, une chaine vide conviendra.

## Programmes de base

1. Compléter le programme [Create.java](Create.java) qui crée une table `CLIENTS`. 
1. Compiler le programme : `javac Create.java`  
Le driver n'est pas nécessaire au moment de la compilation.
1. Exécuter le programme : `java -cp .;h2-1.4.197.jar Create`  
Lors de l'exécution, le classpath java doit contenir le chemin vers la librairie contenant le driver JDBC.
1. Modifiez le programme pour éviter le throws exception
1. Compléter et tester le programme [Insert.java](Insert.java) qui effectue une insertion de table.
1. Modifier ce programme pour que l’on puisse insérer 1000 clients d’un coup: nom_1, ... ,nom_1000
1. Compléter et tester le programme [Select.java](Select.java) qui effectue une sélection de table
1. Ecrire un nouveau programme Java qui affiche le nombre de lignes de la table CLIENTS.

**Dans cet exercice, H2 a été utilisé comme Serveur et vos programmes Java comme Clients.**


## Indépendance de JDBC et du SGBD

Dans cette partie nous allons illustrer l'intérêt de jdbc. En ne modifiant que le driver et les paramètres de connexion JDBC, nous allons ré-exécuter ces programmes en utilisant postgreSQL.  

Vous pouvez installer postgreSQL sur votre poste ou utiliser un serveur existant sur votre réseau.  
Vous pouvez aussi créer une base "sur le cloud" avec des services comme :
* [Clever Cloud](https://www.clever-cloud.com/fr/) 
* [Heroku](https://www.heroku.com/pricing)
* [ElephantSQL](https://www.elephantsql.com/)
* ou encore Amazon AWS, Google Cloud, Microsoft azure ...

les 3 premiers offrent un plan "test" gratuit qui sera suffisant pour vos exercices.  
Pour la suite, 

1. Modifiez le nom du driver pour utiliser `org.postgresql.Driver`
1. L'url de connexion sera de la forme : `jdbc:postgresql://serveur:port/base`
1. Il faudra utiliser votre login et le mot de passe associé
1. Compilez et relancez maintenant chacun des programmes précédents.  
Il faut utiliser le [driver JDBC Postgresql](https://jdbc.postgresql.org/download/postgresql-42.2.5.jar) à l'exécution.
1. Vous pouvez vérifier à l'aide de psql que tout s'est bien passé.

Vos programmes java sont les clients du serveur postgres hébergeant une base de données.  
Généralement un serveur est dédié à l'usage de base de données.  
Dans ce cas, vos données ne se trouve pas sur votre ordinateur mais sur ce serveur.


## Un programme générique

Les programmes que vous avez écrit possèdent le nom du driver et le nom de l’url directement dans le source. Si vous changez de base, il faudra recompiler. Afin d’éviter cela, l’idéal consiste à externaliser ces propriétés dans un fichier texte et faire en sorte que vos programmes aillent lire à l’execution ces différentes propriétés. De cette manière vous n’aurez plus jamais à recompiler, et ce, quel que soit le SGBD utilisé !  
Java offre pour cela un objet tout prêt : [java.util.Properties](https://docs.oracle.com/javase/10/docs/api/java/util/Properties.html). Pour utiliser cet objet, il suffit de le charger (methode load à partir d’un descripteur de fichier) et lire les propriétés souhaitées (methode getProperty).

1. Réécrire vos programmes de manière à ce que driver, url, login, password soient externalisés via un objet Properties.



## Et maintenant sous forme d’un bel Objet

Ecrire une classe `DS` permettant charger le fichier properties dans son constructeur. Cet object possédera une méthode "getConnection" qui renvoie une nouvelle connexion vers la base dont les paramètres sont fournis dans le fichier.

1. Modifiez vos programmes pour utiliser cet objet `DS`.  

exemple :

```java
public class TestBdd
{
  public static void main(String args[]) throws Exception
  {
    DS bdd = new DS();
    Connection con = bdd.getConnection();
    con.prepareStatement("delete from CLIENTS")
       .executeUpdate();
    con.close();
  }
}
```

## Pour aller plus loin ...

* Créer un programme `Lister` pour afficher toutes les lignes d'une table dont le nom est passé en paramètre du programme.  
Vous pourrez l'utiliser avec la commande `java -cp .;h2-1.4.197.jar Lister CLIENTS`

Voici quelques pistes pour y parvenir :

- créer une requête SQL de sélection avec le premier argument du programme.  
- selectionner tous les enregistrements à l'aide d'un `SELECT *`  
- déterminer combien de colonnes sont retournées par l'exécution de notre requête Select.  
Un objet obtenu par la méthode [getMetadata()](https://docs.oracle.com/javase/10/docs/api/java/sql/ResultSet.html#getMetaData()) de `ResultSet` pourra nous y aider. La méthode [getColumnCount()](https://docs.oracle.com/javase/10/docs/api/java/sql/ResultSetMetaData.html#getColumnCount()) vous sera utile.
- parcourir le ResultSet et afficher chaque valeur de colonne, sous forme d'une chaine de caractère.



