package hr.abysalto.hiring.mid.cart.app.usecase;

import hr.abysalto.hiring.mid.cart.app.port.out.ProductGateway;
import hr.abysalto.hiring.mid.cart.app.port.out.UserGateway;
import hr.abysalto.hiring.mid.cart.domain.Cart;
import hr.abysalto.hiring.mid.cart.domain.CartRepository;
import hr.abysalto.hiring.mid.product.app.usecase.exception.ProductNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AddItemToCartService implements AddItemToCartUseCase {

    private final CartRepository cartRepository;
    private final UserGateway userGateway;
    private final ProductGateway productGateway;

    public AddItemToCartService(CartRepository cartRepository, UserGateway userGateway, ProductGateway productGateway) {
        this.cartRepository = cartRepository;
        this.userGateway = userGateway;
        this.productGateway = productGateway;
    }

    @Override
    public Cart addItem(String username, Long productId, int quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be greater than zero");
        }

        Long userId = userGateway.getUserIdByUsername(username);

        if (!productGateway.exists(productId)) {
            throw new ProductNotFoundException(productId);
        }

        Cart cart = cartRepository.findByUserId(userId)
                .orElseGet(() -> new Cart(userId));

        cart.addItem(productId, quantity);

        return cartRepository.save(cart);
    }
}
