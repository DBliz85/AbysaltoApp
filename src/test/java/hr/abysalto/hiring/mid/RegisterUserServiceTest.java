package hr.abysalto.hiring.mid;

import hr.abysalto.hiring.mid.user.app.usecase.RegisterUserService;
import hr.abysalto.hiring.mid.user.domain.User;
import hr.abysalto.hiring.mid.user.domain.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RegisterUserServiceTest {
    @Mock
    private UserRepository userRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @InjectMocks
    private RegisterUserService registerUser;

    private static final String USERNAME = "john";
    private static final String PASSWORD = "password123";

    @Test
    void register_creates_user_with_encoded_password() {
        when(passwordEncoder.encode(PASSWORD)).thenReturn(PASSWORD);

        registerUser.register(USERNAME, PASSWORD);

        ArgumentCaptor<User> captor = ArgumentCaptor.forClass(User.class);
        verify(userRepository).save(captor.capture());

        User saved = captor.getValue();
        assertEquals(USERNAME, saved.getUsername());
        assertEquals(PASSWORD, saved.getPassword());
    }

    @Test
    void should_encode_password_before_saving() {
        when(passwordEncoder.encode(PASSWORD)).thenReturn("encoded");

        registerUser.register(USERNAME, PASSWORD);

        InOrder inOrder = inOrder(passwordEncoder, userRepository);
        inOrder.verify(passwordEncoder).encode(PASSWORD);
        inOrder.verify(userRepository).save(any(User.class));
    }

    @Test
    void should_save_user_exactly_once() {
        when(passwordEncoder.encode(any())).thenReturn("encoded");

        registerUser.register(USERNAME, PASSWORD);

        verify(userRepository, times(1)).save(any(User.class));
    }
}
