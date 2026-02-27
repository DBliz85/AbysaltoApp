package hr.abysalto.hiring.mid.cart.infrastructure.persistence;

import hr.abysalto.hiring.mid.cart.domain.Cart;
import hr.abysalto.hiring.mid.cart.domain.CartRepository;
import hr.abysalto.hiring.mid.cart.infrastructure.entity.CartEntity;
import hr.abysalto.hiring.mid.cart.mapper.CartEntityMapper;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class JpaCartRepository implements CartRepository {

    private final SpringCartJpaRepository springCartJpaRepository;

    public JpaCartRepository(SpringCartJpaRepository springCartJpaRepository) {
        this.springCartJpaRepository = springCartJpaRepository;
    }

    @Override
    public Optional<Cart> findByUserId(Long userId) {
        return springCartJpaRepository.findByUserId(userId)
                .map(CartEntityMapper::toDomain);
    }

    @Override
    public Cart save(Cart cart) {

        CartEntity entity = CartEntityMapper.toEntity(cart);

        CartEntity saved = springCartJpaRepository.save(entity);

        return CartEntityMapper.toDomain(saved);
    }
}