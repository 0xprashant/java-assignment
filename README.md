# Assignment - 6 JavaFilters

## Question - WAP to implement the authentication of multiple web pages using Filter interface.

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
    <form action="./login" method="post"> 
        <h1>Do Signup and then Login</h1>
        <h2>Signup here</h2>
        <p>Enter Username:</p>  
        <input type="text" name="username"/> 
        <p>Enter Password:</p>  
        <input type="text" name="password"/><br></br>
        <input type="submit"/>
        <br></br>
    </form>
    <a href="profile">profile Page</a>
    <a href="secret">Secret Page</a>
    
</center></body> 

</html>
```

> login.java (servlet)

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

public class login extends HttpServlet {

   public void doPost(HttpServletRequest rq, HttpServletResponse rs)
   {
       try
       {
           rs.setContentType("text/html");
           PrintWriter out=rs.getWriter();
           String username=rq.getParameter("username");
           String password=rq.getParameter("password");
           out.print("I am login page<br></br>");
           out.print(username);
           
           
       }
       catch(Exception e)
       {

       }
       
    }
}
```

> login_check (filter)

```java
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.*;
import javax.servlet.http.HttpSession;
import java.io.IOException;  
import java.io.PrintWriter;  
import javax.servlet.ServletException;  
import javax.servlet.http.HttpServlet;  
import javax.servlet.http.HttpServletRequest;  
import javax.servlet.http.HttpServletResponse;  
import javax.servlet.http.HttpSession;

public class login_check implements Filter{

	public void init(FilterConfig arg0) throws ServletException {}
	
	public void doFilter(ServletRequest rq, ServletResponse rs,
			FilterChain chain) throws IOException, ServletException {
	rs.setContentType("text/html");  
        PrintWriter out=rs.getWriter();  
        try{
            String username=rq.getParameter("username");
           String password=rq.getParameter("password");
            
        HttpServletRequest request=(HttpServletRequest)rq;
        HttpSession session=request.getSession(false); 
        
        if(password.isEmpty() || username.isEmpty())
           {
                
                out.print("Don't specify any null value");
   
           }
           else 
           {
                session=request.getSession(true);
                
                
           }
        
        if (session==null)
                {
        out.print("<br>You have to login first soryy</br>");
        
        }
        else
        {
            
            chain.doFilter(rq, rs);
        }}
        catch(Exception e)
        {
            //out.print(e.getMessage());
        }
        }
	public void destroy() {}
}
```

> profile.java (servlet)

```java
import java.io.IOException;  
import java.io.PrintWriter;  
import javax.servlet.ServletException;  
import javax.servlet.http.HttpServlet;  
import javax.servlet.http.HttpServletRequest;  
import javax.servlet.http.HttpServletResponse;  
import javax.servlet.http.HttpSession;  
public class profile extends HttpServlet {  
    protected void doGet(HttpServletRequest request, HttpServletResponse response)  
                      throws ServletException, IOException {  
        response.setContentType("text/html");  
        PrintWriter out=response.getWriter();  
          
          
        out.print("I am profile page");
        
          
    }  
}

```

> profileFilter.java (Filter)


```java
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.*;
import javax.servlet.http.HttpSession;
import java.io.IOException;  
import java.io.PrintWriter;  
import javax.servlet.ServletException;  
import javax.servlet.http.HttpServlet;  
import javax.servlet.http.HttpServletRequest;  
import javax.servlet.http.HttpServletResponse;  
import javax.servlet.http.HttpSession;

public class profileFilter implements Filter{

	public void init(FilterConfig arg0) throws ServletException {}
	
	public void doFilter(ServletRequest rq, ServletResponse rs,
			FilterChain chain) throws IOException, ServletException {
	rs.setContentType("text/html");  
        PrintWriter out=rs.getWriter();  
        try{
         
        HttpServletRequest request=(HttpServletRequest)rq;
        HttpSession session=request.getSession(false); 
        if (session==null)
                {
        out.print("<br>You have to login first</br>");
        
        }
        else
        {
            
            chain.doFilter(rq, rs);
        }
        }
        catch(Exception e)
        {
            
        }
        }
	public void destroy() {}
}
```

