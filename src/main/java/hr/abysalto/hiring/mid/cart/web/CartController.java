package hr.abysalto.hiring.mid.cart.web;

import hr.abysalto.hiring.mid.cart.dto.AddToCartRequest;
import hr.abysalto.hiring.mid.cart.dto.CartDto;
import hr.abysalto.hiring.mid.cart.app.usecase.CartService;
import hr.abysalto.hiring.mid.common.mapper.CartMapper;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    private final CartService cartService;
    private final CartMapper cartMapper;

    public CartController(CartService cartService, CartMapper cartMapper) {
        this.cartService = cartService;
        this.cartMapper = cartMapper;
    }

    @GetMapping
    public CartDto getCart(Authentication authentication) {
        return cartMapper.toResponse(cartService.getCart(authentication));
    }

    @PostMapping("/items")
    @ResponseStatus(HttpStatus.CREATED)
    public CartDto addItem(
            Authentication authentication,
            @Valid @RequestBody AddToCartRequest request) {
        return cartMapper.toResponse(
                cartService.addItem(authentication, request.productId(), request.quantity())
        );
    }

    @DeleteMapping("/items/{productId}")
    public CartDto removeItem(
            Authentication authentication,
            @PathVariable Long productId) {
        return cartMapper.toResponse(
                cartService.removeItem(authentication, productId)
        );
    }
}
