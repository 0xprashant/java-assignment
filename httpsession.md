# Assignment HttpSession 

Submitted by - Prashant Saini

> index.html

```html

<!DOCTYPE html> 
<html> 
<head> 
<title>HttpSession</title> 
<style>
</style>
</head> 

<body><center>
    <form action="./signup" method="post"> 
        <h1>Do Signup and then Login</h1>
        <h2>Signup here</h2>
        <p>Enter Username:</p>  
        <input type="text" name="username"/> 
        <p>Enter Password:</p>  
        <input type="text" name="password"/><br></br>
        <input type="submit"/>
        <br></br>
    </form>
    
</center></body> 

</html> 

```

> signup.java

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
import javax.servlet.http.HttpSession;

public class signup extends HttpServlet {

   public void doPost(HttpServletRequest rq, HttpServletResponse rs)
   {
       try
       {
           rs.setContentType("text/html");
           PrintWriter out=rs.getWriter();
           String username=rq.getParameter("username");
           String password=rq.getParameter("password");
           HttpSession session=rq.getSession(true);
           if(password!=null || username!=null)
           {
                session.setAttribute("username",username);
                
                rs.sendRedirect("./login");
           }
           else
           {
                out.print("Don't specify any null value");
           }
       }
       catch(Exception e)
       {

       }
       
    }
}
```

> login.java

```java
import java.io.IOException;  
import java.io.PrintWriter;  
import javax.servlet.ServletException;  
import javax.servlet.http.HttpServlet;  
import javax.servlet.http.HttpServletRequest;  
import javax.servlet.http.HttpServletResponse;  
import javax.servlet.http.HttpSession;  
public class login extends HttpServlet {  
    protected void doGet(HttpServletRequest request, HttpServletResponse response)  
                      throws ServletException, IOException {  
        response.setContentType("text/html");  
        PrintWriter out=response.getWriter();  
          
          
        HttpSession session=request.getSession(false);  
        if(session!=null){  
        String name=(String)session.getAttribute("username");  
          
        out.print("Hello, "+name+" Welcome to Profile");  
        }    
          
    }  
}
```

![](/images/image-22.png)

![](/images/image-23.png)
