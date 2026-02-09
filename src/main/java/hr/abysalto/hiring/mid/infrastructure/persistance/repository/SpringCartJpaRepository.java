package hr.abysalto.hiring.mid.infrastructure.persistance.repository;

import hr.abysalto.hiring.mid.cart.JpaCartEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SpringCartJpaRepository extends JpaRepository<JpaCartEntity, Long> {
    Optional<JpaCartEntity> findByUserId(Long userId);
}
