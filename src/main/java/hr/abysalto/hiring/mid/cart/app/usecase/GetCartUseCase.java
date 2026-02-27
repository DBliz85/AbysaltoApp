package hr.abysalto.hiring.mid.cart.app.usecase;

import hr.abysalto.hiring.mid.cart.domain.Cart;

public interface GetCartUseCase {
    Cart getCart(String username);
}
