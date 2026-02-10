package hr.abysalto.hiring.mid.common.mapper;

import hr.abysalto.hiring.mid.cart.domain.Cart;
import hr.abysalto.hiring.mid.cart.infrastructure.persistance.entity.CartEntity;
import hr.abysalto.hiring.mid.cart.infrastructure.persistance.entity.CartItemEntity;
import hr.abysalto.hiring.mid.product.domain.Product;
import org.springframework.stereotype.Component;

@Component
public class CartEntityMapper {

    public Cart toDomain(CartEntity entity) {
        Cart cart = new Cart(entity.getUserId());

        entity.getItems().forEach(i -> {
            cart.addItem(
                    ProductMapper.toDto(new Product(i.getProductId(), i.getProductName(), i.getPrice())),
                    i.getQuantity());
        });

        return cart;
    }

    public CartEntity toEntity(Cart cart) {
        CartEntity entity = new CartEntity(cart.getUserId());

        cart.getItems().forEach(item -> {
            CartItemEntity itemEntity = new CartItemEntity(
                    item.getProductId(),
                    item.getProductName(),
                    item.getPrice(),
                    item.getQuantity()
            );
            entity.addItem(itemEntity);
        });

        return entity;
    }
}