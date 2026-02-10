package hr.abysalto.hiring.mid.cart.domain;

import hr.abysalto.hiring.mid.cart.infrastructure.entity.CartEntity;

import java.util.List;
import java.util.Optional;

public interface CartItemRepository {
    Optional<CartItem> findById(Long id);
    List<CartItem> findByCartId(Long cartId);
    CartItem save(CartItem cartItem, CartEntity cartEntity);
    void delete(CartItem cartItem);
}