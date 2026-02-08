package hr.abysalto.hiring.mid;

import hr.abysalto.hiring.mid.app.service.ProductService;
import hr.abysalto.hiring.mid.domain.model.Product;
import hr.abysalto.hiring.mid.infrastructure.client.DummyJsonClient;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ProductServiceTest {

    @Autowired
    private DummyJsonClient dummyJsonClient;

    @InjectMocks
    private ProductService productService;

    @Test
    void fetchProducts_returnsAllProducts() {
        List<Product> products = dummyJsonClient.fetchProducts();

        assertNotNull(products);
        assertEquals(2, products.size());

        Product first = products.get(0);
        assertEquals(1L, first.getId());
        assertEquals("Laptop", first.getTitle());
        assertEquals(BigDecimal.valueOf(1200), first.getPrice());

        Product second = products.get(1);
        assertEquals(2L, second.getId());
        assertEquals("Phone", second.getTitle());
        assertEquals(BigDecimal.valueOf(800), second.getPrice());
    }
    @Test
    void fetchProducts_containsLaptop() {
        List<Product> products = dummyJsonClient.fetchProducts();

        assertTrue(products.stream().anyMatch(p -> "Laptop".equals(p.getTitle())));
    }

    @Test
    void fetchProducts_priceGreaterThan500() {
        List<Product> products = dummyJsonClient.fetchProducts();
        products.forEach(p -> assertTrue(p.getPrice().compareTo(BigDecimal.valueOf(500)) > 0));
    }
}