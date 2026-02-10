package hr.abysalto.hiring.mid.cart.app.usecase;

import hr.abysalto.hiring.mid.cart.domain.Cart;
import hr.abysalto.hiring.mid.cart.infrastructure.persistance.CartRepository;
import hr.abysalto.hiring.mid.product.app.usecase.port.in.ProductUseCase;
import hr.abysalto.hiring.mid.product.dto.ProductDto;
import hr.abysalto.hiring.mid.user.domain.User;
import hr.abysalto.hiring.mid.user.domain.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class CartService {

    private final CartRepository cartRepository;
    private final ProductUseCase productRepository;
    private final UserRepository userRepository;

    public CartService(CartRepository cartRepository,
                       ProductUseCase productRepository,
                       UserRepository userRepository) {
        this.cartRepository = cartRepository;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
    }

    public Cart getCart(Authentication auth) {
        User user = getUser(auth);
        return cartRepository.findByUserId(user.getId())
                .orElseGet(() -> cartRepository.save(new Cart(user.getId())));
    }

    @Transactional
    public Cart addItem(Authentication authentication, Long productId, int quantity) {
        User user = userRepository.findByUsername(authentication.getName())
                .orElseThrow(() -> new RuntimeException("User not found"));

        ProductDto product = productRepository.getProductById(productId);

        Cart cart = cartRepository.findByUserId(user.getId())
                .orElseGet(() -> cartRepository.save(new Cart(user.getId())));

        cart.addItem(product, quantity);

        return cartRepository.save(cart);
    }

    public Cart removeItem(Authentication auth, Long productId) {
        User user = getUser(auth);
        Cart cart = cartRepository.findByUserId(user.getId())
                .orElseThrow(() -> new RuntimeException("Cart not found"));

        cart.removeItem(productId);
        return cartRepository.save(cart);
    }

    private User getUser(Authentication auth) {
        return userRepository.findByUsername(auth.getName())
                .orElseThrow(() -> new RuntimeException("User not found"));
    }
}