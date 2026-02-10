package hr.abysalto.hiring.mid.cart.infrastructure.persistance;

import hr.abysalto.hiring.mid.cart.domain.Cart;
import hr.abysalto.hiring.mid.user.domain.User;

import java.util.Optional;

public interface CartRepository {
    Optional<Cart> findByUserId(Long userId);
    Cart save(Cart cart);
}