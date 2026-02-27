package hr.abysalto.hiring.mid;

import hr.abysalto.hiring.mid.cart.app.usecase.GetCartService;
import hr.abysalto.hiring.mid.cart.app.port.out.UserGateway;
import hr.abysalto.hiring.mid.cart.domain.Cart;
import hr.abysalto.hiring.mid.cart.domain.CartRepository;
import hr.abysalto.hiring.mid.user.app.usecase.exception.UserNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GetCartServiceTest {

    @Mock
    CartRepository cartRepository;

    @Mock
    UserGateway userGateway;

    @InjectMocks
    GetCartService getCartUseCase;

    @Test
    void should_return_existing_cart_when_cart_exists() {
        String username = "john";
        Long userId = 1L;
        Cart existingCart = new Cart(userId);

        when(userGateway.getUserIdByUsername(username)).thenReturn(userId);
        when(cartRepository.findByUserId(userId)).thenReturn(Optional.of(existingCart));

        Cart result = getCartUseCase.getCart(username);

        assertNotNull(result);
        assertEquals(userId, result.getUserId());
        verify(cartRepository, never()).save(any());
    }

    @Test
    void should_create_and_return_cart_when_cart_does_not_exist() {
        String username = "john";
        Long userId = 1L;
        Cart newCart = new Cart(userId);

        when(userGateway.getUserIdByUsername(username)).thenReturn(userId);
        when(cartRepository.findByUserId(userId)).thenReturn(Optional.empty());
        when(cartRepository.save(any(Cart.class))).thenReturn(newCart);

        Cart result = getCartUseCase.getCart(username);

        assertNotNull(result);
        assertEquals(userId, result.getUserId());
        ArgumentCaptor<Cart> captor = ArgumentCaptor.forClass(Cart.class);
        verify(cartRepository).save(captor.capture());
        assertEquals(userId, captor.getValue().getUserId());
    }

    @Test
    void should_throw_exception_when_user_not_found() {
        when(userGateway.getUserIdByUsername("ghost"))
                .thenThrow(UserNotFoundException.class);

        assertThrows(
                UserNotFoundException.class,
                () -> getCartUseCase.getCart("ghost")
        );

        verifyNoInteractions(cartRepository);
    }
}
