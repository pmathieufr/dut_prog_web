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
    out.println( "<h1>Ascii</h1>" );

    out.println("<table>");
    for ( int c = 32; c < 256; c++ )
      out.println("<tr><td>"+(c)+"</td><td>"+((char)c)+"</td></tr>");
    out.println("</table>");

    out.println( "</center> </body>" );
  }
}
