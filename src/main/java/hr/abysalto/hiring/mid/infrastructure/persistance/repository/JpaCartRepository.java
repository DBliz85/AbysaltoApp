package hr.abysalto.hiring.mid.infrastructure.persistance.repository;

import hr.abysalto.hiring.mid.domain.model.Cart;
import hr.abysalto.hiring.mid.domain.model.User;
import hr.abysalto.hiring.mid.domain.repository.CartRepository;
import hr.abysalto.hiring.mid.infrastructure.persistance.entity.CartEntity;
import hr.abysalto.hiring.mid.infrastructure.persistance.mapper.CartMapper;
import hr.abysalto.hiring.mid.infrastructure.persistance.mapper.UserMapper;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class JpaCartRepository implements CartRepository {

    private final SpringCartJpaRepository springRepo;
    private final CartMapper mapper;
    private final UserMapper userMapper;

    public JpaCartRepository(SpringCartJpaRepository springRepo, CartMapper mapper, UserMapper userMapper) {
        this.springRepo = springRepo;
        this.mapper = mapper;
        this.userMapper = userMapper;
    }

    @Override
    public Optional<Cart> findByUser(User user) {
        return springRepo.findByUser(userMapper.toEntity(user)).map(mapper::toDomain);
    }

    @Override
    public Cart save(Cart cart) {
        return mapper.toDomain(springRepo.save(mapperToEntity(cart)));
    }

    private CartEntity mapperToEntity(Cart cart) {
        CartEntity entity = new CartEntity();
        entity.setUser(userMapper.toEntity(cart.getUser()));
        return entity;
    }
}