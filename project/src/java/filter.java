
import javax.servlet.*;
import javax.servlet.http.HttpSession;
import java.io.IOException;  
import java.io.PrintWriter;  
import javax.servlet.ServletException;  
import javax.servlet.http.HttpServlet;  
import javax.servlet.http.HttpServletRequest;  
import javax.servlet.http.HttpServletResponse;  
import javax.servlet.http.HttpSession;

public class filter implements Filter{

	public void init(FilterConfig arg0) throws ServletException {}
	
	public void doFilter(ServletRequest rq, ServletResponse rs,
			FilterChain chain) throws IOException, ServletException {
	rs.setContentType("text/html");  
        PrintWriter out=rs.getWriter();  
        try{
        HttpServletRequest request=(HttpServletRequest)rq;
        HttpServletResponse response=(HttpServletResponse)rs;
        HttpSession session=request.getSession(false);
        if (session==null)
        {
           
              response.sendRedirect("./index.html");
        
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