> secret.java (servlet)

```java
import java.io.IOException;  
import java.io.PrintWriter;  
import javax.servlet.ServletException;  
import javax.servlet.http.HttpServlet;  
import javax.servlet.http.HttpServletRequest;  
import javax.servlet.http.HttpServletResponse;  
import javax.servlet.http.HttpSession;  
public class secret extends HttpServlet {  
    protected void doGet(HttpServletRequest request, HttpServletResponse response)  
                      throws ServletException, IOException {  
        response.setContentType("text/html");  
        PrintWriter out=response.getWriter();  
          
          
        out.print("I am secret page , the secret is that Mirzapur 2 is coming");     
          
    }  
}
```

> web.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<web-app version="3.1" xmlns="http://xmlns.jcp.org/xml/ns/javaee" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemaLocation="http://xmlns.jcp.org/xml/ns/javaee http://xmlns.jcp.org/xml/ns/javaee/web-app_3_1.xsd">
    <filter>
        <filter-name>login_check</filter-name>
        <filter-class>login_check</filter-class>
    </filter>
    <filter>
        <filter-name>profileFilter</filter-name>
        <filter-class>profileFilter</filter-class>
    </filter>
    <filter-mapping>
        <filter-name>login_check</filter-name>
        <url-pattern>/login</url-pattern>
    </filter-mapping>
    <filter-mapping>
        <filter-name>profileFilter</filter-name>
        <url-pattern>/profile</url-pattern>
        <url-pattern>/secret</url-pattern>
    </filter-mapping>
    <servlet>
        <servlet-name>login</servlet-name>
        <servlet-class>login</servlet-class>
    </servlet>
    <servlet>
        <servlet-name>profile</servlet-name>
        <servlet-class>profile</servlet-class>
    </servlet>
    <servlet>
        <servlet-name>secret</servlet-name>
        <servlet-class>secret</servlet-class>
    </servlet>
    <servlet-mapping>
        <servlet-name>login</servlet-name>
        <url-pattern>/login</url-pattern>
    </servlet-mapping>
    <servlet-mapping>
        <servlet-name>profile</servlet-name>
        <url-pattern>/profile</url-pattern>
    </servlet-mapping>
    <servlet-mapping>
        <servlet-name>secret</servlet-name>
        <url-pattern>/secret</url-pattern>
    </servlet-mapping>
    <session-config>
        <session-timeout>
            30
        </session-timeout>
    </session-config>
</web-app>
```

## output 

![](/images/image-31.png) 

> without logging in to access profile and secret

![](/images/32.png)

> login without any username and password

![](/images/33.png) 

> After logging in 

![](/images/34.png) 

![](/images/35.png) 

![](/images/36.png) 

## Question - 2 

> WAP to implement the addition of two numbers but use Filter to perform validation NumberFormatException.

> sum.java (servlet)

```java

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class sum extends HttpServlet {
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		int n1=Integer.parseInt(request.getParameter("email"));
		int n2=Integer.parseInt(request.getParameter("password"));

		int sum=n1+n2;
		PrintWriter pw=response.getWriter();
		pw.print("Sum is  " + sum);
	}

}
```

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
    <form action="./sum" method="post"> 
        <h1>Do Signup</h1>
        <p>Enter 1st</p>  
        <input type="text" name="email"/> 
        <p>Enter 2nd:</p>  
        <input type="text" name="password"/><br></br>
        
        <input type="submit"/>
        <br></br>
    </form>
    
</center></body> 

</html> 
```

> filter.java (filter)

```java
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.*;

public class filter implements Filter{

	public void init(FilterConfig arg0) throws ServletException {}
	
	public void doFilter(ServletRequest req, ServletResponse resp,
			FilterChain chain) throws IOException, ServletException {
	PrintWriter out=resp.getWriter();	
            try {
		
		int num1=Integer.parseInt(req.getParameter("email"));
		int num2=Integer.parseInt(req.getParameter("password"));
                chain.doFilter(req, resp);
                
                }
                catch(NumberFormatException e)
		{

			out.print("How can you add chars ?");
                }

	}
	public void destroy() {}
}
```

## output

![](/images/37.png) 

![](/images/38.png) 
