package hr.abysalto.hiring.mid.favorites.web;

import hr.abysalto.hiring.mid.common.mapper.FavoritesMapper;
import hr.abysalto.hiring.mid.favorites.dto.FavoritesDto;
import hr.abysalto.hiring.mid.user.app.usecase.FavoriteService;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("v1/api/favorites")
public class FavoriteController {

    private final FavoriteService favoriteService;

    public FavoriteController(FavoriteService favoriteService) {
        this.favoriteService = favoriteService;
    }

    @PostMapping("/{productId}")
    @ResponseStatus(HttpStatus.CREATED)
    public void addFavorite(Authentication authentication, @PathVariable Long productId) {
        favoriteService.addFavorite(authentication.getName(), productId);
    }

    @DeleteMapping("/{productId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeFavorite(Authentication authentication, @PathVariable Long productId) {
        favoriteService.removeFavorite(authentication.getName(), productId);
    }

    @GetMapping("/getAll")
    public FavoritesDto getFavorites(Authentication authentication) {
        return FavoritesMapper.toFavoritesDto(favoriteService.getFavorites(authentication.getName()));
    }
}