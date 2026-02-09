package hr.abysalto.hiring.mid.user.domain;

import java.util.Optional;

public interface UserRepository {
    Optional<User> findByUsername(String username);
    User save(User user);
}
