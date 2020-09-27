import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;

@WebServlet("/Ascii")
public class Ascii extends HttpServlet
{
  public void service( HttpServletRequest req, HttpServletResponse res )
  throws ServletException, IOException
  {
    res.setContentType("text/html");
    PrintWriter out = res.getWriter();
    out.println( "<head><title>servlet first</title>" );
    out.println( "</head><body><center>" );
    out.println( "<h1>Ascii v2</h1>" );

    // 3.2
    int nbcol = 5;
    String nbcolParam = req.getParameter("nbcol");
    if ( nbcolParam != null ) {
      try {
        nbcol = Integer.parseInt( nbcolParam );
      } catch( Exception e ) {
        nbcol = 5;
        System.out.println("erreur lecture param nbcol: " + e.getMessage());
      }
    }
    if ( nbcol < 1 ) nbcol = 1;
    if ( nbcol > 16 ) nbcol = 16;

    int charPerTable = (256-32) / nbcol;
    if ( (256-32) % nbcol > 0 ) charPerTable++;


    // 3.3
    out.println("<form method='post'>");
    out.println("<select name=nbcol>");
    for ( int i = 1; i < 15; i++ )
      out.println("<option"+(i==nbcol ? " selected":"")+">"+i+"</option>");
    out.println("</select>");
    out.println("<input type=submit value=Ok>");
    out.println("</form>");

    out.println("<table><tr>");
    int c = 32;
    for ( int i = 32; i < 256; i++ ) {
      out.println("<td>"+(c)+"</td><td>"+((char)c)+"</td>");
      c+=charPerTable;
      if ( c > 255 && i < 255) {
        out.println("</tr><tr>");
        c -= 255 - 32;
      }
    }
    out.println("</tr></table>");

    out.println( "</center> </body>" );
  }
}
