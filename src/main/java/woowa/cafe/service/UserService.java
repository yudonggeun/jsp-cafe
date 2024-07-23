package woowa.cafe.service;

import woowa.cafe.domain.User;
import woowa.cafe.dto.CreateUserRequest;
import woowa.cafe.repository.UserRepository;
import woowa.frame.core.annotation.Component;

@Component
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void createUser(CreateUserRequest request) {
        User user = new User(request.userId(), request.password(), request.name(), request.email());
        userRepository.save(user);
    }
}
