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

public class showmessage extends HttpServlet {

   public void doPost(HttpServletRequest rq, HttpServletResponse rs) throws IOException
   {
       try
       {
           rs.setContentType("text/html");
           PrintWriter out=rs.getWriter();
           String n;
           int count=0;
           String from=rq.getParameter("from");
           Class.forName("org.mariadb.jdbc.Driver").newInstance();
           Connection con=DriverManager.getConnection("jdbc:mariadb://localhost:3306/project?user=root&password=root");
           Statement st=con.createStatement();
           HttpSession session=rq.getSession(false);  
           String email=(String)session.getAttribute("email");  
           rq.getRequestDispatcher("menu.html").include(rq, rs);
           ResultSet ps2,ps1= st.executeQuery("select * from message");
           out.print(" <link rel=\"stylesheet\" href=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css\">\n" +
                        "  <script src=\"https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js\"></script>\n" +
                        "  <script src=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js\"></script>");
           out.print("<style>\n" +
                        "msg {\n" +
                        "  background-color: black;\n" +
                        "  width: 200px;\n" +
                        " color: white;\n" +
                        "  padding: 10px;\n" +
                        "  margin: 10px;\n" +
                        "}\n" +
                        "msg1 {\n" +
                        "  background-color: yellowgreen;\n" +
                        "  width: 200px;\n" +
                        " color: black;\n" +
                        "  padding: 10px;\n" +
                        "  margin: 10px;\n" +
                        "}\n" +
                        "</style>");
           ps1= st.executeQuery("select from_email,date,aes_decrypt(unhex(msg),'MyStrongKey') as msg from message where to_email='"+email+"' AND from_email='"+from+"' OR to_email='"+from+"' AND from_email='"+email+"';");
           //ps2= st.executeQuery("select aes_decrypt(unhex(msg),'MyStrongKey') as msg from message where from_email='"+email+"' AND to_email='"+from+"';");
           while (ps1.next() )
           {
               String Allmsg=ps1.getString("msg");
               String frommsg=ps1.getString("from_email");
               //String tomsg=ps1.getString("to_email");
               String date=ps1.getString("date");
             //  String frommsg=ps2.getString("msg");
             if(email.equals(frommsg))
             {
                 out.print("<br></br>");
                 out.print("<h4 style=\"text-align:right;\">"+ "<msg1>"+Allmsg + "</msg1>&#160;&#160;&#160;&#160;&#160;&#160;"+date +"</h4>");
                 
             }
             else
             {
                 out.print("<br></br>");
                 out.print("<h4 style=\"text-align:left;\">"+date + "&#160;&#160;&#160;&#160; " +frommsg + " : &#160;&#160;&#160;" + "<msg>"+Allmsg+"</msg></h4>");
                 
               
           }
           
           }
           out.print("\n<body style=\"background-image: url('./image.png');\">\n");
           out.print("<br></br><br></br><br></br><br></br>");
           out.print("<form onsubmit=\"setTimeout(function(){window.location.reload();},10);\" class=\"form-horizontal\" action=\"./personal_message\" style=\"color:black;font-family:'Courier New';position: fixed; bottom: 0; width:100%;\">\n" +
                        "    <div class=\"form-group\">\n" +
                        "      <label class=\"control-label col-sm-2\" for=\"profile\">Enter Your new message here : </label> \n" +
                        "      <div class=\"col-sm-8\">\n" +
                        "        <input type=\"text\" class=\"form-control\"  placeholder=\"Enter Your Message\" name=\"message\">\n" +
                        "        <input type=\"hidden\" class=\"form-control\"  placeholder=\"Enter Url\" name=\"to_email\" value=\""+from+"\">\n" +
                        "      </div>\n" +
                        "    </div>\n" +
                        "\n" +
                        "    <div class=\"form-group\">        \n" +
                        "      <div class=\"col-sm-offset-2 col-sm-10 \">\n" +
                        "        <button type=\"submit\" class=\"btn btn-default\">Submit</button>\n" +
                        "      </div>\n" +
                        "    </div>\n" +
                        "  </form>");
           
           //out.print("<meta http-equiv=\"refresh\" content=\"0;url=http://localhost:24123/project/showmessage?from=\""+from+">");
           
       }
       catch(Exception e)
       {    PrintWriter out=rs.getWriter();
           out.print(e.getMessage());
       }
       
    }
}