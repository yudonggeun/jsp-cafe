package woowa.frame.web;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import woowa.frame.core.BeanContainer;
import woowa.frame.core.annotation.Component;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Optional;

@Component
public class DispatcherServlet extends HttpServlet {

    private BeanContainer beanContainer = BeanContainer.getInstance();
    private RouteTable table;

    @Override
    public void init(ServletConfig servletConfig) {
        table = beanContainer.getBean(RouteTable.class);
    }

    @Override
    public void service(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Optional<RouteTableRow> routeTableRow = table.findTable(request);

        if (routeTableRow.isPresent()) {
            Object result = routeTableRow.get().handle(request, response);

            response.setContentType("text/html");

            PrintWriter out = response.getWriter();
            out.println("<html><body>");
            out.println("<h1>200 OK</h1>");
            out.println("<h1>" + result + "</h1>");
            out.println("</body></html>");
        } else {
            response.setContentType("text/html");

            PrintWriter out = response.getWriter();
            out.println("<html><body>");
            out.println("<h1>404 NOT FOUND</h1>");
            out.println("</body></html>");
        }
    }

    @Override
    public void destroy() {
    }
}
