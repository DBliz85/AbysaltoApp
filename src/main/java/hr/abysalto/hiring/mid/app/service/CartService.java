package hr.abysalto.hiring.mid.app.service;

import hr.abysalto.hiring.mid.domain.model.Cart;
import hr.abysalto.hiring.mid.domain.model.Product;
import hr.abysalto.hiring.mid.domain.model.User;
import hr.abysalto.hiring.mid.domain.repository.CartRepository;
import hr.abysalto.hiring.mid.domain.repository.ProductRepository;
import org.springframework.stereotype.Service;

@Service
public class CartService {

    private final CartRepository cartRepository;
    private final ProductRepository productRepository;

    public CartService(CartRepository cartRepository, ProductRepository productRepository) {
        this.cartRepository = cartRepository;
        this.productRepository = productRepository;
    }

    public Cart addItem(User user, Long productId, int quantity) {
        Cart cart = cartRepository.findByUser(user).orElse(new Cart(user));
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        cart.addItem(product, quantity);
        return cartRepository.save(cart);
    }

    public Cart removeItem(User user, Long productId) {
        Cart cart = cartRepository.findByUser(user)
                .orElseThrow(() -> new RuntimeException("Cart not found"));
        cart.removeItem(productId);
        return cartRepository.save(cart);
    }

    public Cart getCart(User user) {
        return cartRepository.findByUser(user)
                .orElse(new Cart(user));
    }
}