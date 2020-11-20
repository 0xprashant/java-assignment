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

public class Dashboard extends HttpServlet {

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
           out.print(" <link rel=\"stylesheet\" href=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css\">\n" +
"  <script src=\"https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js\"></script>\n" +
"  <script src=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js\"></script>");
           HttpSession session=rq.getSession(false);  
           String email=(String)session.getAttribute("email");  
           ResultSet ps1= st.executeQuery("select first_name,last_name,profile_url from register where email='" + email +"'");
           rq.getRequestDispatcher("menu.html").include(rq, rs);
           out.print("<center><h1>Dashboard</h1></center>");
            
           while(ps1.next())
                {
                    firstname = ps1.getString("first_name");
                    lastname = ps1.getString("last_name");
                    url=ps1.getString("profile_url");
                }
           if(url == null || url.isEmpty() || url.equals("null"))
           {
               
           }
           else
           {
               out.print("<center><img src=\""+ url +"\" alt=\"Avatar\" style=\"width:200px\"></center><br></br>");
           }
           //out.print("<a href=\"./logout\"> Logout</a>");
          //out.print("<br><center>" +firstname + " " + lastname + "</center></br>");
          out.print("\n<body style=\"background-image: url('./image.png');\">\n");
          String str = firstname + " " + lastname;
          //rq.getRequestDispatcher("background.html").include(rq, rs);
          out.print("\n<style>\ndiv {\n" +
                    "    \n" +
                    "    text-align: center;\n" +
                    "}\n" +
                    "\n" +
                    ".Typewriter__wrapper{\n" +
                    "    color: #33cc33;\n" +
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
                    "typewriter.typeString('Hello :~$ ')\n" +
                                        "    .typeString('"+firstname+ " " + lastname+  "')\n" +
                                        "    .pauseFor(500)\n" +
                                        "    .deleteChars("+str.length()+")\n" +
                                        "    .typeString('How Are you ?')\n" +
                                        "    .pauseFor(1000)\n" +
                                        "    .deleteChars(13)\n" +
                                        "    .typeString('How can Prashant help you ?')\n" +
                                        "    .pauseFor(1000)\n" +
                                        "    .deleteChars(27)\n" +
                                        "    .typeString('Scroll Down For More !')\n" +
                                        "    .pauseFor(6000)\n" +
                                        "    .start();</script>");
        out.print("<br></br>");
             rq.getRequestDispatcher("dashboardlinks.html").include(rq, rs);
             out.print("<br></br>");
             out.print("<div style=\"position: relative;\">\n" +
"      \n" +
"        <center><h4 style=\"color:black;font-family:'Courier New'; bottom: 0; width:100%;\">Made by 0xPrashant with <i class=\"fa fa-heart\" data-placement=\"bottom\" style=\"font-size:22px;color:red\"></i></h4></center>\n" +
"    </div>");
             out.print("");
       }
       catch(Exception e)
       {    PrintWriter out=rs.getWriter();
           out.print(e.getMessage());
       }
       
    }
}