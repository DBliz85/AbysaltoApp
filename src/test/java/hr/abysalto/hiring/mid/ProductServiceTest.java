package hr.abysalto.hiring.mid;

import hr.abysalto.hiring.mid.product.app.usecase.port.out.ProductPersistencePort;
import hr.abysalto.hiring.mid.product.domain.Product;
import hr.abysalto.hiring.mid.product.app.usecase.service.ProductService;
import hr.abysalto.hiring.mid.product.dto.ProductDto;
import hr.abysalto.hiring.mid.product.infrastructure.persistance.client.DummyJsonClientImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {

    @Mock
    private DummyJsonClientImpl dummyJsonClient;

    @InjectMocks
    private ProductService productService;

    private List<Product> products;

    @BeforeEach
    void setUp() {
        products = List.of(
                new Product(1L, "Product 1", BigDecimal.valueOf(10)),
                new Product(2L, "Product 2", BigDecimal.valueOf(20)),
                new Product(3L, "Product 3", BigDecimal.valueOf(30))
        );
    }
    @Test
    void getAllProducts_shouldReturnFirstPage() {
        Pageable pageable = PageRequest.of(0, 2);

        when(dummyJsonClient.fetchProducts()).thenReturn(products);

        Page<ProductDto> result = productService.getAllProducts(pageable);

        assertEquals(2, result.getContent().size());
        assertEquals(3, result.getTotalElements());
        assertEquals("Product 1", result.getContent().get(0).title());
        assertEquals("Product 2", result.getContent().get(1).title());

        verify(dummyJsonClient).fetchProducts();
    }

    @Test
    void getAllProducts_shouldReturnSecondPage() {
        Pageable pageable = PageRequest.of(1, 2); // page 1, size 2

        when(dummyJsonClient.fetchProducts()).thenReturn(products);

        Page<ProductDto> result = productService.getAllProducts(pageable);

        assertEquals(1, result.getContent().size());
        assertEquals("Product 3", result.getContent().get(0).title());

        verify(dummyJsonClient).fetchProducts();
    }

    @Test
    void getAllProducts_shouldReturnEmptyPageIfOutOfRange() {
        Pageable pageable = PageRequest.of(5, 10); // page beyond total products

        when(dummyJsonClient.fetchProducts()).thenReturn(products);

        Page<ProductDto> result = productService.getAllProducts(pageable);

        assertTrue(result.isEmpty());

        verify(dummyJsonClient).fetchProducts();
    }

    @Test
    void getProductById_shouldReturnProduct() {
        Product product = new Product(1L, "Product 1", BigDecimal.valueOf(10));

        when(dummyJsonClient.fetchProductById(1L)).thenReturn(Optional.of(product));

        ProductDto dto = productService.getProductById(1L);

        assertNotNull(dto);
        assertEquals(1L, dto.id());
        assertEquals("Product 1", dto.title());
        assertEquals(BigDecimal.valueOf(10), dto.price());

        verify(dummyJsonClient).fetchProductById(1L);
    }

    @Test
    void getProductById_shouldThrowIfNotFound() {
        when(dummyJsonClient.fetchProductById(99L)).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> productService.getProductById(99L));

        assertEquals("Product not found with id: 99", ex.getMessage());

        verify(dummyJsonClient).fetchProductById(99L);
    }
}