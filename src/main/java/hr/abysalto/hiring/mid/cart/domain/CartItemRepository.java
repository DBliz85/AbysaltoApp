package hr.abysalto.hiring.mid.cart.domain;

public interface CartItemRepository {
    CartItem save(CartItem cartItem, Long id);
}