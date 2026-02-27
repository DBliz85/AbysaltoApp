package hr.abysalto.hiring.mid;

import hr.abysalto.hiring.mid.security.JwtUtil;
import hr.abysalto.hiring.mid.user.app.usecase.LoginUserService;
import hr.abysalto.hiring.mid.user.app.usecase.exception.InvalidCredentialsException;
import hr.abysalto.hiring.mid.user.app.usecase.exception.UserNotFoundException;
import hr.abysalto.hiring.mid.user.domain.User;
import hr.abysalto.hiring.mid.user.domain.UserRepository;
import hr.abysalto.hiring.mid.user.dto.LoginResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LoginUserServiceTest {
    @Mock
    private UserRepository userRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private JwtUtil jwtUtil;
    @InjectMocks
    private LoginUserService loginUser;

    @Test
    void should_login_user_when_credentials_are_valid() {
        User user = new User(1L, "john", "testPassword");
        when(userRepository.findByUsername("john")).thenReturn(Optional.of(user));
        when(passwordEncoder.matches("password123", "testPassword")).thenReturn(true);
        when(jwtUtil.generateToken("john")).thenReturn("jwt-token");

        LoginResponse response = loginUser.login("john", "password123");

        assertEquals("john", response.username());
        assertEquals("jwt-token", response.token());
    }

    @Test
    void should_throw_exception_when_password_is_invalid() {
        User user = new User(1L, "john", "testPassword");
        when(userRepository.findByUsername("john")).thenReturn(Optional.of(user));
        when(passwordEncoder.matches("wrongPassword", "testPassword")).thenReturn(false);

        assertThrows(
                InvalidCredentialsException.class,
                () -> loginUser.login("john", "wrongPassword")
        );

        verify(jwtUtil, never()).generateToken(any());
    }

    @Test
    void should_throw_exception_when_user_not_found() {
        when(userRepository.findByUsername("john")).thenReturn(Optional.empty());

        assertThrows(
                UserNotFoundException.class,
                () -> loginUser.login("john", "password123")
        );

        verifyNoInteractions(passwordEncoder, jwtUtil);
    }
}
