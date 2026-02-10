package hr.abysalto.hiring.mid.cart.infrastructure.persistance;

import hr.abysalto.hiring.mid.cart.infrastructure.entity.CartItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SpringCartItemJpaRepository extends JpaRepository<CartItemEntity, Long> {
    List<CartItemEntity> findByCartId(Long userId);
}
