package demo;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.util.annotation.ManagedAttribute;
import org.eclipse.jetty.util.annotation.ManagedObject;

@WebServlet(urlPatterns = "/hello", loadOnStartup = 1)
@ManagedObject("Hello Servlet")
@JmxInit.Key("hello")
public class HelloServlet extends HttpServlet
{
    private String message = "Hello World";
    
    @Override
    public void init() throws ServletException
    {
        JmxInit.add(getServletContext(), this);
    }
    
    @ManagedAttribute(name = "Message")
    public String getMessage()
    {
        return message;
    }
    
    public void setMessage(String message)
    {
        this.message = message;
    }
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        resp.setContentType("text/plain");
        resp.getWriter().println(message);
    }
}
