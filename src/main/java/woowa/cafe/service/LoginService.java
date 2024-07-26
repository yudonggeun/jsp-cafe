package woowa.cafe.service;

import woowa.cafe.domain.User;
import woowa.cafe.repository.UserRepository;
import woowa.frame.core.annotation.Component;

@Component
public class LoginService {

    private final UserRepository userRepository;

    public LoginService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public boolean login(String userId, String password) {
        User user = userRepository.findByUserIdAndPassword(userId, password);
        return user != null;
    }
}
