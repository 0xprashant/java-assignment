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

public class newgroup extends HttpServlet {

   public void doPost(HttpServletRequest rq, HttpServletResponse rs) throws IOException
   {
       try
       {
           rs.setContentType("text/html");
           PrintWriter out=rs.getWriter();
           String n;
           int count=0;
           String name=null;
           String grpname=rq.getParameter("grpname");
           String members=rq.getParameter("members");
           
           
           Class.forName("org.mariadb.jdbc.Driver").newInstance();
           Connection con=DriverManager.getConnection("jdbc:mariadb://localhost:3306/project?user=root&password=root");
           Statement st=con.createStatement();
           HttpSession session=rq.getSession(false);  
           String email=(String)session.getAttribute("email");  
           ResultSet ps1= st.executeQuery("select * from groups");
           Date date = new Date();  
           SimpleDateFormat formatter = new SimpleDateFormat("YYYY/MM/d");  
           String tdate= formatter.format(date);
           ps1= st.executeQuery("select count(*) from groups");
           while (ps1.next())
           {
               count=Integer.parseInt(ps1.getString("count(*)"));
               
           }
           if(count==0)
           {
               ps1=st.executeQuery("insert into groups values('"+grpname+"' , '"+email+"' , '"+members+" "+email+"' , '"+tdate+"');");
               out.print("<script>alert('Successfully Created the group You will be alloted with the chat')</script>");
               out.print("<meta http-equiv=\"refresh\" content=\"0;url=./personal_message\">");
           }
           if(count!=0){
               ps1= st.executeQuery("select * from groups");
           while(ps1.next())
           {
               
               name=ps1.getString("name");
               if(grpname.equalsIgnoreCase(name))
               {
                   
                   
                   out.print("<script>alert('The group name exist')</script>");
                   out.print("<meta http-equiv=\"refresh\" content=\"0;url=./personal_message\">");
                   break;
               }
               else
               {
                   
                   ps1=st.executeQuery("insert into groups values('"+grpname+"' , '"+email+"' , '"+members+" "+email+"' , '"+tdate+"');");
                   out.print("<script>alert('Successfully Created the group You will be alloted with the chat')</script>");
                   out.print("<meta http-equiv=\"refresh\" content=\"0;url=./personal_message\">");
                   
               }
               
           }}
       }
       catch(Exception e)
       {    PrintWriter out=rs.getWriter();
           out.print(e.getMessage());
       }
       
    }
   
}