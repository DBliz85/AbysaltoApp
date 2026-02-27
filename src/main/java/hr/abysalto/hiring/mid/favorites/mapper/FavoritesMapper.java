package hr.abysalto.hiring.mid.favorites.mapper;

import hr.abysalto.hiring.mid.favorites.dto.FavoritesDto;
import hr.abysalto.hiring.mid.product.dto.ProductDto;
import hr.abysalto.hiring.mid.shared.readmodel.ProductView;

import java.util.List;
import java.util.stream.Collectors;

public class FavoritesMapper {

    public static ProductDto toProductDto(ProductView view) {
        return new ProductDto(view.id(), view.title(), view.price());
    }

    public static FavoritesDto toFavoritesDto(List<ProductView> views) {
        List<ProductDto> dtos = views.stream()
                .map(FavoritesMapper::toProductDto)
                .collect(Collectors.toList());
        return new FavoritesDto(dtos);
    }
}
