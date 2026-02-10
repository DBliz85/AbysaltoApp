package hr.abysalto.hiring.mid.product.app.usecase;

import hr.abysalto.hiring.mid.common.mapper.ProductMapper;
import hr.abysalto.hiring.mid.infrastructure.client.DummyJsonClient;
import hr.abysalto.hiring.mid.product.app.usecase.exception.ProductNotFoundException;
import hr.abysalto.hiring.mid.product.domain.Product;
import hr.abysalto.hiring.mid.product.dto.ProductDto;
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
    public Page<ProductDto> getProducts(Pageable pageable) {
        Page<Product> products = productRepository.findAll(pageable);

        if (products.isEmpty()) {
            List<ProductDto> fetchedProducts = dummyJsonClient.fetchProducts();
            productRepository.saveAll(fetchedProducts);
            products = productRepository.findAll(pageable);
        }

        return products.map(ProductMapper::toDto);
    }

    public ProductDto getProduct(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));
        return ProductMapper.toDto(product);
    }

    public ProductDto createProduct(String name, BigDecimal price) {
        Product product = new Product(null, name, price);
        return ProductMapper.toDto(
                productRepository.save(product)
        );
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
