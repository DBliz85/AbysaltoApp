package hr.abysalto.hiring.mid.cart.infrastructure.persistence;

import hr.abysalto.hiring.mid.cart.domain.CartItem;
import hr.abysalto.hiring.mid.cart.domain.CartItemRepository;
import hr.abysalto.hiring.mid.cart.infrastructure.entity.CartEntity;
import hr.abysalto.hiring.mid.cart.infrastructure.entity.CartItemEntity;
import hr.abysalto.hiring.mid.cart.mapper.CartEntityMapper;
import org.springframework.stereotype.Repository;

@Repository
public class JpaCartItemRepository implements CartItemRepository {

    private final SpringCartItemJpaRepository springDataRepository;

    public JpaCartItemRepository(SpringCartItemJpaRepository springDataRepository) {
        this.springDataRepository = springDataRepository;
    }

    @Override
    public CartItem save(CartItem cartItem, Long cartId) {

        CartItemEntity entity = new CartItemEntity(cartItem.getProductId(), cartItem.getQuantity());

        CartEntity cartEntity = new CartEntity(cartId);
        entity.setCart(cartEntity);

        CartItemEntity saved = springDataRepository.save(entity);

        return CartEntityMapper.toDomainItem(saved);
    }
}
