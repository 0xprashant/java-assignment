# JDBC Assignment -3 

**Submitted by - Prashant Saini**

## Task - 1 (Saving employee sata in database)

> index.html

```html
<!DOCTYPE html> 
<html> 
<head> 
<title>Save yourself</title> 
<style>
body{
           background: rgb(2,0,36);
            background: linear-gradient(90deg, rgba(2,0,36,1) 0%, rgba(9,121,116,1) 35%, rgba(0,212,255,1) 100%); }
</style>
</head> 

<body><center>
    <form action="./register" method="post"> 
        <h1>Save yourself in the database</h1>
        <p>Enter your email:</p>  
        <input type="text" name="email"/> 
        <input type="submit"/> 
    </form> 
    
</center></body> 

</html> 
```

> register.java

```java
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

public class register extends HttpServlet {

   public void doPost(HttpServletRequest rq, HttpServletResponse rs)
   {
       try
       {
           rs.setContentType("text/html");
           PrintWriter out=rs.getWriter();
           String a=rq.getParameter("email");
           String n;
           int count=0;
           String query;
           Class.forName("org.mariadb.jdbc.Driver").newInstance();
           Connection con=DriverManager.getConnection("jdbc:mariadb://localhost:3306/java?user=root&password=root");
           Statement st=con.createStatement();
           ResultSet ps1= st.executeQuery("select * from register");
           while(ps1.next())
           {
                n = ps1.getString("email");
                if(a.equalsIgnoreCase(n))
                {
                    out.print("<h1>Your email is already in the database , Redirecting to the homepage in 5Sec....</h1>");
                    out.print("<meta http-equiv=\"refresh\" content=\"5;url=http://localhost:29734/login/\">");
                    count =1;
                    break;         
                }
           }
                if (count==0)
                {
                    ResultSet ps = st.executeQuery("insert into register values('"+a+"');");
        
                    out.print("<meta http-equiv=\"refresh\" content=\"5;url=http://localhost:29734/login/showusers\">");
                    out.print("<h1>Successfully Added your email to Our DB Redirecting in 5Sec.....</h1>");
                }         
       }
       catch(Exception e)
       {

       }
       
    }
}


```

> showusers.java

```java
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;
import javax.swing.JOptionPane;

public class showusers extends HttpServlet {

   public void doGet(HttpServletRequest rq, HttpServletResponse rs)
   {
       try
       {
           
           PrintWriter out=rs.getWriter();
           String query;
           String email;
           Class.forName("org.mariadb.jdbc.Driver").newInstance();
           Connection con=DriverManager.getConnection("jdbc:mariadb://localhost:3306/java?user=root&password=root");
           Statement st=con.createStatement();
           ResultSet ps = st.executeQuery("select * from register");
           out.print("<html><head><style>table, th, td {border: 1px solid black;}body{background: rgb(2,0,36);background: linear-gradient(90deg, rgba(2,0,36,1) 0%, rgba(90,117,7,1) 0%, rgba(0,212,255,1) 100%); }</style></head><body><h1>Emails</h1><table style=\"width:300px;\"><tr><th colspan=\"1\">All Emails</th></tr>");
           while(ps.next())
           {
                email =  ps.getString("email");
                out.print("<tr><td>"+email+"</td></tr>");
           }
            out.print("</table></body></html>");    
           con.close();
       }
       catch(Exception e)
       {

       }
    }
}
```

### Outputs

> index.html

![](/images/image-3.png)

> After submitting a email that is not in database


![](/images/image-4.png)

> After submitting a email that already exist in database

![](/images/image-5.png)

> Redirected to showusers 

![](/images/image-6.png)
