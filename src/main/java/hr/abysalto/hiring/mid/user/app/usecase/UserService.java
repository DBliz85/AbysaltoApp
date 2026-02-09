package hr.abysalto.hiring.mid.user.app.usecase;

import hr.abysalto.hiring.mid.common.mapper.UserMapper;
import hr.abysalto.hiring.mid.user.domain.User;
import hr.abysalto.hiring.mid.user.domain.UserRepository;
import hr.abysalto.hiring.mid.user.dto.UserDto;
import hr.abysalto.hiring.mid.user.infrastructure.persistance.entity.UserEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void register(String username, String password) {
        String hashed = passwordEncoder.encode(password);
        User user = new User(null, username, hashed);
        userRepository.save(user);
    }

    public UserDto findByUsername(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return UserMapper.toDto(user);
    }
}
