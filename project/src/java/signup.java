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

public class signup extends HttpServlet {

   public void doPost(HttpServletRequest rq, HttpServletResponse rs) throws IOException
   {
       try
       {
           rs.setContentType("text/html");
           PrintWriter out=rs.getWriter();
           String email=rq.getParameter("email");
           String firstname=rq.getParameter("firstname");
           String lastname=rq.getParameter("lastname");
           String password=rq.getParameter("password");
           String n;
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
                    out.print("<script>alert('You are already signed up')</script>");
                    out.print("<meta http-equiv=\"refresh\" content=\"0;url=index.html\">");  
                    count =1;
                    break;         
                }
           }
                if (count==0)
                {
                    ps1 = st.executeQuery("insert into register values('"+firstname+"' , '" + lastname + "', '" + email + "' , hex(aes_encrypt('" + password + "','MyStrongKey')) , NULL    );");
                    out.print("<script>alert('You are signed up ! Login now')</script>");
                    out.print("<meta http-equiv=\"refresh\" content=\"0;url=index.html\">");
                }         
       }
       catch(Exception e)
       {    PrintWriter out=rs.getWriter();
           out.print(e.getMessage());
       }
       
    }
}