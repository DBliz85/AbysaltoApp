package hr.abysalto.hiring.mid;

import hr.abysalto.hiring.mid.cart.Cart;
import hr.abysalto.hiring.mid.cart.CartRepository;
import hr.abysalto.hiring.mid.cart.CartService;
import hr.abysalto.hiring.mid.product.Product;
import hr.abysalto.hiring.mid.product.ProductRepository;
import hr.abysalto.hiring.mid.user.User;
import hr.abysalto.hiring.mid.user.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CartServiceTest {

    @Mock
    CartRepository cartRepository;

    @Mock
    ProductRepository productRepository;

    @Mock
    UserRepository userRepository;

    @Mock
    Authentication authentication;

    @InjectMocks
    CartService cartService;

    User user;
    Product product;

    @BeforeEach
    void setup() {
        user = new User(1L, "dejan", "secret");

        product = new Product(10L, "Keyboard", BigDecimal.valueOf(111));
    }

    @Test
    void testGetCart_WhenCartExists() {
        Cart existingCart = new Cart(user.getId());
        when(authentication.getName()).thenReturn("dejan");
        when(userRepository.findByUsername("dejan")).thenReturn(Optional.of(user));
        when(cartRepository.findByUser(user)).thenReturn(Optional.of(existingCart));

        Cart cart = cartService.getCart(authentication);

        assertEquals(existingCart, cart);
        verify(cartRepository, never()).save(any()); // should not save new cart
    }

    @Test
    void testGetCart_WhenCartDoesNotExist() {
        when(authentication.getName()).thenReturn("dejan");
        when(userRepository.findByUsername("dejan")).thenReturn(Optional.of(user));
        when(cartRepository.findByUser(user)).thenReturn(Optional.empty());
        when(cartRepository.save(any(Cart.class))).thenAnswer(i -> i.getArgument(0));

        Cart cart = cartService.getCart(authentication);

        assertNotNull(cart);
        assertEquals(user.getId(), cart.getUserId());
        verify(cartRepository, times(1)).save(any(Cart.class));
    }

    @Test
    void testAddItem_Success() {
        when(authentication.getName()).thenReturn("dejan");
        when(userRepository.findByUsername("dejan")).thenReturn(Optional.of(user));
        when(cartRepository.findByUser(user)).thenReturn(Optional.empty());
        when(cartRepository.save(any(Cart.class))).thenAnswer(i -> i.getArgument(0));
        when(productRepository.findById(10L)).thenReturn(Optional.of(product));

        Cart cart = cartService.addItem(authentication, 10L, 2);

        assertNotNull(cart);
        assertEquals(user.getId(), cart.getUserId());
        assertFalse(cart.isEmpty());
        verify(cartRepository, times(2)).save(any(Cart.class)); // one for new cart, one after adding item
    }

    @Test
    void testRemoveItem_Success() {
        Cart cart = new Cart(user.getId());
        cart.addItem(product, 2);

        when(authentication.getName()).thenReturn("dejan");
        when(userRepository.findByUsername("dejan")).thenReturn(Optional.of(user));
        when(cartRepository.findByUser(user)).thenReturn(Optional.of(cart));
        when(cartRepository.save(any(Cart.class))).thenAnswer(i -> i.getArgument(0));

        Cart result = cartService.removeItem(authentication, 10L);

        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(cartRepository, times(1)).save(cart);
    }

    @Test
    void testGetUser_NotFound() {
        when(authentication.getName()).thenReturn("unknown");
        when(userRepository.findByUsername("unknown")).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> cartService.getCart(authentication));

        assertEquals("User not found", exception.getMessage());
    }

    @Test
    void testGetProduct_NotFound() {
        when(authentication.getName()).thenReturn("dejan");
        when(userRepository.findByUsername("dejan")).thenReturn(Optional.of(user));
        when(cartRepository.findByUser(user)).thenReturn(Optional.empty());
        when(productRepository.findById(999L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> cartService.addItem(authentication, 999L, 1));

        assertEquals("Product not found", exception.getMessage());
    }
}