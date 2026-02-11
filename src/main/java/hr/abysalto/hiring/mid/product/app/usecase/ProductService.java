package hr.abysalto.hiring.mid.product.app.usecase;

import hr.abysalto.hiring.mid.common.mapper.ProductMapper;
import hr.abysalto.hiring.mid.product.domain.Product;
import hr.abysalto.hiring.mid.product.domain.ProductRepository;
import hr.abysalto.hiring.mid.product.dto.ProductDto;
import hr.abysalto.hiring.mid.product.infrastructure.persistance.client.DummyJsonClient;
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

    public ProductService(ProductRepository productRepository, DummyJsonClient dummyJsonClient) {
        this.productRepository = productRepository;
        this.dummyJsonClient = dummyJsonClient;
    }

    @Transactional
    public Page<ProductDto> getProducts(Pageable pageable) {
        Page<Product> products = productRepository.findAll(pageable);
        List<ProductDto> fetchedProducts = dummyJsonClient.fetchProducts();
        if (products.isEmpty()) {
            List<Product> domainProducts = fetchedProducts.stream()
                    .map(dto -> new Product(null, dto.title(), dto.price()))
                    .toList();
            productRepository.saveAll(domainProducts);
            products = productRepository.findAll(pageable);
        }

        return products.map(ProductMapper::toDto);
    }

    public ProductDto getProduct(Long id) {
        return dummyJsonClient.fetchProductById(id).map(ProductMapper::toDto).orElseThrow();
    }

    public ProductDto createProduct(String name, BigDecimal price) {
        Product product = new Product(null, name, price);
        return ProductMapper.toDto(
                productRepository.save(product)
        );
    }
}