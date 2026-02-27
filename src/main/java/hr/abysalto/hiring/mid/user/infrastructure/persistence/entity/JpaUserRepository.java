package hr.abysalto.hiring.mid.user.infrastructure.persistence.entity;

import hr.abysalto.hiring.mid.user.domain.User;
import hr.abysalto.hiring.mid.user.domain.UserRepository;
import hr.abysalto.hiring.mid.user.mapper.UserMapper;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class JpaUserRepository implements UserRepository {

    private final SpringUserJpaRepository springRepo;

    public JpaUserRepository(SpringUserJpaRepository springRepo) {
        this.springRepo = springRepo;
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return springRepo.findByUsername(username).map(UserMapper::toDomain);
    }

    @Override
    public User save(User user) {
        UserEntity saved = springRepo.save(UserMapper.toEntity(user));
        return UserMapper.toDomain(saved);
    }
}
