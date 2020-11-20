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

public class secret extends HttpServlet {

   public void doGet(HttpServletRequest rq, HttpServletResponse rs) throws IOException
   {
       try
       {
           rs.setContentType("text/html");
           PrintWriter out=rs.getWriter();
           String n;
           int count=0;
           String secret=rq.getParameter("secret");
           String password=rq.getParameter("password");
           Class.forName("org.mariadb.jdbc.Driver").newInstance();
           String validpassword=null;
           Connection con=DriverManager.getConnection("jdbc:mariadb://localhost:3306/project?user=root&password=root");
           Statement st=con.createStatement();
           HttpSession session=rq.getSession(false);  
           String email=(String)session.getAttribute("email");  
           ResultSet ps1= st.executeQuery("select * from secret");
           rq.getRequestDispatcher("menu.html").include(rq, rs);
           out.print("<center><h1>Store Anything You Want</h1></center>");
           //rq.getRequestDispatcher("background.html").include(rq, rs); 
           
           
      
               
                   if(secret == null || secret.isEmpty() || secret.equals("null"))
                    {
                    }
                   else
                    {    
                        
                        
                            ps1= st.executeQuery("insert into secret values('"+ email + "', hex(aes_encrypt(\"" +secret +"\" , 'MyStrongKey')));");
                            
                           // out.print("insert into secret values('"+ email + "', hex(aes_encrypt(\"" +secret +"\" , 'MyStrongKey')));");
                           out.print("<script>alert('We have stored it very safe')</script>");
                            out.print("<meta http-equiv=\"refresh\" content=\"0;url=./secret\">");
                    
                        
                    }
          out.print("\n<style>\ndiv {\n" +
                    "    \n" +
                    "    text-align: center;\n" +
                    "}\n" +
                    "\n" +
                    ".Typewriter__wrapper{\n" +
                    "    color: Blue;\n" +
                    "    font-size: 30px;\n" +
                    "}\n" +
                    "\n" +
                    ".Typewriter__cursor{\n" +                                                                            
                    "    color: red;\n" +
                    "    font-size: 40px;\n" +
                    "}\n</style>");
         
        out.print("<div id=\"app\"></div>\n" +
                    "  <script src=\"https://unpkg.com/typewriter-effect@latest/dist/core.js\"></script>");
        out.print("<script>\nvar app = document.getElementById('app');\n" +
                    "\n" +
                    "var typewriter = new Typewriter(app, {\n" +
                    "    loop: true,\n" +
                    "    delay: 2\n" +
                    "});\n" +
                    "\n" +
                    "typewriter.typeString('Storing Here means :~$ ')\n" +
                                        "    .typeString('Safe ')\n" +
                                        "    .pauseFor(1000)\n" +
                                        "    .deleteChars('5')\n" +
                                        "    .typeString('Secure ')\n" +
                                        "    .pauseFor(1000)\n" +
                                        "    .deleteChars(7)\n" +
                                        "    .typeString('TrustWorthy ')\n" +
                                        "    .pauseFor(1000)\n" +
                                        "    .deleteChars(13)\n" +
                                        "    .typeString(' Dont hesitate Go ahead ! ')\n" +
                                        "    .pauseFor(10000)\n" +
                                        "    .start();</script>");

           out.print("<br></br>");
           
           rq.getRequestDispatcher("secret.html").include(rq, rs); 
           ps1= st.executeQuery("select count(*) from secret where email='"+email+"';");
           while(ps1.next())
           {
               String cn=ps1.getString("count(*)");
               out.print("<br></br>");
               out.print("<center><h3>You have Total number of <strong>"+cn+"</strong> Secrets stored ! , fill in the password to view them Securely </h3></center>");
           }
           out.print("<br></br>");
           rq.getRequestDispatcher("passforsecret.html").include(rq, rs); 
           ps1= st.executeQuery("select aes_decrypt(unhex(password) , 'MyStrongKey') as password from register  where email='" + email +"';");
           while(ps1.next())
                {
                    validpassword = ps1.getString("password");

                }
           if(validpassword.equals(password))
               {
                   if(password == null || password.isEmpty() || password.equals("null"))
                    {
                    }
                    else
                    {    
                        ps1= st.executeQuery("select aes_decrypt(unhex(secret_text) , 'MyStrongKey') as secret from secret  where email='" + email +"';");
                    }
       }
           else{
               if(password == null || password.isEmpty() || password.equals("null"))
                    {
                    }
                    else
                    {    
                        out.print("<script>alert('Wrong Password can not Store anything !')</script>");
                         out.print("<meta http-equiv=\"refresh\" content=\"0;url=./secret\">");
                    }
               
           }
           out.print("<br></br>");
           while(ps1.next())
                {
                    String allsecret = (ps1.getString("secret"));
                    out.print("<textarea type=\"number\" class=\"form-control\" required id=\"exampleFormControlTextarea1\" rows=\"10\" \">"+allsecret+"</textarea><br></br>");

                }
       }
       
       catch(Exception e)
       {    PrintWriter out=rs.getWriter();
           out.print(e.getMessage());
       }
       
    }
}