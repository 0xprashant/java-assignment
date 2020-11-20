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
import java.time.format.DateTimeFormatter;  
import java.time.LocalDateTime;    
import javax.servlet.http.HttpSession;

public class login extends HttpServlet {

   public void doPost(HttpServletRequest rq, HttpServletResponse rs) throws IOException
   {
       try
       {
           rs.setContentType("text/html");
           PrintWriter out=rs.getWriter();
           String email=rq.getParameter("email");
           String password=rq.getParameter("password");
           String n;
           String pass = null;
           DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");  
            LocalDateTime now = LocalDateTime.now();  
           int count=0;
           String query;
           Class.forName("org.mariadb.jdbc.Driver").newInstance();
           Connection con=DriverManager.getConnection("jdbc:mariadb://localhost:3306/project?user=root&password=root");
           Statement st=con.createStatement();
           ResultSet ps1= st.executeQuery("select * from register");
           while(ps1.next())
           {
                n = ps1.getString("email");
                
                if(email.equalsIgnoreCase(n))
                {
                    
                    count =1;
                    break;         
                }
           }
           if (count==1)
           {
               ps1= st.executeQuery("select aes_decrypt(unhex(password) , 'MyStrongKey') as password from register where email='" + email + "'");
               while(ps1.next())
                {
                    pass= ps1.getString("password");
               
                }
               if(password.equals(pass))
               {
                     HttpSession session=rq.getSession(true);
                     session.setAttribute("email",email);
                     rs.sendRedirect("./Dashboard");
                     
                     
               }
               else
               {
                   out.print("<script>alert('Wrong Email or Password !')</script>");
                    out.print("<meta http-equiv=\"refresh\" content=\"0;url=index.html\">");
               }
           }
                if (count==0)
                {
                    
                    out.print("<script>alert('You are not signed up ! Signup now')</script>");
                   out.print("<meta http-equiv=\"refresh\" content=\"0;url=index.html\">");
                }         
       }
       catch(Exception e)
       {    PrintWriter out=rs.getWriter();
           out.print(e.getMessage());
       }
       
    }
}