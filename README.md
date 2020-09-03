# Java assignment 2 -- Movie review java application

**Submitted by - Prashant Saini**
**Sql server - MariaDb**
**OS - Linux**

> index.html

```html

<!DOCTYPE html> 
<html> 
<head> 
<title>Movie Review</title> 
</head> 
<body> 
    <form action="./connect" method="post"> 
        <h1>Movie review of Student of the year two</h1>
        <p>Stars in integer upto 5:</p>  
        <input type="text" name="stars"/> 
        <br/> 
        <p>Like(yes/NO):</p>  
        <input type="text" name="like"/> 
        <br/>
        <p>Comment:</p>  
        <input type="text" name="string"/> 
        <br/><br/><br/> 
        <input type="submit"/> 
    </form> 
</body> 
</html> 
```

> connect.java

```java

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;
import javax.swing.JOptionPane;

public class connect extends HttpServlet {

   public void doPost(HttpServletRequest rq, HttpServletResponse rs)
   {
       try
       {
           rs.setContentType("text/html");
           PrintWriter out=rs.getWriter();
           /// client information
           String a=rq.getParameter("stars");
           String b=rq.getParameter("like");
           String c=rq.getParameter("string");
           String query;
           //
           Class.forName("org.mariadb.jdbc.Driver").newInstance();
           Connection con=DriverManager.getConnection("jdbc:mariadb://localhost:3306/java?user=root&password=root");
           Statement st=con.createStatement();     
           query="insert into moviereview values('"+a+"','"+b+"','"+c+"');";
           st.executeUpdate(query);

           out.print("<h1>Review done</h1>" + "You entered<br></br>" + "Stars = " + a + "<br></br>Like = " + b + "<br></br>Comment = " + c);
           
       }
       catch(Exception e)
       {

       }
       
    }
   }
```

## OutPut scrrenshots

![](/images/image-1.png)

![](/images/image-2.png)

```sql
MariaDB [java]> select * from moviereview;
+-------+------+------------------------------------------------------------------------+
| stars | res  | comment                                                                |
+-------+------+------------------------------------------------------------------------+
| 0     | no   |  Bekar movie hai bilkul , isse achcha to tom and jerry dekh lu         |
+-------+------+------------------------------------------------------------------------+
1 row in set (0.000 sec)
```

