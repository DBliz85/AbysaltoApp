package hr.abysalto.hiring.mid.user.mapper;

import hr.abysalto.hiring.mid.user.domain.User;
import hr.abysalto.hiring.mid.user.dto.UserDto;
import hr.abysalto.hiring.mid.user.infrastructure.persistence.entity.UserEntity;

public class UserMapper {

    public static User toDomain(UserEntity entity) {
        if (entity == null) return null;

        var user = new User(entity.getId(), entity.getUsername(), entity.getPassword());
        user.overwriteFavorites(entity.getFavoriteProductIds());
        return user;
    }

    public static UserEntity toEntity(User domain) {
        if (domain == null) return null;

        UserEntity entity = new UserEntity();
        entity.setId(domain.getId());
        entity.setUsername(domain.getUsername());
        entity.setPassword(domain.getPassword());
        entity.setFavoriteProductIds(domain.getFavoriteProductIds());

        return entity;
    }

    public static UserDto toDto(User domain) {
        if (domain == null) return null;
        return new UserDto(domain.getId(), domain.getUsername());
    }
}