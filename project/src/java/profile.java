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

public class profile extends HttpServlet {

   public void doGet(HttpServletRequest rq, HttpServletResponse rs) throws IOException
   {
       try
       {
           rs.setContentType("text/html");
           PrintWriter out=rs.getWriter();
           String n;
           int count=0;
           String firstname=null,lastname=null,url=null;
           Class.forName("org.mariadb.jdbc.Driver").newInstance();
           Connection con=DriverManager.getConnection("jdbc:mariadb://localhost:3306/project?user=root&password=root");
           Statement st=con.createStatement();
           
           HttpSession session=rq.getSession(false);  
           String email=(String)session.getAttribute("email");  
           ResultSet ps1= st.executeQuery("select first_name,last_name,profile_url from register where email='" + email +"'");
           rq.getRequestDispatcher("menu.html").include(rq, rs);
           out.print("<center><h1>Update Your Profile</h1></center>");
           
           while(ps1.next())
            {
                firstname = ps1.getString("first_name");
                lastname = ps1.getString("last_name");
                url = ps1.getString("profile_url");
                
                
            }
           
           //out.print("<a href=\"./logout\"> Logout</a>");
           out.print("<br></br>");
           if(url == null || url.isEmpty() || url.equals("null"))
           {
               
           }
           else
           {
               out.print("<center><img src=\""+ url +"\" alt=\"Avatar\" style=\"width:200px\"></center>");
           }
           //out.print("update register set password = hex(aes_encrypt('" + firstname + "',('MyStrongKey'))" + " where email='" + email +"';");
           out.print("<br></br>");
           rq.getRequestDispatcher("update-profile.html").include(rq, rs); 
           out.print("<br></br>");
out.print("<div style=\"position: relative;\">\n" +
"      \n" +
"        <center><h4 style=\"color:black;font-family:'Courier New'; bottom: 0; width:100%;\">Made by 0xPrashant with <i class=\"fa fa-heart\" data-placement=\"bottom\" style=\"font-size:22px;color:red\"></i></h4></center>\n" +
"    </div>");       }
       catch(Exception e)
       {    
       }
       
    }
}