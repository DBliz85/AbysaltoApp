package hr.abysalto.hiring.mid.infrastructure.persistance.repository;

import hr.abysalto.hiring.mid.domain.model.User;
import hr.abysalto.hiring.mid.domain.repository.UserRepository;
import hr.abysalto.hiring.mid.infrastructure.persistance.mapper.UserMapper;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class JpaUserRepository implements UserRepository {
    private final SpringUserJpaRepository springRepo;
    private final UserMapper mapper;

    public JpaUserRepository(SpringUserJpaRepository springRepo, UserMapper mapper) {
        this.springRepo = springRepo;
        this.mapper = mapper;
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return springRepo.findByUsername(username).map(mapper::toDomain);
    }

    @Override
    public User save(User user) {
        return mapper.toDomain(springRepo.save(mapper.toEntity(user)));
    }
}
