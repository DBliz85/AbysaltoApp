package hr.abysalto.hiring.mid;

import hr.abysalto.hiring.mid.app.service.UserService;
import hr.abysalto.hiring.mid.domain.model.User;
import hr.abysalto.hiring.mid.domain.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    @Test
    void findByUsername_existingUser_returnsUser() {
        User user = new User(1L, "dejan", "password");

        when(userRepository.findByUsername("dejan")).thenReturn(Optional.of(user));

        Optional<User> existingUser = Optional.ofNullable(userService.findByUsername("dejan"));

        assertTrue(existingUser.isPresent());
        assertEquals("dejan", existingUser.get().getUsername());
        assertEquals("password", existingUser.get().getPassword());
    }

    @Test
    void findByUsername_nonExistingUser_returnsEmpty() {
        when(userRepository.findByUsername("unknown")).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> userService.findByUsername("unknown"));
    }
    @Test
    void saveUser_callsRepository() {
        User user = new User(null, "alice", "secret");

        // Mock the encoding
        when(passwordEncoder.encode("secret")).thenReturn("encoded-secret");

        when(userRepository.save(any(User.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        User registeredUser = userService.register(user.getUsername(), user.getPassword());

        assertEquals("encoded-secret", registeredUser.getPassword());

        verify(userRepository, times(1)).save(any(User.class));
        verify(passwordEncoder, times(1)).encode("secret");
    }
}
