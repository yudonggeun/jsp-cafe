package woowa.cafe.dto;

public record CreateUserRequest(
        String userId,
        String password,
        String name,
        String email
) {
}
