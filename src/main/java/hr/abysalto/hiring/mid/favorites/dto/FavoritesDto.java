package hr.abysalto.hiring.mid.favorites.dto;

import hr.abysalto.hiring.mid.product.dto.ProductDto;

import java.util.List;

public record FavoritesDto(List<ProductDto> favorites) {}

