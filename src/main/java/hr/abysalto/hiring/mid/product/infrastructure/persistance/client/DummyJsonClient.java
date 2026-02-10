package hr.abysalto.hiring.mid.product.infrastructure.persistance.client;

import hr.abysalto.hiring.mid.product.domain.Product;
import hr.abysalto.hiring.mid.product.dto.ProductDto;

import java.util.List;
import java.util.Optional;

public interface DummyJsonClient {

    List<ProductDto> fetchProducts();

    Optional<Product> fetchProductById(Long id);
}