package hr.abysalto.hiring.mid.user.app.usecase;

import hr.abysalto.hiring.mid.shared.readmodel.ProductView;

import java.util.List;

public interface GetFavoritesUseCase {
    List<ProductView> getFavorites(String username);
}
