package woowa.frame.web;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import woowa.frame.core.BeanContainer;
import woowa.frame.core.annotation.Component;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Optional;

@Component
public class DispatcherServlet extends HttpServlet {

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private BeanContainer beanContainer = BeanContainer.getInstance();
    private RouteTable table;

    @Override
    public void init() {
        table = beanContainer.getBean(RouteTable.class);
    }

    @Override
    public void service(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        Optional<RouteTableRow> routeTableRow = table.findTable(request);

        if (routeTableRow.isPresent()) {
            Object result = routeTableRow.get().handle(request, response);

            if (result instanceof String) {
                String stringResult = (String) result;
                if(stringResult.startsWith("redirect:")) {
                    response.sendRedirect(stringResult.substring(9));
                    return;
                }
            }

            response.setContentType("text/html");

            PrintWriter out = response.getWriter();
            out.println("<html><body>");
            out.println("<h1>200 OK</h1>");
            out.println("<h1>" + result + "</h1>");
            out.println("</body></html>");
        } else {
            try {
                RequestDispatcher dispatcher = request.getRequestDispatcher("error/404.html");
                dispatcher.forward(request, response);
            } catch (ServletException e) {
                e.printStackTrace();
                throw e;
            }
        }
    }

    @Override
    public void destroy() {
    }
}
