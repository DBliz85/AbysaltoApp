package hr.abysalto.hiring.mid.user.persistance.repository;

import hr.abysalto.hiring.mid.user.domain.User;

import java.util.Optional;

public interface UserRepository {
    Optional<User> findByUsername(String username);
    User save(User user);
}
