package hr.abysalto.hiring.mid.product.app.usecase;

import hr.abysalto.hiring.mid.product.app.usecase.exception.ProductNotFoundException;
import hr.abysalto.hiring.mid.product.domain.Product;
import hr.abysalto.hiring.mid.product.domain.ProductRepository;
import hr.abysalto.hiring.mid.product.dto.ProductDto;
import hr.abysalto.hiring.mid.product.infrastructure.persistence.client.DummyJsonClient;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService implements ProductUseCase {
    private final ProductRepository productRepository;
    private final DummyJsonClient dummyJsonClient;

    public ProductService(ProductRepository productRepository, @Qualifier("dummyJsonClientImpl") DummyJsonClient dummyJsonClient) {
        this.productRepository = productRepository;
        this.dummyJsonClient = dummyJsonClient;
    }

    @Transactional
    public Page<Product> getProducts(Pageable pageable) {
        Page<Product> products = productRepository.findAll(pageable);
        List<ProductDto> fetchedProducts = dummyJsonClient.fetchProducts();
        if (products.isEmpty()) {
            List<Product> domainProducts = fetchedProducts.stream()
                    .map(dto -> new Product(null, dto.title(), dto.price()))
                    .toList();
            productRepository.saveAll(domainProducts);
            products = productRepository.findAll(pageable);
        }

        return products;
    }

    @Override
    public Product getProductById(Long id) {
        return dummyJsonClient.fetchProductById(id)
                .map(dto -> new Product(dto.getId(), dto.getTitle(), dto.getPrice()))
                .orElseThrow(() -> new ProductNotFoundException(id));
    }
}