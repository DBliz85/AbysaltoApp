package hr.abysalto.hiring.mid;

import hr.abysalto.hiring.mid.cart.app.usecase.RemoveItemFromCartService;
import hr.abysalto.hiring.mid.cart.app.exception.CartNotFoundException;
import hr.abysalto.hiring.mid.cart.app.port.out.UserGateway;
import hr.abysalto.hiring.mid.cart.domain.Cart;
import hr.abysalto.hiring.mid.cart.domain.CartRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RemoveItemFromCartServiceTest {

    @Mock
    private CartRepository cartRepository;
    @Mock
    private UserGateway userGateway;
    @InjectMocks
    private RemoveItemFromCartService removeItemUseCase;

    @Test
    void should_remove_item_from_cart_when_product_exists() {
        String username = "john";
        Long userId = 1L;
        Long productId = 100L;

        Cart cart = new Cart(userId);
        cart.addItem(productId, 2);

        when(userGateway.getUserIdByUsername(username)).thenReturn(userId);
        when(cartRepository.findByUserId(userId)).thenReturn(Optional.of(cart));
        when(cartRepository.save(any(Cart.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Cart result = removeItemUseCase.removeItem(username, productId);

        assertNotNull(result);
        assertTrue(result.getItems().isEmpty());
        verify(cartRepository, times(1)).save(cart);
    }

    @Test
    void should_throw_exception_when_product_not_in_cart() {
        String username = "john";
        Long userId = 1L;
        Long productId = 100L;

        Cart cart = new Cart(userId);
        when(userGateway.getUserIdByUsername(username)).thenReturn(userId);
        when(cartRepository.findByUserId(userId)).thenReturn(Optional.of(cart));

        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> removeItemUseCase.removeItem(username, productId));

        assertEquals(String.valueOf(100L), exception.getMessage());
        verify(cartRepository, never()).save(any());
    }

    @Test
    void should_throw_exception_when_cart_not_found() {
        String username = "john";
        Long userId = 1L;
        Long productId = 100L;

        when(userGateway.getUserIdByUsername(username)).thenReturn(userId);
        when(cartRepository.findByUserId(userId)).thenReturn(Optional.empty());

        CartNotFoundException exception = assertThrows(CartNotFoundException.class,
                () -> removeItemUseCase.removeItem(username, productId));

        assertEquals("Cart not found", exception.getMessage());
        verify(cartRepository, never()).save(any());
    }
}
