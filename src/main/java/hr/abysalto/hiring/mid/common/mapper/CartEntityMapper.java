package hr.abysalto.hiring.mid.common.mapper;

import hr.abysalto.hiring.mid.cart.domain.Cart;
import hr.abysalto.hiring.mid.cart.domain.CartItem;
import hr.abysalto.hiring.mid.cart.infrastructure.entity.CartEntity;
import hr.abysalto.hiring.mid.cart.infrastructure.entity.CartItemEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CartEntityMapper {

    public Cart toDomain(CartEntity entity) {

        List<CartItem> items = entity.getItems().stream()
                .map(this::toDomainItem)
                .toList();

        return new Cart(
                entity.getId(),
                entity.getUserId(),
                items
        );
    }

    public CartEntity toEntity(Cart cart) {

        CartEntity entity = new CartEntity(cart.getId(), cart.getUserId());

        List<CartItemEntity> itemEntities = cart.getItems().stream()
                .map(item -> toEntityItem(item, entity))
                .toList();

        entity.getItems().clear();
        entity.getItems().addAll(itemEntities);

        return entity;
    }

    public CartItem toDomainItem(CartItemEntity entity) {
        return new CartItem(
                entity.getProductId(),
                entity.getQuantity()
        );
    }

    public CartItemEntity toEntityItem(CartItem item, CartEntity cartEntity) {

        CartItemEntity entity = new CartItemEntity(item.getProductId(),item.getQuantity());
        entity.setCart(cartEntity);

        return entity;
    }
}