package hr.abysalto.hiring.mid;

import hr.abysalto.hiring.mid.cart.app.usecase.AddItemToCartService;
import hr.abysalto.hiring.mid.cart.app.port.out.ProductGateway;
import hr.abysalto.hiring.mid.cart.app.port.out.UserGateway;
import hr.abysalto.hiring.mid.cart.domain.Cart;
import hr.abysalto.hiring.mid.cart.domain.CartItem;
import hr.abysalto.hiring.mid.cart.domain.CartRepository;
import hr.abysalto.hiring.mid.product.app.usecase.exception.ProductNotFoundException;
import hr.abysalto.hiring.mid.user.app.usecase.exception.UserNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.AdditionalAnswers;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AddItemToCartServiceTest {
    @Mock
    CartRepository cartRepository;

    @Mock
    UserGateway userGateway;

    @Mock
    ProductGateway productGateway;

    @InjectMocks
    AddItemToCartService addItemToCartService;

    @Test
    void should_add_product_to_existing_cart() {
        String username = "john";
        Long userId = 1L;
        Long productId = 100L;
        int quantity = 2;

        Cart existingCart = new Cart(userId);

        mockValidUserAndProduct(username, userId, productId);
        when(cartRepository.findByUserId(userId)).thenReturn(Optional.of(existingCart));
        when(cartRepository.save(any(Cart.class)))
                .then(AdditionalAnswers.returnsFirstArg());

        Cart result = addItemToCartService.addItem(username, productId, quantity);
        CartItem item = result.getItems().stream()
                .filter(i -> i.getProductId().equals(productId))
                .findFirst()
                .orElseThrow(() -> new AssertionError("Product not found in cart"));

        assertNotNull(result);
        assertEquals(1, result.getItems().size());
        assertEquals(quantity, item.getQuantity());

        verify(cartRepository, times(1)).save(existingCart);
    }

    @Test
    void should_create_new_cart_if_not_exists() {
        String username = "alice";
        Long userId = 2L;
        Long productId = 200L;
        int quantity = 1;

        mockValidUserAndProduct(username, userId, productId);
        when(cartRepository.findByUserId(userId)).thenReturn(Optional.empty());
        when(cartRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        Cart result = addItemToCartService.addItem(username, productId, quantity);

        CartItem item = result.getItems().stream()
                .filter(i -> i.getProductId().equals(productId))
                .findFirst()
                .orElseThrow(() -> new AssertionError("Product not found in cart"));
        assertNotNull(result);
        assertEquals(userId, result.getUserId());
        assertEquals(1, result.getItems().size());
        assertEquals(quantity, item.getQuantity());

        ArgumentCaptor<Cart> captor = ArgumentCaptor.forClass(Cart.class);
        verify(cartRepository).save(captor.capture());
        Cart savedCart = captor.getValue();
        assertEquals(userId, savedCart.getUserId());
    }

    @Test
    void should_throw_exception_if_product_does_not_exist() {
        String username = "bob";
        Long userId = 3L;
        Long productId = 999L;

        when(userGateway.getUserIdByUsername(username)).thenReturn(userId);
        when(productGateway.exists(productId)).thenReturn(false);

        ProductNotFoundException exception = assertThrows(ProductNotFoundException.class,
                () -> addItemToCartService.addItem(username, productId, 1));

        assertTrue(exception.getMessage().contains("999"));
        verify(cartRepository, never()).save(any());
    }

    @Test
    void should_throw_exception_when_quantity_is_zero_or_negative() {
        assertThrows(IllegalArgumentException.class,
                () -> addItemToCartService.addItem("john", 100L, 0));

        verify(cartRepository, never()).save(any());
    }

    @Test
    void should_throw_exception_when_user_not_found() {
        when(userGateway.getUserIdByUsername("ghost"))
                .thenThrow(UserNotFoundException.class);

        assertThrows(UserNotFoundException.class,
                () -> addItemToCartService.addItem("ghost", 1L, 1));

        verifyNoInteractions(cartRepository);
    }

    private void mockValidUserAndProduct(String username, Long userId, Long productId) {
        when(userGateway.getUserIdByUsername(username)).thenReturn(userId);
        when(productGateway.exists(productId)).thenReturn(true);
    }
}
