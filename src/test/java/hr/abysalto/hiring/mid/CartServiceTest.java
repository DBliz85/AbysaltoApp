package hr.abysalto.hiring.mid;

import hr.abysalto.hiring.mid.cart.app.usecase.CartService;
import hr.abysalto.hiring.mid.cart.domain.Cart;
import hr.abysalto.hiring.mid.cart.domain.CartItem;
import hr.abysalto.hiring.mid.cart.domain.CartRepository;
import hr.abysalto.hiring.mid.product.domain.Product;
import hr.abysalto.hiring.mid.product.domain.ProductRepository;
import hr.abysalto.hiring.mid.user.domain.User;
import hr.abysalto.hiring.mid.user.domain.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;

import java.math.BigDecimal;
import java.util.ArrayList;
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
    Cart cart;

    @BeforeEach
    void setup() {
        user = new User(1L, "dejan", "secret");
        product = new Product(10L, "Keyboard", BigDecimal.valueOf(111));
        cart = new Cart(1L, 1L, new ArrayList<>());
    }

    @Test
    void testGetCart_WhenCartExists() {
        Cart existingCart = new Cart(user.getId());
        when(authentication.getName()).thenReturn("dejan");
        when(userRepository.findByUsername("dejan")).thenReturn(Optional.of(user));
        when(cartRepository.findByUserId(user.getId())).thenReturn(Optional.of(existingCart));

        cart = cartService.getCart(authentication);

        assertEquals(existingCart, cart);
        verify(cartRepository, never()).save(any());
    }

    @Test
    void testGetCart_WhenCartDoesNotExist() {
        when(authentication.getName()).thenReturn("dejan");
        when(userRepository.findByUsername("dejan")).thenReturn(Optional.of(user));
        when(cartRepository.findByUserId(user.getId())).thenReturn(Optional.empty());
        when(cartRepository.save(any(Cart.class))).thenAnswer(i -> i.getArgument(0));

        cart = cartService.getCart(authentication);

        assertNotNull(cart);
        assertEquals(user.getId(), cart.getUserId());
        verify(cartRepository, times(1)).save(any(Cart.class));
    }

    @Test
    void testAddItem_Success() {
        when(authentication.getName()).thenReturn("dejan");
        when(userRepository.findByUsername("dejan")).thenReturn(Optional.of(user));
        when(cartRepository.findByUserId(user.getId())).thenReturn(Optional.of(cart));
        when(cartRepository.save(any(Cart.class))).thenAnswer(i -> i.getArgument(0));

        when(productRepository.findById(10L)).thenReturn(Optional.of(product));

        cart = cartService.addItem(authentication, 10L, 2);

        assertNotNull(cart);
        assertEquals(user.getId(), cart.getUserId());
        assertEquals(1, cart.getItems().size());
        CartItem item = cart.getItems().get(0);
        assertEquals(product.getId(), item.getProductId());
        assertEquals(2, item.getQuantity());
        verify(cartRepository, times(1)).save(any(Cart.class));
    }

    @Test
    void testRemoveItem_Success() {
        Cart cart = new Cart(1L, user.getId(), new ArrayList<>());
        cart.addItem(product, 2);

        when(authentication.getName()).thenReturn("dejan");
        when(userRepository.findByUsername("dejan")).thenReturn(Optional.of(user));
        when(cartRepository.findByUserId(user.getId())).thenReturn(Optional.of(cart));
        when(cartRepository.save(any(Cart.class))).thenAnswer(i -> i.getArgument(0));

        cart = cartService.removeItem(authentication, 10L);

        assertNotNull(cart);
        assertTrue(cart.isEmpty());
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
}