package demo;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.util.Set;
import java.util.stream.Collectors;

import javax.servlet.ServletContainerInitializer;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.HandlesTypes;

import org.eclipse.jetty.util.annotation.ManagedObject;

@HandlesTypes(JmxInit.Key.class)
public class JmxInit implements ServletContainerInitializer
{
    @Documented
    @Retention(RUNTIME)
    public @interface Key
    {
        String value() default "";
    }
    
    public static void add(ServletContext servletContext, Object obj)
    {
        Key key = obj.getClass().getAnnotation(Key.class);
        if (key == null)
        {
            throw new RuntimeException("Unable to find @" + Key.class.getName() + " on " + obj.getClass().getName());
        }
        servletContext.setAttribute(key.value(), obj);
    }
    
    @Override
    public void onStartup(Set<Class<?>> c, ServletContext ctx) throws ServletException
    {
        String namedKeys = c.stream()
                .map(e ->
                {
                    if (e.getAnnotation(ManagedObject.class) == null)
                        throw new RuntimeException("Missing required @" + ManagedObject.class.getName() + " annotation on class " + e.getName());
                    return e.getAnnotation(Key.class).value();
                })
                .collect(Collectors.joining(","));
        
        ctx.setInitParameter("org.eclipse.jetty.server.context.ManagedAttributes", namedKeys);
    }
}
