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

public class personal_message extends HttpServlet {

   public void doGet(HttpServletRequest rq, HttpServletResponse rs) throws IOException
   {
       try
       {
           rs.setContentType("text/html");
           PrintWriter out=rs.getWriter();
           String n;
           int count=0;
           String chats=null,grpchat=null;
           String[] members = null;
           String to_email=rq.getParameter("to_email");
           String message=rq.getParameter("message");
           Class.forName("org.mariadb.jdbc.Driver").newInstance();
           Connection con=DriverManager.getConnection("jdbc:mariadb://localhost:3306/project?user=root&password=root");
           Statement st=con.createStatement();
           HttpSession session=rq.getSession(false);  
           String email=(String)session.getAttribute("email");  
           ResultSet ps1= st.executeQuery("select * from register");
           rq.getRequestDispatcher("menu.html").include(rq, rs);
           out.print("\n<body style=\"background-image: url('./image.png');\">\n");
           out.print("<br></br>");  
           Date date = new Date();  
           SimpleDateFormat formatter = new SimpleDateFormat("YYYY/MM/d H:M:s");  
            String tdate= formatter.format(date);
           rq.getRequestDispatcher("pm.html").include(rq, rs);
           
           if(to_email == null || to_email.isEmpty() || to_email.equals("null"))
                    {
                    }
           else
           {    if(to_email.equals(email) || to_email.equalsIgnoreCase(email))
                    {
                        out.print("<script>alert('You cant sent email to yourself')</script>");
                        out.print("<meta http-equiv=\"refresh\" content=\"0;url=./personal_message\">");
                    }
                else
                   {
                       ps1= st.executeQuery("insert into message values('"+ email + "', '"+ to_email + "',hex(aes_encrypt(\"" +message +"\" , 'MyStrongKey')) , '"+tdate+"');");
                       out.print("<meta http-equiv=\"refresh\" content=\"0;url=./personal_message\">");
                   } 
           }
            
           out.print("<style>\n" +
                    ".button {\n" +
                    "  border: none;\n" +
                    "  color: white;\n" +
                    "  padding: 16px 50px;\n" +
                    "  text-align: center;\n" +
                    "  text-decoration: none;\n" +
                    "  display: inline-block;\n" +
                    "  font-size: 16px;\n" +
                    "  margin: 4px 2px;\n" +
                    "  transition-duration: 0.4s;\n" +
                    "  cursor: pointer;\n" +
                    "}\n" +
                    "\n" +
                    ".button1 {\n" +
                    "  background-color: white; \n" +
                    "  color: black; \n" +
                    "  border: 2px solid #4CAF50;\n" +
                    "}\n" +
                    "\n" +
                    ".button1:hover {\n" +
                    "  background-color: #4CAF50;\n" +
                    "  color: white;\n" +
                    "}</style>");
           
           ps1= st.executeQuery("select count(distinct from_email) as msg from message where to_email='"+email+"';");
           while(ps1.next())
           {
               chats=ps1.getString("msg");
               break;
               
           }    
           out.print("<br></br>");
           ps1= st.executeQuery("select * from groups");
           while(ps1.next())
           {
               String grpname=ps1.getString("name");
               
               if(ps1.getString("members").contains(email))
               {
                   
                   out.print("<body>\n" +
                            "\n" +
                            "<center><form action=\"./group_messages\" method=\"post\"><h2>You have a group chat from Group : "+grpname+"          <input type=\"hidden\" id=\"custId\" name=\"grpname\" style=\"float: right\" value=\""+grpname+"\"><button type=\"submit\" class=\"button button1\">Goto Chat</button></form></h2></center>\n" +
                            "");
               }
               
               
               
           }
           out.print("<center><h3>You have Total Number of <strong>" +chats+" </strong>chats</h3></center>");
           ps1= st.executeQuery("select distinct from_email as msg from message where to_email='"+email+"';");
           while(ps1.next())
           {
               chats=ps1.getString("msg");
               out.print("<body>\n" +
                            "\n" +
                            "<center><form action=\"./showmessage\" method=\"post\"><h2>You have chat from : "+chats+"          <input type=\"hidden\" id=\"custId\" name=\"from\" style=\"float: right\" value=\""+chats+"\"><button type=\"submit\" class=\"button button1\">Goto Chat</button></form></h2></center>\n" +
                            "");
               
           }
           out.print("<div style=\"position: relative;\">\n" +
                        "      \n" +
                        "        <center><h4 style=\"color:black;font-family:'Courier New';bottom: 0; width:100%;\">Made by 0xPrashant with <i class=\"fa fa-heart\"  style=\"font-size:22px;color:red\"></i></h4></center>\n" +
                        "    </div>");
       }
       catch(Exception e)
       {    PrintWriter out=rs.getWriter();
           out.print(e.getMessage());
       }
       
    }
}