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

public class updateuser extends HttpServlet {

   public void doGet(HttpServletRequest rq, HttpServletResponse rs) throws IOException
   {
       try
       {
           rs.setContentType("text/html");
           PrintWriter out=rs.getWriter();
           String newemail=rq.getParameter("email");
           String newfirstname=rq.getParameter("firstname");
           String newlastname=rq.getParameter("lastname");
           String password=rq.getParameter("password");
           String newpassword=rq.getParameter("newpassword");
           int count=0;
           Class.forName("org.mariadb.jdbc.Driver").newInstance();
           Connection con=DriverManager.getConnection("jdbc:mariadb://localhost:3306/project?user=root&password=root");
           Statement st=con.createStatement();
           HttpSession session=rq.getSession(false);  
           String email=(String)session.getAttribute("email");  
           String validpassword=null;
           ResultSet ps1= st.executeQuery("select aes_decrypt(unhex(password) , 'MyStrongKey') as password from register  where email='" + email +"';");
           while(ps1.next())
                {
                    validpassword = ps1.getString("password");

                }
           if(validpassword.equals(password))
               {
                   if(newfirstname == null || newfirstname.isEmpty() || newfirstname.equals("null"))
                    {
                    }
                    else
                    {    
                        ps1= st.executeQuery("update register set first_name = '" + newfirstname + "'" + " where email='" + email +"';");
                    }
           
                    if(newlastname == null || newlastname.isEmpty() || newlastname.equals("null"))
                    {
                    }
                    else
                    {
                        ps1= st.executeQuery("update register set last_name = '" + newlastname + "'" + " where email='" + email +"';");
                    }

                    if(newpassword == null || newpassword.isEmpty() || newpassword.equals("null"))
                    {
                    }
                    else
                    {
                        ps1= st.executeQuery("update register set password = hex(aes_encrypt('" + newpassword + "','MyStrongKey'))" + " where email='" + email +"';");
                        count=1;
                    }
                    if(newemail == null || newemail.isEmpty()|| newemail.equals("null"))
                    {
                    }
                    else
                    {
                        ps1= st.executeQuery("update register set email = '" + newemail + "'" + " where email='" + email +"';");
                        count=1;
                    }
                        }
             else
             {
                count=2;
             }
           
           if(count==1)
           {
               out.print("<script>alert('You either changed your email or password , so you are logged out !')</script>");
               session.invalidate();
               out.print("<meta http-equiv=\"refresh\" content=\"0;url=http://localhost:24123/project/\">");
               
           }
           if(count==2)
           {
               out.print("<script>alert('Your current password is wrong can notupdate the profile , Sorry !')</script>");
               out.print("<meta http-equiv=\"refresh\" content=\"0;url=http://localhost:24123/project/profile\">");
           }
           if(count==0)
           {
               out.print(count);
                out.print("<script>alert('Updated your profile')</script>");
                out.print("<meta http-equiv=\"refresh\" content=\"0;url=http://localhost:24123/project/profile\">");
           }
           
       }
       catch(Exception e)
       {    PrintWriter out=rs.getWriter();
           out.print(e.getMessage());
       }
       
    }
}