package hr.abysalto.hiring.mid;

import hr.abysalto.hiring.mid.user.app.usecase.FindUserByUsernameService;
import hr.abysalto.hiring.mid.user.app.usecase.exception.UserNotFoundException;
import hr.abysalto.hiring.mid.user.domain.User;
import hr.abysalto.hiring.mid.user.domain.UserRepository;
import hr.abysalto.hiring.mid.user.dto.UserDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FindUserByUsernameServiceTest {
    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private FindUserByUsernameService findUserByUsername;

    @Test
    void should_return_user_when_username_exists() {
        User user = new User(1L, "john", "testPassword");
        when(userRepository.findByUsername("john")).thenReturn(Optional.of(user));

        UserDto dto = findUserByUsername.findUser("john");

        assertEquals("john", dto.username());
    }

    @Test
    void should_throw_exception_when_user_not_found() {
        when(userRepository.findByUsername("john")).thenReturn(Optional.empty());

        UserNotFoundException ex = assertThrows(UserNotFoundException.class,
                () -> findUserByUsername.findUser("john"));

        assertEquals("User not found", ex.getMessage());
    }
}
