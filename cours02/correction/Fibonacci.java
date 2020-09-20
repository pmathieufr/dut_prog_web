import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;

@WebServlet("/Fibonacci")
public class Fibonacci extends HttpServlet
{
  public void service( HttpServletRequest req, HttpServletResponse res )
  throws ServletException, IOException
  {
    res.setContentType("text/html;charset=UTF-8");
    PrintWriter out = res.getWriter();
    out.println( "<head><title>servlet first</title>" );
    out.println( "<META content=\"charset=UTF-8\"></head><body><center>" );
    out.println( "<h1>Suite de fibonacci</h1>" );
     
     long f1 = 1;
     long f2 = 1;
     long f = 0;
     out.println("1 1");
     for ( int i = 2; i < 30; i++ ) 
       {
	  f = f1 + f2;
	  out.println( " " + f);
	  f2 = f1;
	  f1 = f;
       }
     
     
     
    out.println( "</center> </body>" );
  }
}
