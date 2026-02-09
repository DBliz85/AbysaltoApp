package hr.abysalto.hiring.mid.infrastructure.persistance.repository;

import hr.abysalto.hiring.mid.user.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SpringUserJpaRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByUsername(String username);
}
