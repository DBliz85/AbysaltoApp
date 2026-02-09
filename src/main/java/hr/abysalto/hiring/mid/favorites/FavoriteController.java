package hr.abysalto.hiring.mid.favorites;

import hr.abysalto.hiring.mid.common.mapper.ProductMapper;
import hr.abysalto.hiring.mid.product.domain.ProductResponse;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/favorites")
public class FavoriteController {

    private final FavoriteService favoriteService;

    public FavoriteController(FavoriteService favoriteService) {
        this.favoriteService = favoriteService;
    }

    @PostMapping("/{productId}")
    public void addFavorite(Authentication authentication, @PathVariable Long productId) {
        favoriteService.addFavorite(authentication.getName(), productId);
    }

    @DeleteMapping("/{productId}")
    public void removeFavorite(Authentication authentication, @PathVariable Long productId) {
        favoriteService.removeFavorite(authentication.getName(), productId);
    }

    @GetMapping
    public List<ProductResponse> getFavorites(Authentication authentication) {
        return favoriteService.getFavorites(authentication.getName())
                .stream()
                .map(ProductMapper::toResponse)
                .collect(Collectors.toList());
    }
}