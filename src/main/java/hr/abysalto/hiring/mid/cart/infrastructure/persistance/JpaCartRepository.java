package hr.abysalto.hiring.mid.cart.infrastructure.persistance;

import hr.abysalto.hiring.mid.cart.domain.Cart;
import hr.abysalto.hiring.mid.cart.domain.CartRepository;
import hr.abysalto.hiring.mid.cart.infrastructure.entity.CartEntity;
import hr.abysalto.hiring.mid.common.mapper.CartEntityMapper;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class JpaCartRepository implements CartRepository {

    private final SpringCartJpaRepository springCartJpaRepository;
    private final CartEntityMapper mapper;

    public JpaCartRepository(SpringCartJpaRepository springCartJpaRepository,
                             CartEntityMapper mapper) {
        this.springCartJpaRepository = springCartJpaRepository;
        this.mapper = mapper;
    }

    @Override
    public Optional<Cart> findByUserId(Long userId) {
        return springCartJpaRepository.findByUserId(userId)
                .map(mapper::toDomain);
    }

    @Override
    public Cart save(Cart cart) {

        CartEntity entity = mapper.toEntity(cart);

        CartEntity saved = springCartJpaRepository.save(entity);

        return mapper.toDomain(saved);
    }
}