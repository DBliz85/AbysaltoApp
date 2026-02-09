package hr.abysalto.hiring.mid.cart;

import hr.abysalto.hiring.mid.user.User;

import java.util.Optional;

public interface CartRepository {

    Optional<Cart> findByUser(User user);

    Cart save(Cart cart);
}