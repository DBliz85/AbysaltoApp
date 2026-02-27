package hr.abysalto.hiring.mid.user.app.usecase;

import hr.abysalto.hiring.mid.user.app.usecase.exception.UserNotFoundException;
import hr.abysalto.hiring.mid.user.domain.User;
import hr.abysalto.hiring.mid.user.domain.UserRepository;
import hr.abysalto.hiring.mid.user.dto.UserDto;
import hr.abysalto.hiring.mid.user.mapper.UserMapper;
import org.springframework.stereotype.Service;

@Service
public class FindUserByUsernameService implements FindUserByUsernameUseCase {
    private final UserRepository userRepository;

    public FindUserByUsernameService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserDto findUser(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
        return UserMapper.toDto(user);
    }
}
