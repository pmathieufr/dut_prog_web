import java.sql.*;

public class Insert
{
  public static void main(String args[]) throws Exception 
  {
      // TODO
      // enregistrement du driver
      // connexion à la base
      Connection con = null;

      // execution de la requete
      // TODO : écrire une requête INSERT
      String query = "";
      PreparedStatement ps = con.prepareStatement( query );
      ps.executeUpdate();
      // fermeture des espaces
      con.close();

      System.out.println("Ok.");
  }
}
