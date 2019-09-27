import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;

@WebServlet("/Palette")
public class Palette extends HttpServlet
{
  public void service( HttpServletRequest req, HttpServletResponse res )
  throws ServletException, IOException
  {
    res.setContentType("text/html;charset=UTF-8");
    PrintWriter out = res.getWriter();
    out.println( "<head><title>servlet first</title>" );
    out.println( "<META content=\"charset=UTF-8\"></head><body><center>" );
    out.println( "<h1>Test de ma Palette</h1>" );
     String hexa = "0123456789ABCDEF";
     
     // -- 3.1
     String rParam = req.getParameter("r");
     if ( rParam == null || rParam.isEmpty() ) rParam = "0";
     int r = Integer.parseInt(rParam);
     if ( r < 0 || r > 15 ) r = 0;
     for ( int i = 0; i < 16; i++ )
      out.println(" <a href='Palette?r="+i+"'>"+i+"</a> | ");

     out.println( "<table>" );
     for ( int v = 0; v < 16; v++ ) 
       {
	  out.print("<tr>");
	  for ( int b = 0; b < 16; b++ ) 
	    {
	       String col = ""+ hexa.charAt(r) + hexa.charAt(v) + hexa.charAt(b);
	       out.println("<td width=10 height=10 bgcolor='#"+col+"'></td>");
	    }
	  out.print("</tr>");
	  
       }
     out.println( "</table>" );
     
    out.println( "<h2>C'est beau !</h2>" );
    out.println( "</center> </body>" );
  }
}
