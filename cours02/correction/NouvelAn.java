// Servlet Test.java  de test de la configuration
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;

import java.time.*;
import java.time.temporal.*;

@WebServlet("/NouvelAn")
public class NouvelAn extends HttpServlet
{
  public void service( HttpServletRequest req, HttpServletResponse res ) 
       throws ServletException, IOException
  {
    res.setContentType("text/html;charset=UTF-8");
    PrintWriter out = res.getWriter();
    out.println("<!doctype html>");
    out.println("<head><title>servlet Test</title></head><body><center> ");
    out.println("<h1>Nouvel An</h1> ");
    out.println("<br>");
     
     LocalDateTime today = LocalDateTime.now();
     LocalDateTime newYear = LocalDateTime.of(today.get(ChronoField.YEAR)+1, 1, 1, 0, 0);
     Duration delay = Duration.between(today, newYear);
     long seconds = delay.get( ChronoUnit.SECONDS );
     
     out.println("Il reste " + seconds + " secondes avant la nouvelle année");

     out.println("</body></html> ");
  }
}
