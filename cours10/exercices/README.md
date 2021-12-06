# Modèle Vue Contrôleur

## Exercice 1

L'objectif de cet exercice consiste à mettre en place un MCV trivial.
- Le modèle est constitué d'un "Dao" trivial (`DAO.java`), qui, au lieu de se connecter à la base de données, gère une liste d'objets créés en dur dans le constructeur de ce DAO. Par exemple, une liste de String. On écrira deux méthodes dans ce modèle : `findAll` qui renvoie la liste, et `findById` qui renvoie le nième object (modulo la taille) de la liste `n` étant passé en paramètre de la méthode.
- Ecrire un contrôleur pour ce modèle (`Control.java`), qui prend en paramètre l'action choisie. Le contrôleur gère deux actions : `vignette` ou `liste` et appelle dans le premier cas, une vue qui affiche le nième object de la liste (`vignette.jsp`), `n` étant passé en paramètre de la requête en plus de l'action, et dans le second cas, une vue qui affiche la liste totale de ces objets (`liste.jsp` , sans paramètre cette fois).  
- Ajouter à la vue `Vignette.jsp` un titre avec l'indice `n` qui a  été demandé.
- Idéalement on peut maintenant placer l'ensemble des vues dans le répertoire `WEB-INF` afin d'en sécuriser l'accès. On pourra d'ailleurs peaufiner encore en créant 3 répertoires : `modele`,`vue` et `controleur` et y ranger les objets correspondants.

## Exercice 2


Cet exercice a pour but de gérer les parties d'un jeu à deux joueurs, par exemple celui du précédent TP sur les échecs (mais cet exercice est indépendant du jeu choisi). On souhaite créer les outils nécessaires pour voir ou pour modifier une partie.

* Après avoir créé la table `partie(pno, jno1, jno2, date, statut, temps, gagnant)`, faire le Pojo `Partie.java` et le DAO correspondant.

* Dans le contrôleur :
    * utiliser le DAO joueur pour charger les deux joueurs concernés (réutiliser le DAO du TP précédent).
    * utiliser le DAO partie pour charger les données de la partie (passée en paramètre).
	* si action  
	  = "voir" afficher `view.jsp`  
	  = "modifier" afficher `edit.jsp`  

* Dans les vues :
    * `view.jsp` : afficher les données d'une partie.
    * `modifier.jsp` : afficher un formulaire qui permet de modifier les données d'une partie. Dans ce formulaire: 
        * `joueur` : une liste déroulante affichant le pseudo de chacun et associant en valeur leur `jno`  
        * `date` : un calendrier (`type=date`)  
	* `statut` : une liste déroulante fixe (1 = non commencée, 2 = en cours, 3 = terminée)  

* Puis ajouter au contrôleur les actions
 	* `list` (affichage de toutes les parties)
 	* `delete` (suppression d'une partie)


## exercice 3

*Objectif* : Créer un contrôleur pour joueur une partie d'echec

* les actions du contrôleur :
	* creerPartie
		crée un board et stock le plateau dans la base ou dans un fichier
	* afficherPlateau
		charge la partie et affiche le plateau</br>
		si c'est à moi de jouer : je peux cliquer sur une piece
	* afficheDestination
		(idem afficher plateau et ...)<br/>
		affiche la destination possible d'une pièce passée en paramètre<br/>
		(mais uniquement si c'est à moi de joueur)
	* jouer
		(idem afficher plateau)
		+ joue la pièce si c'est à moi de jouer<br/>
		paramètre : case origine et case destination<br/>
		check si la partie est terminée et met à jour la BDD (partie)

* Dans la vue.
	- afficher le plateau
		si c'est à moi : les pions de ma couleur on un lien vers afficheDestination
	- mettre en surbrillance rouge la case d'origine choisi
	- mettre en surbrillance vert la case d'origine possible
		les case de destination ont un lien vers l'action joueur
