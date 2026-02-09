package hr.abysalto.hiring.mid;

import hr.abysalto.hiring.mid.infrastructure.client.DummyJsonClient;
import hr.abysalto.hiring.mid.product.domain.Product;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class DummyJsonClientTest {

    @Autowired
    private DummyJsonClient dummyJsonClient;

    @Test
    void fetchProducts_returnsAllProducts() {
        List<Product> products = dummyJsonClient.fetchProducts();

        assertNotNull(products);
        assertEquals(2, products.size());
    }

    @Test
    void fetchProducts_containsLaptop() {
        List<Product> products = dummyJsonClient.fetchProducts();

        assertTrue(products.stream()
                .anyMatch(p -> "Laptop".equals(p.getName())));
    }

    @Test
    void fetchProducts_priceGreaterThan500() {
        List<Product> products = dummyJsonClient.fetchProducts();

        products.forEach(p ->
                assertTrue(p.getPrice().compareTo(BigDecimal.valueOf(500)) > 0)
        );
    }
}