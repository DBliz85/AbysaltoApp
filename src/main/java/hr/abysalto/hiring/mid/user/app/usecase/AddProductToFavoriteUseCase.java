package hr.abysalto.hiring.mid.user.app.usecase;

public interface AddProductToFavoriteUseCase {
    void addFavorite(String username, Long productId);
}
