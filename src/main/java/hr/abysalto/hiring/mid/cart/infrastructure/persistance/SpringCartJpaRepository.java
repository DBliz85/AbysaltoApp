package hr.abysalto.hiring.mid.cart.infrastructure.persistance;

import hr.abysalto.hiring.mid.cart.infrastructure.persistance.entity.JpaCartEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SpringCartJpaRepository extends JpaRepository<JpaCartEntity, Long> {
    Optional<JpaCartEntity> findByUserId(Long userId);
}
