package hr.abysalto.hiring.mid.cart.app.usecase;

import hr.abysalto.hiring.mid.cart.app.usecase.port.out.ProductGateway;
import hr.abysalto.hiring.mid.cart.app.usecase.port.out.UserGateway;
import hr.abysalto.hiring.mid.cart.domain.Cart;
import hr.abysalto.hiring.mid.cart.domain.CartRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class CartService {

    private final CartRepository cartRepository;
    private final UserGateway userGateway;
    private final ProductGateway productGateway;

    public CartService(CartRepository cartRepository,
                       UserGateway userGateway,
                       ProductGateway productGateway) {
        this.cartRepository = cartRepository;
        this.userGateway = userGateway;
        this.productGateway = productGateway;
    }

    public Cart getCart(String username) {
        Long userId = userGateway.getUserIdByUsername(username);

        return cartRepository.findByUserId(userId)
                .orElseGet(() -> cartRepository.save(new Cart(userId)));
    }

    public Cart addItem(String username, Long productId, int quantity) {
        Long userId = userGateway.getUserIdByUsername(username);

        if (!productGateway.exists(productId)) {
            throw new RuntimeException("Product not found");
        }

        Cart cart = cartRepository.findByUserId(userId)
                .orElseGet(() -> cartRepository.save(new Cart(userId)));

        cart.addItem(productId, quantity);

        return cartRepository.save(cart);
    }

    public Cart removeItem(String username, Long productId) {
        Long userId = userGateway.getUserIdByUsername(username);

        Cart cart = cartRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Cart not found"));

        boolean exists = cart.getItems().stream()
                .anyMatch(item -> item.getProductId().equals(productId));

        if (!exists) {
            throw new RuntimeException("Product not found in cart");
        }

        cart.removeItem(productId);

        return cartRepository.save(cart);
    }
}