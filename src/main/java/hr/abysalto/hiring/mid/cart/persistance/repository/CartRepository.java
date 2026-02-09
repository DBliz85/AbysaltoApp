package hr.abysalto.hiring.mid.cart.persistance.repository;

import hr.abysalto.hiring.mid.cart.domain.Cart;
import hr.abysalto.hiring.mid.user.domain.User;

import java.util.Optional;

public interface CartRepository {
    Optional<Cart> findByUser(User user);
    Cart save(Cart cart);
}