import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;
import javax.swing.JOptionPane;
import java.util.*; 
import java.lang.*;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpSession;

public class updateprofile extends HttpServlet {

   public void doGet(HttpServletRequest rq, HttpServletResponse rs) throws IOException
   {
       try
       {
           rs.setContentType("text/html");
           PrintWriter out=rs.getWriter();
           String n;
           int count=0;
           String url=rq.getParameter("url");
           Class.forName("org.mariadb.jdbc.Driver").newInstance();
           Connection con=DriverManager.getConnection("jdbc:mariadb://localhost:3306/project?user=root&password=root");
           Statement st=con.createStatement();
           HttpSession session=rq.getSession(false);  
           String email=(String)session.getAttribute("email");  
           ResultSet ps1= st.executeQuery("select * from register");
           while(ps1.next())
                {
                    ps1= st.executeQuery("update register set profile_url = '" + url + "'" + " where email='" + email +"';");

                }
           out.print("<script>alert('Updated your profile')</script>");
           out.print("<meta http-equiv=\"refresh\" content=\"0;url=http://localhost:24123/project/profile\">");
           
           
       }
       catch(Exception e)
       {    PrintWriter out=rs.getWriter();
           out.print(e.getMessage());
       }
       
    }
}