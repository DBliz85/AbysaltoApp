package hr.abysalto.hiring.mid;

import hr.abysalto.hiring.mid.product.domain.Product;
import hr.abysalto.hiring.mid.product.persistance.repository.ProductRepository;
import hr.abysalto.hiring.mid.product.app.ProductService;
import hr.abysalto.hiring.mid.user.domain.User;
import hr.abysalto.hiring.mid.user.persistance.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private ProductService productService;

    private User user;
    private Product product;

    @BeforeEach
    void setup() {
        user = new User(1L, "alice", "hash");
        product = new Product(2L, "Laptop", BigDecimal.valueOf(1200));
    }

    @Test
    void testAddToFavorites_Success() {
        when(userRepository.findByUsername("alice"))
                .thenReturn(Optional.of(user));
        when(productRepository.findById(2L))
                .thenReturn(Optional.of(product));
        when(userRepository.save(any(User.class)))
                .thenAnswer(inv -> inv.getArgument(0));

        productService.addToFavorites("alice", 2L);

        assertEquals(1, user.getFavorites().size());
        assertEquals(product, user.getFavorites().get(0));

        verify(userRepository).save(user);
    }

    @Test
    void testAddToFavorites_UserNotFound() {
        when(userRepository.findByUsername("alice"))
                .thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> productService.addToFavorites("alice", 2L));

        assertEquals("User not found", ex.getMessage());

        verify(productRepository, never()).findById(anyLong());
        verify(userRepository, never()).save(any());
    }

    @Test
    void testAddToFavorites_ProductNotFound() {
        when(userRepository.findByUsername("alice"))
                .thenReturn(Optional.of(user));
        when(productRepository.findById(2L))
                .thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> productService.addToFavorites("alice", 2L));

        assertEquals("Product not found", ex.getMessage());

        verify(userRepository, never()).save(any());
    }

    @Test
    void testAddToFavorites_DuplicateProductIgnored() {
        when(userRepository.findByUsername("alice"))
                .thenReturn(Optional.of(user));
        when(productRepository.findById(2L))
                .thenReturn(Optional.of(product));
        when(userRepository.save(any(User.class)))
                .thenAnswer(inv -> inv.getArgument(0));

        productService.addToFavorites("alice", 2L);
        productService.addToFavorites("alice", 2L);

        assertEquals(1, user.getFavorites().size());
        verify(userRepository, times(2)).save(user);
    }

    @Test
    void testAddToFavorites_UserIsModifiedBeforeSave() {
        when(userRepository.findByUsername("alice"))
                .thenReturn(Optional.of(user));
        when(productRepository.findById(2L))
                .thenReturn(Optional.of(product));
        when(userRepository.save(any(User.class)))
                .thenAnswer(inv -> inv.getArgument(0));

        productService.addToFavorites("alice", 2L);

        ArgumentCaptor<User> captor = ArgumentCaptor.forClass(User.class);
        verify(userRepository).save(captor.capture());

        User savedUser = captor.getValue();
        assertTrue(savedUser.getFavorites().contains(product));
    }
}