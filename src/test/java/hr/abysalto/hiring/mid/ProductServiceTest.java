package hr.abysalto.hiring.mid;

import hr.abysalto.hiring.mid.product.app.usecase.ProductService;
import hr.abysalto.hiring.mid.product.domain.Product;
import hr.abysalto.hiring.mid.product.domain.ProductRepository;
import hr.abysalto.hiring.mid.product.dto.ProductDto;
import hr.abysalto.hiring.mid.product.infrastructure.persistance.client.DummyJsonClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.math.BigDecimal;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ProductServiceTest {

    private ProductRepository productRepository;
    private DummyJsonClient dummyJsonClient;
    private ProductService productService;

    @BeforeEach
    void setUp() {
        productRepository = mock(ProductRepository.class);
        dummyJsonClient = mock(DummyJsonClient.class);
        productService = new ProductService(productRepository, dummyJsonClient);
    }

    @Test
    void testGetProducts_FromRepository() {
        Product p1 = new Product(1L, "Phone", BigDecimal.valueOf(500));
        Product p2 = new Product(2L, "Laptop", BigDecimal.valueOf(1000));
        Page<Product> page = new PageImpl<>(List.of(p1, p2));
        when(productRepository.findAll(PageRequest.of(0, 10))).thenReturn(page);

        Page<ProductDto> result = productService.getProducts(PageRequest.of(0, 10));

        assertEquals(2, result.getContent().size());
        assertEquals("Phone", result.getContent().get(0).title());
        verify(productRepository, times(1)).findAll(PageRequest.of(0, 10));
    }

    @Test
    void testGetProducts_FromDummyJsonClient() {
        when(productRepository.findAll(PageRequest.of(0, 10)))
                .thenReturn(Page.empty());

        ProductDto dto1 = new ProductDto(null, "Tablet", BigDecimal.valueOf(300));
        ProductDto dto2 = new ProductDto(null, "Monitor", BigDecimal.valueOf(200));
        when(dummyJsonClient.fetchProducts()).thenReturn(List.of(dto1, dto2));

        ArgumentCaptor<List<Product>> captor = ArgumentCaptor.forClass(List.class);
        when(productRepository.saveAll(captor.capture())).thenReturn(null);
        when(productRepository.findAll(PageRequest.of(0, 10)))
                .thenReturn(new PageImpl<>(List.of(
                        new Product(1L, "Tablet", BigDecimal.valueOf(300)),
                        new Product(2L, "Monitor", BigDecimal.valueOf(200))
                )));

        Page<ProductDto> result = productService.getProducts(PageRequest.of(0, 10));

        assertEquals(2, result.getContent().size());
        assertEquals("Tablet", result.getContent().get(0).title());
        verify(productRepository, times(1)).findAll(PageRequest.of(0, 10));
    }

    @Test
    void testGetProduct_Found() {
        Product product = new Product(1L, "Laptop", BigDecimal.valueOf(999.99));
        when(dummyJsonClient.fetchProductById(1L)).thenReturn(Optional.of(product));

        ProductDto result = productService.getProduct(1L);

        assertNotNull(result);
        assertEquals("Laptop", result.title());
    }

    @Test
    void testGetProduct_NotFound() {
        when(dummyJsonClient.fetchProductById(1L)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> productService.getProduct(1L));
    }

    @Test
    void testCreateProduct() {
        Product product = new Product(1L, "Keyboard", BigDecimal.valueOf(100));
        when(productRepository.save(any(Product.class))).thenReturn(product);

        ProductDto result = productService.createProduct("Keyboard", BigDecimal.valueOf(100));

        assertEquals("Keyboard", result.title());
        assertEquals(BigDecimal.valueOf(100), result.price());
        verify(productRepository, times(1)).save(any(Product.class));
    }
}