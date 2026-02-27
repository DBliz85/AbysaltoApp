package hr.abysalto.hiring.mid.cart.mapper;

import hr.abysalto.hiring.mid.cart.domain.Cart;
import hr.abysalto.hiring.mid.cart.domain.CartItem;
import hr.abysalto.hiring.mid.cart.infrastructure.entity.CartEntity;
import hr.abysalto.hiring.mid.cart.infrastructure.entity.CartItemEntity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class CartEntityMapper {

    private CartEntityMapper() {
    }

    public static Cart toDomain(CartEntity entity) {
        if (entity == null) return null;

        List<CartItem> items = Optional.ofNullable(entity.getItems())
                .orElse(Collections.emptyList())
                .stream()
                .map(CartEntityMapper::toDomainItem)
                .collect(Collectors.toCollection(ArrayList::new));

        return new Cart(
                entity.getId(),
                entity.getUserId(),
                items
        );
    }

    public static CartEntity toEntity(Cart cart) {
        if (cart == null) return null;

        CartEntity entity = new CartEntity(cart.getId(), cart.getUserId());

        List<CartItemEntity> itemEntities = Optional.ofNullable(cart.getItems())
                .orElse(Collections.emptyList())
                .stream()
                .map(item -> toEntityItem(item, entity))
                .toList();

        entity.getItems().clear();
        entity.getItems().addAll(itemEntities);

        return entity;
    }

    public static CartItem toDomainItem(CartItemEntity entity) {
        if (entity == null) return null;

        return new CartItem(
                entity.getProductId(),
                entity.getQuantity()
        );
    }

    public static CartItemEntity toEntityItem(CartItem item, CartEntity cartEntity) {
        if (item == null) return null;

        CartItemEntity entity = new CartItemEntity(item.getProductId(), item.getQuantity());
        entity.setCart(cartEntity);
        return entity;
    }
}