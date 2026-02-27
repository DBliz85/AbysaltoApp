package hr.abysalto.hiring.mid;

import hr.abysalto.hiring.mid.product.app.usecase.ProductService;
import hr.abysalto.hiring.mid.product.app.usecase.exception.ProductNotFoundException;
import hr.abysalto.hiring.mid.product.domain.Product;
import hr.abysalto.hiring.mid.product.domain.ProductRepository;
import hr.abysalto.hiring.mid.product.dto.ProductDto;
import hr.abysalto.hiring.mid.product.infrastructure.persistence.client.DummyJsonClient;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;
    @Mock
    private DummyJsonClient dummyJsonClient;
    @InjectMocks
    private ProductService productService;

    @Test
    void should_get_products_from_repository() {
        Product p1 = new Product(1L, "Phone", BigDecimal.valueOf(500));
        Product p2 = new Product(2L, "Laptop", BigDecimal.valueOf(1000));
        Page<Product> page = new PageImpl<>(List.of(p1, p2));
        when(productRepository.findAll(PageRequest.of(0, 10))).thenReturn(page);

        Page<Product> result = productService.getProducts(PageRequest.of(0, 10));

        assertEquals(2, result.getContent().size());
        assertEquals("Phone", result.getContent().get(0).getTitle());
        verify(productRepository, times(1)).findAll(PageRequest.of(0, 10));
    }

    @Test
    void should_get_products_from_dummy_json_client() {
        List<Product> savedProducts = List.of(
                new Product(1L, "Tablet", BigDecimal.valueOf(300)),
                new Product(2L, "Monitor", BigDecimal.valueOf(200))
        );

        when(productRepository.findAll(PageRequest.of(0, 10)))
                .thenReturn(Page.empty())
                .thenReturn(new PageImpl<>(savedProducts));

        when(dummyJsonClient.fetchProducts())
                .thenReturn(List.of(
                        new ProductDto(null, "Tablet", BigDecimal.valueOf(300)),
                        new ProductDto(null, "Monitor", BigDecimal.valueOf(200))
                ));

        when(productRepository.saveAll(anyList()))
                .thenAnswer(invocation -> invocation.getArgument(0));

        Page<Product> result = productService.getProducts(PageRequest.of(0, 10));

        assertEquals(2, result.getContent().size());
        assertEquals("Tablet", result.getContent().get(0).getTitle());
        assertEquals("Monitor", result.getContent().get(1).getTitle());

        ArgumentCaptor<List<Product>> captor = ArgumentCaptor.forClass(List.class);
        verify(productRepository, times(1)).saveAll(captor.capture());

        List<Product> captured = captor.getValue();
        assertEquals(2, captured.size());
    }

    @Test
    void should_return_product_when_found_by_id() {
        Product product = new Product(1L, "Laptop", BigDecimal.valueOf(999.99));
        when(dummyJsonClient.fetchProductById(1L)).thenReturn(Optional.of(product));

        Product result = productService.getProductById(1L);

        assertNotNull(result);
        assertEquals("Laptop", result.getTitle());
    }

    @Test
    void should_throw_exception_when_product_not_found() {
        when(dummyJsonClient.fetchProductById(1L)).thenReturn(Optional.empty());

        assertThrows(ProductNotFoundException.class, () -> productService.getProductById(1L));
    }
}