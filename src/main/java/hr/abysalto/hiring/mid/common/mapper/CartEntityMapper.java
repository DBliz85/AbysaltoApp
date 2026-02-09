package hr.abysalto.hiring.mid.common.mapper;

import hr.abysalto.hiring.mid.cart.domain.Cart;
import hr.abysalto.hiring.mid.cart.infrastructure.persistance.entity.JpaCartEntity;
import hr.abysalto.hiring.mid.cart.infrastructure.persistance.entity.JpaCartItemEntity;
import hr.abysalto.hiring.mid.product.domain.Product;
import org.springframework.stereotype.Component;

@Component
public class CartEntityMapper {

    public Cart toDomain(JpaCartEntity entity) {
        Cart cart = new Cart(entity.getUserId());

        entity.getItems().forEach(i -> {
            cart.addItem(
                    new Product(i.getProductId(), i.getProductName(), i.getPrice()),
                            i.getQuantity());
        });

        return cart;
    }

    public JpaCartEntity toEntity(Cart cart) {
        JpaCartEntity entity = new JpaCartEntity(cart.getUserId());

        cart.getItems().forEach(item -> {
            JpaCartItemEntity itemEntity = new JpaCartItemEntity(
                    entity,
                    item.getProduct().getId(),
                    item.getProduct().getName(),
                    item.getQuantity(),
                    item.getProduct().getPrice()
            );
            entity.getItems().add(itemEntity);
        });

        return entity;
    }
}