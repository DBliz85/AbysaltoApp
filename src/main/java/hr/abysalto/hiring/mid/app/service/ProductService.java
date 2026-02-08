package hr.abysalto.hiring.mid.app.service;

import hr.abysalto.hiring.mid.domain.model.Product;
import hr.abysalto.hiring.mid.domain.repository.ProductRepository;
import hr.abysalto.hiring.mid.infrastructure.client.DummyJsonClient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final DummyJsonClient dummyJsonClient;

    public ProductService(ProductRepository productRepository, DummyJsonClient dummyJsonClient) {
        this.productRepository = productRepository;
        this.dummyJsonClient = dummyJsonClient;
    }

    public Page<Product> getProducts(Pageable pageable) {
        Page<Product> products = productRepository.findAll(pageable);
        if (products.isEmpty()) {
            dummyJsonClient.fetchProducts().forEach(productRepository::save);
            products = productRepository.findAll(pageable);
        }
        return products;
    }

    public Product getProduct(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));
    }
}
