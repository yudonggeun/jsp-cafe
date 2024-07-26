package woowa.cafe.router;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import woowa.cafe.service.LoginService;
import woowa.frame.web.annotation.HttpMapping;
import woowa.frame.web.annotation.Router;

@Router
public class LoginRouter {

    private final LoginService loginService;

    public LoginRouter(LoginService loginService) {
        this.loginService = loginService;
    }

    @HttpMapping(method = "GET", urlTemplate = "/login")
    public String getLoginPage(HttpServletRequest request, HttpServletResponse response) {
        return "/template/login/login.jsp";
    }

    @HttpMapping(method = "POST", urlTemplate = "/login")
    public String login(HttpServletRequest request, HttpServletResponse response) {
        String userId = request.getParameter("userId");
        String password = request.getParameter("password");

        if(loginService.login(userId, password)){
            HttpSession session = request.getSession(true);
            session.setAttribute("userId", userId);
            return "redirect:/";
        } else {
            return "redirect:/login";
        }
    }
}
