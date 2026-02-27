package hr.abysalto.hiring.mid.cart.web;

import hr.abysalto.hiring.mid.cart.app.usecase.AddItemToCartUseCase;
import hr.abysalto.hiring.mid.cart.app.usecase.GetCartUseCase;
import hr.abysalto.hiring.mid.cart.app.usecase.RemoveItemFromCartUseCase;
import hr.abysalto.hiring.mid.cart.dto.AddToCartRequest;
import hr.abysalto.hiring.mid.cart.dto.CartDto;
import hr.abysalto.hiring.mid.cart.mapper.CartMapper;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("v1/api/cart")
public class CartController {

    private final AddItemToCartUseCase addItemToCart;
    private final GetCartUseCase getCart;
    private final RemoveItemFromCartUseCase removeItemFromCart;

    public CartController(AddItemToCartUseCase addItemToCart, GetCartUseCase getCart, RemoveItemFromCartUseCase removeItemFromCart) {
        this.addItemToCart = addItemToCart;
        this.getCart = getCart;
        this.removeItemFromCart = removeItemFromCart;
    }

    @GetMapping
    public CartDto getCart(Authentication authentication) {
        return CartMapper.toResponse(getCart.getCart(authentication.getName()));
    }

    @PostMapping("/items")
    @ResponseStatus(HttpStatus.CREATED)
    public CartDto addItem(
            Authentication authentication,
            @Valid @RequestBody AddToCartRequest request) {
        return CartMapper.toResponse(
                addItemToCart.addItem(authentication.getName(), request.productId(), request.quantity())
        );
    }

    @DeleteMapping("/items/{productId}")
    public CartDto removeItem(
            Authentication authentication,
            @PathVariable @Positive Long productId) {
        return CartMapper.toResponse(
                removeItemFromCart.removeItem(authentication.getName(), productId)
        );
    }
}

