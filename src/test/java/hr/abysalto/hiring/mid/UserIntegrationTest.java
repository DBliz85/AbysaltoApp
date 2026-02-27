package hr.abysalto.hiring.mid;

import hr.abysalto.hiring.mid.user.domain.User;
import hr.abysalto.hiring.mid.user.domain.UserRepository;
import hr.abysalto.hiring.mid.user.infrastructure.persistence.entity.UserEntity;
import hr.abysalto.hiring.mid.user.mapper.UserMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
@ActiveProfiles("test")
class UserIntegrationTest {

    @Container
    @ServiceConnection
    static MySQLContainer<?> mysql = new MySQLContainer<>("mysql:8.0");

    @Autowired
    private UserRepository userRepository;

    @BeforeAll
    static void setup() { System.setProperty("api.version", "1.44"); }

    @Test
    void shouldSaveAndRetrieveUser() {
        UserEntity user = new UserEntity();
        user.setUsername("test");
        user.setPassword("password123");
        userRepository.save(UserMapper.toDomain(user));

        Optional<User> found = userRepository.findByUsername("test");

        assertThat(found).isPresent();
        assertThat(found.get().getUsername()).isEqualTo("test");
    }
}