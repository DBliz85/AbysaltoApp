package hr.abysalto.hiring.mid.cart.app.usecase;

import hr.abysalto.hiring.mid.cart.app.exception.CartNotFoundException;
import hr.abysalto.hiring.mid.cart.app.exception.ProductNotInCartException;
import hr.abysalto.hiring.mid.cart.app.port.out.UserGateway;
import hr.abysalto.hiring.mid.cart.domain.Cart;
import hr.abysalto.hiring.mid.cart.domain.CartRepository;
import org.springframework.stereotype.Service;

@Service
public class RemoveItemFromCartService implements RemoveItemFromCartUseCase {
    private final CartRepository cartRepository;
    private final UserGateway userGateway;

    public RemoveItemFromCartService(CartRepository cartRepository, UserGateway userGateway) {
        this.cartRepository = cartRepository;
        this.userGateway = userGateway;
    }

    @Override
    public Cart removeItem(String username, Long productId) {
        Long userId = userGateway.getUserIdByUsername(username);

        Cart cart = cartRepository.findByUserId(userId)
                .orElseThrow(() -> new CartNotFoundException("Cart not found"));

        boolean exists = cart.getItems().stream()
                .anyMatch(item -> item.getProductId().equals(productId));

        if (!exists) {
            throw new ProductNotInCartException(productId);
        }

        cart.removeItem(productId);

        return cartRepository.save(cart);
    }
}
