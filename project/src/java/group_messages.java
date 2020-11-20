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
import java.text.SimpleDateFormat;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;  
import java.util.Date;

public class group_messages extends HttpServlet {

   public void doPost(HttpServletRequest rq, HttpServletResponse rs) throws IOException
   {
       try
       {
           rs.setContentType("text/html");
           PrintWriter out=rs.getWriter();
           String n;
           int count=0;
           String grpname=rq.getParameter("grpname");
           
           String message=rq.getParameter("message");
           Class.forName("org.mariadb.jdbc.Driver").newInstance();
           Connection con=DriverManager.getConnection("jdbc:mariadb://localhost:3306/project?user=root&password=root");
           Statement st=con.createStatement();
           HttpSession session=rq.getSession(false);  
           String email=(String)session.getAttribute("email");  
           Date date = new Date();  
           SimpleDateFormat formatter = new SimpleDateFormat("YYYY/MM/d H:M:s");  
            String tdate= formatter.format(date);
           rq.getRequestDispatcher("menu.html").include(rq, rs);
           ResultSet ps2,ps1= st.executeQuery("select * from message");
           out.print(" <link rel=\"stylesheet\" href=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css\"><script src=\"https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js\"></script><script src=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js\"></script>");
           out.print("<style>\n" +"msg {\n" +"  background-color: black;\n" +"  width: 200px;\n" +" color: white;\n" +"  padding: 10px;\n" +"  margin: 10px;\n" +"}\n" +"msg1 {\n" +"  background-color: yellowgreen;\n" +"  width: 200px;\n" +" color: black;\n" +"  padding: 10px;\n" +"  margin: 10px;\n" +"}\n" +"</style>");
           ps1= st.executeQuery("select * from group_messages where grpname='"+grpname+"';");
           //out.print("select * from group_messages where name='"+grpname+"';");
           while(ps1.next())
           {
               String from=ps1.getString("from_email");
               String msg=ps1.getString("message");
               if(email.equals(from))
                {
                    out.print("<br></br>");
                    out.print("<h4 style=\"text-align:right;\">"+ "<msg1>"+msg + "</msg1>&#160;&#160;&#160;&#160;&#160;&#160;"+tdate +"</h4>");

                }
               else
                {
                    out.print("<br></br>");
                    out.print("<h4 style=\"text-align:left;\">"+tdate + "&#160;&#160;&#160;&#160; " +from + " : &#160;&#160;&#160;" + "<msg>"+msg+"</msg></h4>"); 
                }
               
           }
           
           
           
           out.print("\n<body style=\"background-image: url('./image.png');\">\n");
           //out.print("<br></br><br></br><br></br><br></br>");
           out.print("<form onsubmit=\"setTimeout(function(){window.location.reload();},10);\" class=\"form-horizontal\" action=\"./group_messages\" style=\"color:black;font-family:'Courier New';position: fixed; bottom: 0; width:100%;\">\n" +
                        "    <div class=\"form-group\">\n" +
                        "      <label class=\"control-label col-sm-2\" for=\"profile\">Enter Your new message here : </label> \n" +
                        "      <div class=\"col-sm-8\">\n" +
                        "        <input type=\"text\" class=\"form-control\"  placeholder=\"Enter Your Message\" name=\"message\">\n" +
                        "        <input type=\"hidden\" class=\"form-control\"  placeholder=\"Enter Url\" name=\"grpname\" value=\""+grpname+"\">\n" +
                                "        <input type=\"hidden\" class=\"form-control\"  placeholder=\"Enter Url\" name=\"from_email\" value=\""+email+"\">\n" +
                        "      </div>\n" +
                        "    </div>\n" +
                        "\n" +
                        "    <div class=\"form-group\">        \n" +
                        "      <div class=\"col-sm-offset-2 col-sm-10 \">\n" +
                        "        <button type=\"submit\" class=\"btn btn-default\">Submit</button>\n" +
                        "      </div>\n" +
                        "    </div>\n" +
                        "  </form>");
       }
       
       
       
       catch(Exception e)
       {    PrintWriter out=rs.getWriter();
           out.print(e.getMessage());
       }
       
    }
}