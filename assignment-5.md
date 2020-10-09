# Assignment 5 - Cookie Manipulation

Submitted by - Prashant Saini

> index.html

```html

<!DOCTYPE html> 
<html> 
<head> 
<title>Cookie Example</title> 
<style>
            body{
           background: rgb(2,0,36);
            background: linear-gradient(90deg, rgba(2,0,36,1) 0%, rgba(9,121,116,1) 35%, rgba(0,212,255,1) 100%); }
</style>
</head> 

<body><center>
    <form action="./signup" method="post"> 
        <h1>Do Signup</h1>
        <p>Enter FirstName:</p>  
        <input type="text" name="firstname"/> 
        <p>Enter LastName:</p>  
        <input type="text" name="lastname"/><br></br>
        <p>Enter Email:</p>  
        <input type="text" name="email"/><br></br>
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
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpSession;

public class signup extends HttpServlet {

   public void doPost(HttpServletRequest rq, HttpServletResponse rs) throws ServletException, IOException
   {
       try
       {
           rs.setContentType("text/html");
           PrintWriter out=rs.getWriter();
           String firstname=rq.getParameter("firstname");
           String lastname=rq.getParameter("lastname");
           String email=rq.getParameter("email");
           Cookie ck[]=rq.getCookies();
           
           if(ck==null)
           {    
               if(firstname.isEmpty() || lastname.isEmpty()|| email.isEmpty())
               {
                    out.print("<script>alert('Dont specify any null value , because you are new here')</script>");
                    out.print("<meta http-equiv=\"refresh\" content=\"0.5;url=http://localhost:29734/CookieExample/index.html\">");
               }
               else
               {
                    Cookie c1 = new Cookie("firstname",firstname);
                    Cookie c2 = new Cookie("lastname",lastname);
                    Cookie c3 = new Cookie("email",email);
                    rs.addCookie(c1);
                    rs.addCookie(c2);
                    rs.addCookie(c3);
                    out.print("<h1>Added</h1>");
                    rs.setIntHeader("Refresh", 1);
               }
           }
           else
           {
               if(firstname.isEmpty() == false)
               {
                   Cookie c1 = new Cookie("firstname",firstname);
                   rs.addCookie(c1);
               }
               if(lastname.isEmpty() == false)
               {
                   Cookie c2 = new Cookie("lastname",lastname);
                   rs.addCookie(c2);
               }
               if(email.isEmpty() == false)
               {
                   Cookie c3 = new Cookie("email",email);
                   rs.addCookie(c3);
               }
               out.print("<html><head><style>table, th, td {border: 1px solid black;}</style></head><body><table style=\"width:300px;\"><tr><th colspan=\"1\">All Emails</th><th>value</th></tr>");
               for(int i=0;i<ck.length;i++)
               {           
                   out.print("<tr><td>"+ck[i].getName()+"</td>");
                   out.print("<td>"+ck[i].getValue()+"</td></tr>");
               }
               out.print("</table></body></html>");
           }
       }
       catch(Exception e)
       {

       }  
    }
   
       
}
```

## Outputs

> signup page

![](/images/image-25.png)


> First time user with input of null values

![](/images/image-26.png)

> Showing details after signup

![](/images/image-27.png)

> if the user is an old user and specify any new input and update the cookies

![](/images/image-28.png)

> if the user specify any input value null or empty 

![](/images/image-29.png)

![](/images/image-30.png)




