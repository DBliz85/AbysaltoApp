package hr.abysalto.hiring.mid.domain.repository;

import hr.abysalto.hiring.mid.domain.model.Cart;
import hr.abysalto.hiring.mid.domain.model.User;

import java.util.Optional;

public interface CartRepository {
    Optional<Cart> findByUser(User user);
    Cart save(Cart cart);
}
