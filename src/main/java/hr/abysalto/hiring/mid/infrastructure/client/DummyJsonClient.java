package hr.abysalto.hiring.mid.infrastructure.client;

import hr.abysalto.hiring.mid.product.Product;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Component
public class DummyJsonClient {

    public List<Product> fetchProducts() {
        return List.of(
                new Product(1L, "Laptop", BigDecimal.valueOf(1200)),
                new Product(2L, "Phone", BigDecimal.valueOf(800))
        );
    }
}