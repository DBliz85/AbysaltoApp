package hr.abysalto.hiring.mid.infrastructure.persistance.mapper;

import hr.abysalto.hiring.mid.domain.model.Cart;
import hr.abysalto.hiring.mid.domain.model.User;
import hr.abysalto.hiring.mid.infrastructure.persistance.entity.CartEntity;
import org.springframework.stereotype.Component;

@Component
public class CartMapper {

    private final UserMapper userMapper;
    private final ProductMapper productMapper;

    public CartMapper(UserMapper userMapper, ProductMapper productMapper) {
        this.userMapper = userMapper;
        this.productMapper = productMapper;
    }

    public Cart toDomain(CartEntity entity) {
        User user = userMapper.toDomain(entity.getUser());
        Cart cart = new Cart(user);
        entity.getItems().forEach(item -> cart.addItem(
                productMapper.toDomain(item.getProduct()), item.getQuantity()
        ));
        return cart;
    }
}