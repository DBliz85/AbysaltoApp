package hr.abysalto.hiring.mid.cart.infrastructure.persistance;

import hr.abysalto.hiring.mid.cart.domain.CartItem;
import hr.abysalto.hiring.mid.cart.domain.CartItemRepository;
import hr.abysalto.hiring.mid.cart.infrastructure.entity.CartEntity;
import hr.abysalto.hiring.mid.cart.infrastructure.entity.CartItemEntity;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
@Transactional
public class JpaCartItemRepository implements CartItemRepository {
    private final SpringCartItemJpaRepository springCartItemJpaRepository;

    public JpaCartItemRepository(SpringCartItemJpaRepository springCartItemJpaRepository) {
        this.springCartItemJpaRepository = springCartItemJpaRepository;
    }

    private CartItem mapToDomain(CartItemEntity entity) {
        return new CartItem(
                entity.getCart().getId(),
                entity.getProductId(),
                entity.getProductName(),
                entity.getPrice(),
                entity.getQuantity()
        );
    }

    private CartItemEntity mapToEntity(CartItem domain, CartEntity cartEntity) {
        CartItemEntity entity = new CartItemEntity(
                domain.getProductId(),
                domain.getProductName(),
                domain.getPrice(),
                domain.getQuantity()
        );
        entity.setCart(cartEntity);
        return entity;
    }

    @Override
    public Optional<CartItem> findById(Long id) {
        return springCartItemJpaRepository.findById(id)
                .map(this::mapToDomain);
    }

    @Override
    public List<CartItem> findByCartId(Long cartId) {
        return springCartItemJpaRepository.findByCartId(cartId)
                .stream()
                .map(this::mapToDomain)
                .collect(Collectors.toList());
    }

    @Override
    public CartItem save(CartItem cartItem, CartEntity cartEntity) {
        CartItemEntity entity = mapToEntity(cartItem, cartEntity);
        CartItemEntity saved = springCartItemJpaRepository.save(entity);

        return new CartItem(
                saved.getId(),
                saved.getProductId(),
                saved.getProductName(),
                saved.getPrice(),
                saved.getQuantity()
        );
    }

    @Override
    public void delete(CartItem cartItem) {
        springCartItemJpaRepository.deleteById(cartItem.getId());
    }
}
