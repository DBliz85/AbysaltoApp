package hr.abysalto.hiring.mid.cart.app.usecase;

import hr.abysalto.hiring.mid.cart.domain.CartRepository;
import hr.abysalto.hiring.mid.cart.domain.Cart;
import hr.abysalto.hiring.mid.product.domain.Product;
import hr.abysalto.hiring.mid.user.domain.User;
import hr.abysalto.hiring.mid.product.infrastructure.persistance.ProductRepository;
import hr.abysalto.hiring.mid.user.domain.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class CartService {

    private final CartRepository cartRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    public CartService(CartRepository cartRepository,
                       ProductRepository productRepository,
                       UserRepository userRepository) {
        this.cartRepository = cartRepository;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
    }

    public Cart getCart(Authentication auth) {
        User user = getUser(auth);
        return cartRepository.findByUser(user)
                .orElseGet(() -> cartRepository.save(new Cart(user.getId())));
    }

    public Cart addItem(Authentication auth, Long productId, int quantity) {
        User user = getUser(auth);
        Cart cart = cartRepository.findByUser(user)
                .orElseGet(() -> cartRepository.save(new Cart(user.getId())));
        Product product = getProduct(productId);
        cart.addItem(product, quantity);
        return cartRepository.save(cart);
    }

    public Cart removeItem(Authentication auth, Long productId) {
        User user = getUser(auth);
        Cart cart = cartRepository.findByUser(user)
                .orElseThrow(() -> new RuntimeException("Cart not found"));

        cart.removeItem(productId);
        return cartRepository.save(cart);
    }

    private User getUser(Authentication auth) {
        return userRepository.findByUsername(auth.getName())
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    private Product getProduct(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));
    }
}