package woowa.cafe.router;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import woowa.cafe.dto.CreateUserRequest;
import woowa.cafe.service.UserService;
import woowa.frame.web.annotation.Router;
import woowa.frame.web.annotation.HttpMapping;

@Router
public class UserRouter {

    private final UserService userService;

    public UserRouter(UserService userService) {
        this.userService = userService;
    }

    @HttpMapping(method = "POST", urlTemplate = "/user/create")
    public String createUser(HttpServletRequest request, HttpServletResponse response) {
        var createUserRequest = new CreateUserRequest(
                request.getParameter("userId"),
                request.getParameter("password"),
                request.getParameter("name"),
                request.getParameter("email")
        );
        userService.createUser(createUserRequest);
        return "redirect:/static/user/list.html";
    }
}
