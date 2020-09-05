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


> design of homepage

![](/images/image-7.png)

> design of showusers4

![](/images/image-8.png)

## Task 2 - Making a Crud Application in JDBC

### features
- Register (Already registered user will not able to register)
- View all registered users
- Update the email (A user cant update to email that is already taken by someone in database)
- Delete your email (A user cant delete email that is not exist in the database)

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

> register.html

```html

<!DOCTYPE html> 
<html> 
<head> 
<title>Save yourself</title> 
<style>
body {
  background-image: url('https://images.unsplash.com/photo-1539844647304-7702310556ad');
}
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

> delete.html

```html

<!DOCTYPE html> 
<html> 
<head> 
<title>Delete yourself</title> 
<style>
body {
  background-image: url('https://images.unsplash.com/photo-1539844647304-7702310556ad');
}
</style>
</head> 

<body><center>
    <form action="./delete" method="post"> 
        <h1>Delete yourself from the database</h1>
        <p>Enter your email:</p>  
        <input type="text" name="email"/> 
        <input type="submit"/> 
    </form> 
    
</center></body> 

</html> 

```

> update.html

```html

<!DOCTYPE html> 
<html> 
<head> 
<title>Update  yourself</title> 
<style>
body {
  background-image: url('https://images.unsplash.com/photo-1539844647304-7702310556ad');
}
</style>
</head> 

<body><center>
    <form action="./update" method="post"> 
        <h1>Update yourself in the database</h1>
        <p>Enter your current email:</p>  
        <input type="text" name="email"/> 
        <p>Enter your new email:</p>  
        <input type="text" name="newemail"/><br></br>
        <input type="submit"/> 
    </form> 
    
</center></body> 

</html> 

```

### java files

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
           String email=rq.getParameter("email");
           String n;
           int count=0;
           String query;
           Class.forName("org.mariadb.jdbc.Driver").newInstance();
           Connection con=DriverManager.getConnection("jdbc:mariadb://localhost:3306/java?user=root&password=root");
           Statement st=con.createStatement();
           ResultSet ps1= st.executeQuery("select * from register");
           while(ps1.next()){
                n = ps1.getString("email");
                if(email.equalsIgnoreCase(n))
                {
                    out.print("<h1>Your email is already in the database , Redirecting to the homepage in 5Sec....</h1>");
                    out.print("<meta http-equiv=\"refresh\" content=\"5;url=http://localhost:29734/login/\">");
                    count =1;
                    break;         
                }
           }
                if (count==0)
                {
                    ResultSet ps = st.executeQuery("insert into register values('"+email+"');");
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
           out.print("<html><head><style>table, th, td {border: 1px solid black;}body {background: rgb(2,0,36);background: linear-gradient(90deg, rgba(2,0,36,1) 0%, rgba(90,117,7,1) 0%, rgba(0,212,255,1) 100%);}</style></head><body><h1>Emails</h1><table style=\"width:300px;\"><tr><th colspan=\"1\">All Emails</th></tr>");
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

> update.java

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

public class update extends HttpServlet {

   public void doPost(HttpServletRequest rq, HttpServletResponse rs)
   {
       try
       {
           rs.setContentType("text/html");
           PrintWriter out=rs.getWriter();
           String email=rq.getParameter("email");
           String newemail=rq.getParameter("newemail");
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
                if(email.equalsIgnoreCase(n))
                {
                    
                    ResultSet ps = st.executeQuery("update register set email='" +newemail+"' where email='"+email+"';");
                    out.print("<meta http-equiv=\"refresh\" content=\"5;url=http://localhost:29734/crud/showusers\">");
                    out.print("<h1>Successfully Updated your email from Our DB Redirecting in 5Sec.....</h1>");
                    count =1;
                    break;         
                }
                if(newemail.equalsIgnoreCase(n))
                {
                    out.print("<meta http-equiv=\"refresh\" content=\"5;url=http://localhost:29734/crud/\">");
                    out.print("<h1>An Email with the same email is already regsitered , try something different !! Redirecting in 5Sec.....</h1>");   
                    count =2;
                    break; 
                }
            }
                if (count==0)
                {
                    out.print("<h1>Your email is not in the database , Redirecting to the homepage in 5Sec....</h1>");
                    out.print("<meta http-equiv=\"refresh\" content=\"5;url=http://localhost:29734/crud/\">");
                }
           
       }
       catch(Exception e)
       {

       }
       
    }
}
```

> delete.java

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

public class delete extends HttpServlet {

   public void doPost(HttpServletRequest rq, HttpServletResponse rs)
   {
       try
       {
           rs.setContentType("text/html");
           PrintWriter out=rs.getWriter();
           String email=rq.getParameter("email");
           String n;
           int count=0;
           String query;
           Class.forName("org.mariadb.jdbc.Driver").newInstance();
           Connection con=DriverManager.getConnection("jdbc:mariadb://localhost:3306/java?user=root&password=root");
           Statement st=con.createStatement();
          
           
           ResultSet ps1= st.executeQuery("select * from register");
           while(ps1.next()){
                n = ps1.getString("email");
                if(email.equalsIgnoreCase(n))
                {
                    ResultSet ps = st.executeQuery("delete from register where email='"+email+"';");
                    out.print("<meta http-equiv=\"refresh\" content=\"5;url=http://localhost:29734/crud/showusers\">");
                    out.print("<h1>Successfully deleted your email from Our DB Redirecting in 5Sec.....</h1>");
                    count =1;
                    break;         
                }
           }
                if (count==0)
                {
                    out.print("<h1>Your email is not in the database , Redirecting to the homepage in 5Sec....</h1>");
                    out.print("<meta http-equiv=\"refresh\" content=\"5;url=http://localhost:29734/crud/\">");

                }           
       }
       catch(Exception e)
       {

       }
       
    }
 }
```


### Outputs

> index.html

![](/images/image-9.png)

> register.html

![](/images/image-10.png)

> Response after submitting a new email that is not in database

![](/images/image-11.png)

> Redirect after 5sec to showusers

![](/images/image-12.png)