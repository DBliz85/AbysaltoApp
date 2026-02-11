package hr.abysalto.hiring.mid;

import hr.abysalto.hiring.mid.cart.app.usecase.CartService;
import hr.abysalto.hiring.mid.cart.app.usecase.port.out.ProductGateway;
import hr.abysalto.hiring.mid.cart.app.usecase.port.out.UserGateway;
import hr.abysalto.hiring.mid.cart.domain.Cart;
import hr.abysalto.hiring.mid.cart.domain.CartItem;
import hr.abysalto.hiring.mid.cart.domain.CartRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CartServiceTest {

    @Mock
    private CartRepository cartRepository;

    @Mock
    private UserGateway userGateway;

    @Mock
    private ProductGateway productGateway;

    @InjectMocks
    private CartService cartService;

    private final String username = "abysalto";
    private final Long userId = 1L;
    private final Long productId = 100L;

    @Test
    void testGetCart_WhenCartExists() {
        Cart existingCart = new Cart(userId);
        existingCart.addItem(productId, 2);

        when(userGateway.getUserIdByUsername(username)).thenReturn(userId);
        when(cartRepository.findByUserId(userId)).thenReturn(Optional.of(existingCart));

        Cart cart = cartService.getCart(username);

        assertNotNull(cart);
        assertEquals(userId, cart.getUserId());
        assertEquals(1, cart.getItems().size());
        assertEquals(productId, cart.getItems().get(0).getProductId());
    }

    @Test
    void getCart_WhenCartDoesNotExist_CreatesAndReturnsCart() {
        when(userGateway.getUserIdByUsername(username)).thenReturn(userId);
        when(cartRepository.findByUserId(userId)).thenReturn(Optional.empty());
        when(cartRepository.save(any(Cart.class))).thenAnswer(i -> i.getArgument(0));

        Cart cart = cartService.getCart(username);

        assertNotNull(cart);
        assertEquals(userId, cart.getUserId());
        assertTrue(cart.getItems().isEmpty());

        verify(cartRepository).save(any(Cart.class));
    }

    @Test
    void addItem_WhenProductExists_AddsItemToCart() {
        Cart existingCart = new Cart(userId);

        when(userGateway.getUserIdByUsername(username)).thenReturn(userId);
        when(productGateway.exists(productId)).thenReturn(true);
        when(cartRepository.findByUserId(userId)).thenReturn(Optional.of(existingCart));
        when(cartRepository.save(any(Cart.class))).thenAnswer(i -> i.getArgument(0));

        Cart cart = cartService.addItem(username, productId, 3);

        assertEquals(1, cart.getItems().size());
        CartItem item = cart.getItems().get(0);
        assertEquals(productId, item.getProductId());
        assertEquals(3, item.getQuantity());
    }

    @Test
    void addItem_WhenProductDoesNotExist_ThrowsException() {
        when(userGateway.getUserIdByUsername(username)).thenReturn(userId);
        when(productGateway.exists(productId)).thenReturn(false);

        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> cartService.addItem(username, productId, 1));

        assertEquals("Product not found", exception.getMessage());
    }

    @Test
    void testRemoveItem_Success() {
        Cart existingCart = new Cart(userId);
        existingCart.addItem(productId, 5);

        when(userGateway.getUserIdByUsername(username)).thenReturn(userId);
        when(cartRepository.findByUserId(userId)).thenReturn(Optional.of(existingCart));
        when(cartRepository.save(any(Cart.class))).thenAnswer(i -> i.getArgument(0));

        Cart cart = cartService.removeItem(username, productId);

        assertTrue(cart.getItems().isEmpty());
    }

    @Test
    void removeItem_WhenCartNotFound_ThrowsException() {
        when(userGateway.getUserIdByUsername(username)).thenReturn(userId);
        when(cartRepository.findByUserId(userId)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> cartService.removeItem(username, productId));

        assertEquals("Cart not found", exception.getMessage());
    }
}