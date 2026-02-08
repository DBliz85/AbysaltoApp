package hr.abysalto.hiring.mid.presentation.controller;

import hr.abysalto.hiring.mid.app.dto.AddToCartRequest;
import hr.abysalto.hiring.mid.app.service.CartService;
import hr.abysalto.hiring.mid.app.service.UserService;
import hr.abysalto.hiring.mid.domain.model.Cart;
import hr.abysalto.hiring.mid.domain.model.User;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    private final CartService cartService;
    private final UserService userService;
    public CartController(CartService cartService, UserService userService) {
        this.cartService = cartService;
        this.userService = userService;
    }

    @PostMapping("/items")
    public Cart addToCart(Authentication auth, @RequestBody AddToCartRequest req) {
        User user = userService.findByUsername(auth.getName());
        return cartService.addItem(user, req.productId(), req.quantity());
    }

    @DeleteMapping("/items/{productId}")
    public Cart removeFromCart(Authentication auth, @PathVariable Long productId) {
        User user = userService.findByUsername(auth.getName());
        return cartService.removeItem(user, productId);
    }

    @GetMapping
    public Cart getCart(Authentication auth) {
        User user = userService.findByUsername(auth.getName());
        return cartService.getCart(user);
    }
}
