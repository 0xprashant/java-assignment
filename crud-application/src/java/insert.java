/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/**
 *
 * @author prashant
 */
public class insert extends HttpServlet {
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html;charset=UTF-8");
        try {
            PrintWriter out = response.getWriter();
            int id = Integer.parseInt(request.getParameter("id"));
            String name = request.getParameter("name");
            String course = request.getParameter("course");
            projectpojo p = new projectpojo();
            p.setId(id);
            p.setName(name);
            p.setCourse(course);
            Configuration c = new Configuration().configure("hibernate.cfg.xml");
            //c = new Configuration.configure("hibernate.cfg.xml");
            SessionFactory s1 = c.buildSessionFactory();
            Session s = s1.openSession();
            s.save(p);
            s.beginTransaction().commit();
            out.print("Done");
        }
        catch (Exception e)
        {
            response.getWriter().print(e.getMessage());
        }
    }

    
}
