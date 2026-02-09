package hr.abysalto.hiring.mid;

import hr.abysalto.hiring.mid.user.domain.User;
import hr.abysalto.hiring.mid.user.persistance.repository.UserRepository;
import hr.abysalto.hiring.mid.user.app.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
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
    void testFindByUsername_UserExists() {
        User user = new User(1L, "dejan", "passwordHash");
        when(userRepository.findByUsername("dejan")).thenReturn(Optional.of(user));

        User existingUser = userService.findByUsername("dejan");

        assertNotNull(existingUser);
        assertEquals("dejan", existingUser.getUsername());
        verify(userRepository, times(1)).findByUsername("dejan");
    }


    @Test
    void testFindByUsername_UserDoesNotExist() {
        when(userRepository.findByUsername("unknown")).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> userService.findByUsername("unknown"));

        assertEquals("User not found", exception.getMessage());
        verify(userRepository, times(1)).findByUsername("unknown");
    }

    @Test
    void testCreateUser_Success() {

        User savedUser = new User(2L, "alice", "passwordHash");

        when(userRepository.save(any(User.class))).thenReturn(savedUser);
        when(passwordEncoder.encode(anyString())).thenAnswer(i -> "hashed-" + i.getArgument(0));

        User result = userService.register("alice", "passwordHash");

        assertNotNull(result);
        assertEquals(2L, result.getId());
        assertEquals("alice", result.getUsername());

        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
        verify(userRepository, times(1)).save(userCaptor.capture());

        User captured = userCaptor.getValue();
        assertEquals("alice", captured.getUsername());
        assertEquals("hashed-passwordHash", captured.getPassword());
    }

    @Test
    void testCreateUser_NullUsername_Throws() {
        assertThrows(IllegalArgumentException.class,
                () -> userService.register(null, "pass"));
    }

    @Test
    void testCreateUser_BlankUsername_Throws() {
        assertThrows(IllegalArgumentException.class,
                () -> userService.register(" ", "pass"));
    }
}
