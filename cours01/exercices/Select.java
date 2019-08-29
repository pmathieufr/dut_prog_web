import java.sql.*;

public class Select 
{  
  public static void main(String args[]) throws Exception
  {      
      // TODO
      // enregistrement du driver
      // connexion à la base
      Connection con = null; 

      // execution de la requete
      // TODO : écrire une requête SELECT
      String query = ""; 
      
      PreparedStatement ps = con.prepareStatement( query );
      ResultSet rs = ps.executeQuery();
      
      System.out.println("Liste des clients:");
      while (rs.next()) 
      {
        String n = rs.getString("nom");
        String p = rs.getString("prenom");
        int a = rs.getInt("age");
        System.out.println(n + " " + p + " " + a);
      }

      // fermeture des espaces
      con.close();

      System.out.println("Ok.");
  }
}
