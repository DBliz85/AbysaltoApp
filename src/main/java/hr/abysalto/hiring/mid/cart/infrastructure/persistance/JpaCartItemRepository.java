package hr.abysalto.hiring.mid.cart.infrastructure.persistance;

import hr.abysalto.hiring.mid.cart.domain.CartItem;
import hr.abysalto.hiring.mid.cart.domain.CartItemRepository;
import hr.abysalto.hiring.mid.cart.infrastructure.entity.CartEntity;
import hr.abysalto.hiring.mid.cart.infrastructure.entity.CartItemEntity;
import hr.abysalto.hiring.mid.common.mapper.CartEntityMapper;
import org.springframework.stereotype.Repository;

@Repository
public class JpaCartItemRepository implements CartItemRepository {

    private final SpringCartItemJpaRepository springDataRepository;
    private final CartEntityMapper mapper;

    public JpaCartItemRepository(SpringCartItemJpaRepository springDataRepository,
                                 CartEntityMapper mapper) {
        this.springDataRepository = springDataRepository;
        this.mapper = mapper;
    }

    @Override
    public CartItem save(CartItem cartItem, Long cartId) {

        CartItemEntity entity = new CartItemEntity(cartItem.getProductId(), cartItem.getQuantity());

        CartEntity cartEntity = new CartEntity(cartId);
        entity.setCart(cartEntity);

        CartItemEntity saved = springDataRepository.save(entity);

        return mapper.toDomainItem(saved);
    }
}
