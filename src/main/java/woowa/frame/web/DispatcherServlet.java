package woowa.frame.web;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServlet;
import woowa.frame.core.BeanContainer;
import woowa.frame.core.annotation.Component;

import java.io.IOException;
import java.io.PrintWriter;

@Component
public class DispatcherServlet extends HttpServlet {

    private BeanContainer beanContainer = BeanContainer.getInstance();

    @Override
    public void init(ServletConfig servletConfig) throws ServletException {
    }

    @Override
    public void service(ServletRequest request, ServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");

        // Hello
        PrintWriter out = response.getWriter();
        out.println("<html><body>");
        out.println("<h1>dispatcher</h1>");
        out.println("</body></html>");
    }

    @Override
    public void destroy() {
    }
}
