package hr.abysalto.hiring.mid.product.infrastructure.persistance.client;

import hr.abysalto.hiring.mid.product.domain.Product;

import java.util.List;
import java.util.Optional;

public interface DummyJsonClient {

    List<Product> fetchProducts();

    Optional<Product> fetchProductById(Long id);
}