import java.sql.*;

public class Create 
{  
  public static void main(String args[]) throws Exception
  {      
      // enregistrement du driver
      Class.forName("TODO");
      
      // connexion à la base
      String url = "TODO";
      String nom = "TODO";
      String mdp = "TODO";
      Connection con = DriverManager.getConnection(url,nom,mdp);

      // execution de la requete
      String query = "create table CLIENTS (NOM varchar(10), PRENOM varchar(10), AGE int)";
      PreparedStatement ps = con.prepareStatement( query );
      ps.executeUpdate();
      // fermeture des espaces
      con.close();

      System.out.println("Ok.");
  }
}
