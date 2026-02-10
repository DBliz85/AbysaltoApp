package hr.abysalto.hiring.mid.cart.infrastructure.persistance;

import hr.abysalto.hiring.mid.cart.domain.Cart;
import hr.abysalto.hiring.mid.cart.domain.CartItem;
import hr.abysalto.hiring.mid.cart.domain.CartRepository;
import hr.abysalto.hiring.mid.cart.infrastructure.entity.CartEntity;
import hr.abysalto.hiring.mid.cart.infrastructure.entity.CartItemEntity;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@Transactional
public class JpaCartRepository implements CartRepository {

    private final SpringCartJpaRepository jpaCartRepository;

    public JpaCartRepository(SpringCartJpaRepository springCartJpaRepository) {
        this.jpaCartRepository = springCartJpaRepository;
    }

    @Override
    public Optional<Cart> findByUserId(Long userId) {
        return jpaCartRepository.findByUserId(userId)
                .map(this::mapToDomain);
    }

    @Override
    public Cart save(Cart cart) {
        CartEntity entity = mapToEntity(cart);
        return mapToDomain(jpaCartRepository.save(entity));
    }

    private Cart mapToDomain(CartEntity entity) {
        var items = entity.getItems().stream()
                .map(i -> CartItem.fromProduct(
                        i.getProductId(),
                        i.getProductName(),
                        i.getPrice(),
                        i.getQuantity()
                ))
                .toList();

        return new Cart(entity.getId(), entity.getUserId(), items);
    }

    @Transactional
    public CartEntity mapToEntity(Cart cart) {
        CartEntity entity;

        if (cart.getId() == null) {
            entity = new CartEntity(cart.getUserId());
        } else {
            entity = jpaCartRepository.findById(cart.getId())
                    .orElseThrow(() -> new IllegalStateException("Cart not found"));
            entity.getItems().clear();
        }

        for (CartItem item : cart.getItems()) {
            CartItemEntity itemEntity = new CartItemEntity(
                    item.getProductId(),
                    item.getProductName(),
                    item.getPrice(),
                    item.getQuantity()
            );
            entity.addItem(itemEntity);
        }

        return entity;
    }
}