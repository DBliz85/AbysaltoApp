package hr.abysalto.hiring.mid;

import hr.abysalto.hiring.mid.user.User;
import hr.abysalto.hiring.mid.user.UserEntity;
import hr.abysalto.hiring.mid.common.mapper.UserMapper;
import hr.abysalto.hiring.mid.infrastructure.persistance.repository.JpaUserRepository;
import hr.abysalto.hiring.mid.infrastructure.persistance.repository.SpringUserJpaRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@Transactional
class JpaUserRepositoryTest {

    @Autowired
    private SpringUserJpaRepository springUserJpaRepository;

    private JpaUserRepository jpaUserRepository;

    @BeforeEach
    void setup() {
        jpaUserRepository = new JpaUserRepository(springUserJpaRepository);
    }

    @Test
    void findByUsername_existingUser_returnsUser() {
        UserEntity entity = new UserEntity();
        entity.setUsername("dejan");
        entity.setPassword("{noop}password");
        springUserJpaRepository.save(entity);

        Optional<User> user = jpaUserRepository.findByUsername("dejan");

        assertTrue(user.isPresent());
        assertEquals("dejan", user.get().getUsername());
    }

    @Test
    void findByUsername_nonExistingUser_returnsEmpty() {
        Optional<User> user = jpaUserRepository.findByUsername("unknown");
        assertTrue(user.isEmpty());
    }
}