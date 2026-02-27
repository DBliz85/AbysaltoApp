package hr.abysalto.hiring.mid.cart.app.usecase;

import hr.abysalto.hiring.mid.cart.domain.Cart;

public interface AddItemToCartUseCase {
    Cart addItem(String username, Long productId, int quantity);
}
