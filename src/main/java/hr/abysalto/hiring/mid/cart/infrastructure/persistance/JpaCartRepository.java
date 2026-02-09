package hr.abysalto.hiring.mid.cart.infrastructure.persistance;

import hr.abysalto.hiring.mid.cart.domain.Cart;
import hr.abysalto.hiring.mid.cart.domain.CartRepository;
import hr.abysalto.hiring.mid.cart.infrastructure.persistance.entity.JpaCartEntity;
import hr.abysalto.hiring.mid.common.mapper.CartEntityMapper;
import hr.abysalto.hiring.mid.user.domain.User;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@Transactional
public class JpaCartRepository implements CartRepository {

    private final SpringCartJpaRepository springRepo;
    private final CartEntityMapper mapper;

    public JpaCartRepository(SpringCartJpaRepository springRepo, CartEntityMapper mapper) {
        this.springRepo = springRepo;
        this.mapper = mapper;
    }

    @Override
    public Optional<Cart> findByUser(User user) {
        return springRepo.findByUserId(user.getId())
                .map(mapper::toDomain);
    }

    @Override
    public Cart save(Cart cart) {
        JpaCartEntity entity = mapper.toEntity(cart);
        JpaCartEntity saved = springRepo.save(entity);
        return mapper.toDomain(saved);
    }
}