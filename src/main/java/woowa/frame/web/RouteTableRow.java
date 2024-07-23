package woowa.frame.web;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class RouteTableRow {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private final String method;
    private final String urlTemplate;
    private final Object handler;
    private final Method invokeMethod;

    public RouteTableRow(String method, String urlTemplate, Object handler, Method invokeMethod) {
        this.method = method;
        this.urlTemplate = urlTemplate;
        this.handler = handler;
        this.invokeMethod = invokeMethod;
    }

    public boolean isMatch(HttpServletRequest request) {
        return this.method.equals(request.getMethod()) &&
               this.urlTemplate.equals(request.getPathInfo());
    }

    public Object handle(HttpServletRequest request, HttpServletResponse response) {
        try {
            Object result = null;
            result = invokeMethod.invoke(handler, request, response);
            return result;
        } catch (IllegalAccessException e) {
            logger.error("메서드 실행 파라미터가 잘못되었습니다.");
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            logger.error("실행할 수 없는 객체입니다.");
            throw new RuntimeException(e);
        }
    }

}
