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
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;  
import java.util.Date;  
public class weight_calculator extends HttpServlet {

   public void doGet(HttpServletRequest rq, HttpServletResponse rs) throws IOException
   {
       try
       {
           rs.setContentType("text/html");
           PrintWriter out=rs.getWriter();
           String n;
           int count=0;
           String tweight=rq.getParameter("todayweight");
           String weight=null,alldate=null;
           Class.forName("org.mariadb.jdbc.Driver").newInstance();
           Connection con=DriverManager.getConnection("jdbc:mariadb://localhost:3306/project?user=root&password=root");
           Statement st=con.createStatement();
           
           HttpSession session=rq.getSession(false);  
           String email=(String)session.getAttribute("email");  
           ResultSet ps1= st.executeQuery("select * from weight where email='" + email +"';");
           rq.getRequestDispatcher("menu.html").include(rq, rs);
           //out.print("<center><img src=\"https://0xprashant.github.io/assets/img/sample/avater1.png\" alt=\"Avatar\" style=\"width:200px\"></center>");
           out.print("<br></br>");
           Date date = new Date();  
           SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");  
            String tdate= formatter.format(date);  
            out.print("<center><h2>Today's date is " + tdate + " " + date+"</h2></center>");
           
            out.print("<br></br>");
            rq.getRequestDispatcher("weight_input.html").include(rq, rs);
            ps1= st.executeQuery("select max(weight),date from weight where email='" + email +"';");
          while(ps1.next())
                {   
                    out.print("<center><h3>max weight till now is : " +ps1.getString("max(weight)") + "</h3></center>");
                    out.print("<center><h3> On date : " +ps1.getString("date")+ "</h3></center>");
       }
            out.print("<head>\n" +
                        "  <meta charset=\"UTF-8\">\n" +
                        "  <title>Weight Calculator</title>\n" +
                        "  <link rel=\"stylesheet\" href=\"./table.css\">\n" +
                        "\n" +
                        "</head>\n" +
                        "<body>\n" +
                        "<!-- partial:index.partial.html -->\n" +
                        "<html lang=\"en\">\n" +
                        "<head>\n" +
                        "	<meta charset=\"utf-8\" />\n" +
                        "	<meta name=\"viewport\" content=\"initial-scale=1.0; maximum-scale=1.0; width=device-width;\">\n" +
                        "</head>\n" +
                        "\n" +
                        "<body>\n" +
                        "<div class=\"table-title\">\n" +
                        "<center><h3>Your previous Weights</h3></center>\n" +
                        "</div>\n" +
                        "<table class=\"table-fill\">\n" +
                        "<thead>\n" +
                        "<tr>\n" +
                        "<th class=\"text-left\">Date</th>\n" +
                        "<th class=\"text-left\">Weight(KG)</th>\n" +
                        "</tr>\n" +
                        "</thead>\n" +
                        "<tbody class=\"table-hover\">");
            
           ps1= st.executeQuery("select * from weight where email='" + email +"';");
           while(ps1.next())
                {
                    weight = ps1.getString("weight");
                    alldate  = ps1.getString("date");
                    out.print("<tr><td class=\"text-left\">" + alldate + "</td>");
                   out.print("<td class=\"text-left\">" + weight +"</td></tr>");
                }
           
          out.print("</tbody></table></body></html>"); 
          
          if(tweight == null || tweight.isEmpty() || tweight.equals("null"))
                    {
                    }
                    else
                    {    
                        ps1= st.executeQuery("insert into weight values('" + email + "' , '" + tdate + "' , '" + tweight +"');");
                        out.print("<meta http-equiv=\"refresh\" content=\"0;url=./weight_calculator\">");
                    }
          
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