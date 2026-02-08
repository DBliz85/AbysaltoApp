package hr.abysalto.hiring.mid;

import hr.abysalto.hiring.mid.app.service.CartService;
import hr.abysalto.hiring.mid.domain.model.Cart;
import hr.abysalto.hiring.mid.domain.model.Product;
import hr.abysalto.hiring.mid.domain.model.User;
import hr.abysalto.hiring.mid.domain.repository.CartRepository;
import hr.abysalto.hiring.mid.domain.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class CartServiceTest {

    @Mock
    private CartRepository cartRepository;

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private CartService cartService;

    private User user;
    private Product laptop;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        user = new User(1L, "dejan", "password");
        laptop = new Product(1L, "Laptop", BigDecimal.valueOf(1200));
    }

    @Test
    void addItem_existingCart_addsProduct() {
        Cart existingCart = new Cart(user);
        when(cartRepository.findByUser(user)).thenReturn(Optional.of(existingCart));
        when(productRepository.findById(anyLong())).thenReturn(Optional.of(laptop));
        when(cartRepository.save(any(Cart.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Cart updatedCart = cartService.addItem(user, 1L, 2);

        assertEquals(1, updatedCart.getItems().size());
        assertEquals(2, updatedCart.getItems().get(0).getQuantity());

        verify(cartRepository).findByUser(user);
        verify(productRepository).findById(1L);
        verify(cartRepository).save(existingCart);
    }

    @Test
    void addItem_noExistingCart_createsNewCart() {
        when(cartRepository.findByUser(user)).thenReturn(Optional.empty());
        when(productRepository.findById(anyLong())).thenReturn(Optional.of(laptop));
        when(cartRepository.save(any(Cart.class))).thenAnswer(i -> i.getArgument(0));

        Cart cart = cartService.addItem(user, 1L, 1);

        assertNotNull(cart);
        assertEquals(user, cart.getUser());
        assertEquals(1, cart.getItems().size());
    }

    @Test
    void removeItem_existingProduct_removesFromCart() {
        Cart cart = new Cart(user);
        cart.addItem(laptop, 2);

        when(cartRepository.findByUser(user)).thenReturn(Optional.of(cart));
        when(cartRepository.save(any(Cart.class))).thenAnswer(i -> i.getArgument(0));

        Cart updatedCart = cartService.removeItem(user, 1L);

        assertTrue(updatedCart.getItems().isEmpty());
        verify(cartRepository).findByUser(user);
        verify(cartRepository).save(cart);
    }

    @Test
    void getCart_existingCart_returnsCart() {
        Cart cart = new Cart(user);
        when(cartRepository.findByUser(user)).thenReturn(Optional.of(cart));

        Cart result = cartService.getCart(user);

        assertEquals(cart, result);
    }

    @Test
    void getCart_noCart_returnsNewCart() {
        when(cartRepository.findByUser(user)).thenReturn(Optional.empty());

        Cart result = cartService.getCart(user);

        assertNotNull(result);
        assertEquals(user, result.getUser());
        assertTrue(result.getItems().isEmpty());
    }

    @Test
    void addItem_nonExistingProduct_throwsException() {
        when(cartRepository.findByUser(user)).thenReturn(Optional.empty());
        when(productRepository.findById(999L)).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class, () -> {
            cartService.addItem(user, 999L, 1);
        });

        assertEquals("Product not found", ex.getMessage());
    }

    @Test
    void removeItem_noCart_throwsException() {
        when(cartRepository.findByUser(user)).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class, () -> {
            cartService.removeItem(user, 1L);
        });

        assertEquals("Cart not found", ex.getMessage());
    }
}