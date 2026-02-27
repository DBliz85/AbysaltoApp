package hr.abysalto.hiring.mid.favorites.web;

import hr.abysalto.hiring.mid.favorites.mapper.FavoritesMapper;
import hr.abysalto.hiring.mid.favorites.dto.FavoritesDto;
import hr.abysalto.hiring.mid.user.app.usecase.AddProductToFavoriteUseCase;
import hr.abysalto.hiring.mid.user.app.usecase.GetFavoritesUseCase;
import hr.abysalto.hiring.mid.user.app.usecase.RemoveProductFromFavoriteUse;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("v1/api/favorites")
public class FavoriteController {

    private final AddProductToFavoriteUseCase addProductToFavorite;
    private final RemoveProductFromFavoriteUse removeProductFromFavorite;
    private final GetFavoritesUseCase getFavoritesProduct;

    public FavoriteController(AddProductToFavoriteUseCase addProductToFavorite, RemoveProductFromFavoriteUse removeFavorite, GetFavoritesUseCase getFavorites) {
        this.addProductToFavorite = addProductToFavorite;
        this.removeProductFromFavorite = removeFavorite;
        this.getFavoritesProduct = getFavorites;
    }

    @PostMapping("/{productId}")
    @ResponseStatus(HttpStatus.CREATED)
    public void addFavorite(Authentication authentication, @PathVariable Long productId) {
        addProductToFavorite.addFavorite(authentication.getName(), productId);
    }

    @DeleteMapping("/{productId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeFavorite(Authentication authentication, @PathVariable Long productId) {
        removeProductFromFavorite.removeFavorite(authentication.getName(), productId);
    }

    @GetMapping()
    public FavoritesDto getFavorites(Authentication authentication) {
        return FavoritesMapper.toFavoritesDto(getFavoritesProduct.getFavorites(authentication.getName()));
    }
}