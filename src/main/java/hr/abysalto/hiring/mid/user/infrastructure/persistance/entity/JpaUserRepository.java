package hr.abysalto.hiring.mid.user.infrastructure.persistance.entity;

import hr.abysalto.hiring.mid.user.domain.User;
import hr.abysalto.hiring.mid.common.mapper.UserMapper;
import hr.abysalto.hiring.mid.user.domain.UserRepository;
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
        UserEntity entityToSave;

        if (user.getId() == null) {
            entityToSave = UserMapper.toEntity(user);
        } else {
            entityToSave = springRepo.findById(user.getId())
                    .map(existing -> {
                        existing.setUsername(user.getUsername());
                        existing.setPassword(user.getPassword());
                        existing.setFavoriteProductIds(user.getFavoriteProductIds());
                        return existing;
                    })
                    // If the ID was set but doesn't exist in DB, fall back to mapping
                    .orElseGet(() -> UserMapper.toEntity(user));
        }

        return UserMapper.toDomain(springRepo.save(entityToSave));
    }
}
