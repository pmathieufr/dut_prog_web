import java.sql.*;

public class Select 
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
      String query = "select NOM,PRENOM,AGE from CLIENTS";
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
