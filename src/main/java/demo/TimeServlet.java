package demo;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.util.annotation.ManagedAttribute;
import org.eclipse.jetty.util.annotation.ManagedObject;

@ManagedObject("Time Servlet")
public class TimeServlet extends HttpServlet
{
    private String format = "yyyy.MM.dd G 'at' HH:mm:ss z";
    
    @Override
    public void init() throws ServletException
    {
        getServletContext().setAttribute("timeKey", this);
    }
    
    @ManagedAttribute(value = "Format")
    public String getFormat()
    {
        return format;
    }
    
    public void setFormat(String format)
    {
        this.format = format;
    }
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        resp.setContentType("text/plain");
        Date now = new Date();
        resp.getWriter().println(new SimpleDateFormat(format).format(now));
    }
}
