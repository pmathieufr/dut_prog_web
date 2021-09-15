# TP : Applications JDBC

## Objectifs

- Comprendre la manière dont fonctionne JDBC. 
- Utiliser les méthodes principales de cette API.
- Utiliser plusieurs drivers.

Ces exercices sont supposés être réalisés sous Windows. Bien que les outils utilisés soient disponibles sous Linux ou MacOS, certaines explications sont ici propres à Windows. 

## Le serveur Postgresql

Le département informatique de l'IUT-A a installé un serveur `Postgresql` pour ses étudiants sur une machine nommée `psqlserv`. Chacun d'entre vous possède un compte sur ce serveur, avec le même login que votre login Unix. Il y a plusieurs bases disponibles et la votre se nomme `fi2`. Assurez vous que vous pouvez vous y connecter :

```
psql -h psqlserv -U votrelogin fi2
\d
\q
```

Pour information, il est bien évidemment aussi possible d'installer `Postgresql` sur votre propre machine ou utiliser un serveur existant sur votre réseau. Vous pouvez aussi créer une base "sur le cloud" avec des services comme :
* [Clever Cloud](https://www.clever-cloud.com/fr/) 
* [Heroku](https://www.heroku.com/pricing)
* [ElephantSQL](https://www.elephantsql.com/)
* ou encore Amazon AWS, Google Cloud, Microsoft azure ...
les 3 premiers offrent un plan "test" gratuit qui serait suffisant pour nos exercices.  


Dans une première partie, tous les exercices seront écrits avec la base Postgres en utilisant le driver JDBC `org.postgresql.Driver`.

* Téléchargez la librairie [postgresql-42.2.23.jar](https://jdbc.postgresql.org/download/postgresql-42.2.23.jar). Cette librairie contient l'ensemble des classes définissant le fonctionnement du driver.

* Pour des raisons de simplicité, vous placerez tous les fichiers (drivers, sources, classes) dans le même répertoire, `web/tp2` par exemple.


## Programmes de base

1. Compléter le programme [Create.java](Create.java) qui crée une table `CLIENTS`. Pour cela
  - Modifiez le nom du driver pour utiliser `org.postgresql.Driver`
  - L'url de connexion sera de la forme : `jdbc:postgresql://serveur:port/base`
  - Il faudra utiliser votre login et le mot de passe associé
1. Compiler le programme : `javac Create.java`  
Le driver n'est pas nécessaire au moment de la compilation.
1. Exécuter le programme : `java -cp .;postgresql-42.2.23.jar Create`  
Lors de l'exécution, le classpath java doit contenir le chemin vers la librairie contenant le driver JDBC, mais aussi le chemin vers Create.java
1. Définir la variable système CLASSPATH pour éviter le `-cp`
1. Modifiez le programme pour éviter le throws exception
1. Compléter et tester le programme [Insert.java](Insert.java) qui effectue une insertion de table.
1. Modifier ce programme pour que l’on puisse insérer 1000 clients d’un coup: nom_1, ... ,nom_1000
1. Compléter et tester le programme [Select.java](Select.java) qui effectue une sélection de table
1. Ecrire un nouveau programme Java `Compter.java` qui affiche le nombre de lignes de la table CLIENTS. Attention : il ne doit pas y avoir de `while` : le SGBD sait compter tout seul ;-)

**Dans cet exercice, Postgres a été utilisé comme Serveur et vos programmes Java comme Clients.**


## Indépendance de JDBC et du SGBD : passage à H2

Dans cette partie nous allons illustrer l'intérêt de JDBC. En ne modifiant que le driver et les paramètres de connexion JDBC, nous allons ré-exécuter ces programmes en utilisant cette fois la base de données `H2`. On utilise cette fois `H2` comme serveur de données, les programmes Java étant toujours des clients.

* Contrairement à Postgresql, H2 n'est pas installé sur votre machine. Il va falloir lancer le serveur "à la main".

* Téléchargez la librairie [h2-1.4.200.jar](https://repo1.maven.org/maven2/com/h2database/h2/1.4.200/h2-1.4.200.jar) et placez là dans le répertoire contenant vos sources java. `H2` est lui-même écrit en Java. Cette librairie contient donc à la fois les classes utiles au serveur de données, et les classes implémentant le driver JDBC pour s'y connecter.

* Ouvrez une console Linux dans ce répertoire et lancez le serveur H2 à l'aide de la commande `java -cp h2*.jar org.h2.tools.Server -ifNotExists` 
(Il existe d'autres commandes de lancement mais elles ne permettent pas la création d'une base; seul un pgm en mode embedded pourrait le faire.)

Une fenêtre de navigateur s'ouvre, il s'agit d'une application web inclue dans la librairie permettant d'administrer le serveur que nous venons de lancer. Laissons cela de côté pour le moment, votre serveur H2 est prêt à être utilisé. Pour l'arrêter il suffira de faire `CTRL+C` dans la console où vous l'avez lancé, ou de fermer la console.

Dans vos programmes java, pour se connecter à ce serveur, le driver se nomme `org.h2.Driver`, et l'url à utiliser est de la forme `jdbc:h2:tcp://localhost/chemin/mabaseh2`.  
`localhost` correspond à la machine qui héberge le serveur (port 9092 par défaut).  
`chemin` est l'endroit où la base sera créée sur cette machine.  
`mabaseh2` est le nom du fichier qui stockera la base.

Il n'existe qu'un seul utilisateur pour se connecter : login `sa`  
Vous n'avez pas besoin de mot de passe, une chaine vide conviendra.

1. Modifiez les données de connexion inscrites dans les programmes pour pouvoir utiliser `H2`
1. Compilez et testez maintenant chacun des programmes précédents.  
1. Vous pouvez vérifier à l'aide de l'interface Web de H2 que tout s'est bien passé.

Vos programmes java sont les clients du serveur H2 hébergeant une base de données.  


## Un programme générique

Les programmes que vous avez écrit possèdent le nom du driver et le nom de l’url directement dans le source. Si vous changez de base, il faudra recompiler. Afin d’éviter cela, l’idéal consiste à externaliser ces propriétés dans un fichier texte et faire en sorte que vos programmes aillent lire à l’execution ces différentes propriétés. De cette manière vous n’aurez plus jamais à recompiler, et ce, quel que soit le SGBD utilisé !  
Java offre pour cela un objet tout prêt : [java.util.Properties](https://docs.oracle.com/en/java/javase/16/docs/api/java.base/java/util/Properties.html). Pour utiliser cet objet, il suffit de le charger (methode load à partir d’un descripteur de fichier) et lire les propriétés souhaitées (methode `getProperty`).

1. Réécrire vos programmes de manière à ce que driver, url, login, password soient externalisés via un objet Properties.
1. Vous constatez maintenant qu'il n'est plus necessaire de compiler les programmes pour changer de SGBD. Il suffit de changer de fichier de propriétés.


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
Vous pourrez l'utiliser avec la commande `java -cp .;h2-1.4.200.jar Lister CLIENTS`

Voici quelques pistes pour y parvenir :

- créer une requête SQL de sélection avec le premier argument du programme.  
- selectionner tous les enregistrements à l'aide d'un `SELECT *`  
- déterminer combien de colonnes sont retournées par l'exécution de notre requête Select.  
Un objet obtenu par la méthode [getMetadata()](https://docs.oracle.com/en/java/javase/16/docs/api/java.sql/java/sql/ResultSet.html#getMetaData()) de `ResultSet` pourra nous y aider. La méthode [getColumnCount()](https://docs.oracle.com/en/java/javase/16/docs/api/java.sql/java/sql/ResultSetMetaData.html#getColumnCount()) vous sera utile.
- parcourir le ResultSet et afficher chaque valeur de colonne, sous forme d'une chaine de caractère.



