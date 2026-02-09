package hr.abysalto.hiring.mid.product.app.usecase;

import hr.abysalto.hiring.mid.infrastructure.client.DummyJsonClient;
import hr.abysalto.hiring.mid.product.domain.Product;
import hr.abysalto.hiring.mid.product.infrastructure.persistance.ProductRepository;
import hr.abysalto.hiring.mid.user.domain.User;
import hr.abysalto.hiring.mid.user.domain.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final DummyJsonClient dummyJsonClient;
    private final UserRepository userRepository;

    public ProductService(ProductRepository productRepository, DummyJsonClient dummyJsonClient, UserRepository userRepository) {
        this.productRepository = productRepository;
        this.dummyJsonClient = dummyJsonClient;
        this.userRepository = userRepository;
    }

    @Transactional
    public Page<Product> getProducts(Pageable pageable) {
        Page<Product> products = productRepository.findAll(pageable);
        if (products.isEmpty()) {
            List<Product> fetchedProducts = dummyJsonClient.fetchProducts();
            productRepository.saveAll(fetchedProducts);
            products = productRepository.findAll(pageable);
        }
        return products;
    }

    public Product getProduct(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));
    }

    public Product createProduct(String name, BigDecimal price) {
        Product product = new Product(null, name, price);
        return productRepository.save(product);
    }

    @Transactional
    public void addToFavorites(String username, Long productId) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        user.addFavorite(product);
        userRepository.save(user);
    }

    @Transactional
    public void removeFromFavorites(String username, Long productId) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.removeFavorite(productId);
        userRepository.save(user);
    }
}
