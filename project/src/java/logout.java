import java.io.IOException;  
import java.io.PrintWriter;  
  
import javax.servlet.ServletException;  
import javax.servlet.http.HttpServlet;  
import javax.servlet.http.HttpServletRequest;  
import javax.servlet.http.HttpServletResponse;  
import javax.servlet.http.HttpSession;  
public class logout extends HttpServlet {  
        protected void doGet(HttpServletRequest request, HttpServletResponse response)  
                                throws ServletException, IOException {  
            response.setContentType("text/html");  
            PrintWriter out=response.getWriter();  
              
            
              
            HttpSession session=request.getSession();  
            session.invalidate();  
              
            out.print("<script>alert('Logged out successfully')</script>");  
            out.print("<meta http-equiv=\"refresh\" content=\"0;url=../project\">");
              
            out.close();  
    }  
} 