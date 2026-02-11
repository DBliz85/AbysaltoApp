package hr.abysalto.hiring.mid.common.mapper;

import hr.abysalto.hiring.mid.user.domain.User;
import hr.abysalto.hiring.mid.user.dto.UserDto;
import hr.abysalto.hiring.mid.user.infrastructure.persistance.entity.UserEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public static User toDomain(UserEntity entity) {
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

    public static User fromDto(UserDto dto) {
        if (dto == null) return null;
        return new User(dto.id(), dto.username(), null);
    }

    public static UserDetails toUserDetails(UserEntity entity) {
        return org.springframework.security.core.userdetails.User.builder()
                .username(entity.getUsername())
                .password(entity.getPassword())
                .roles("USER")
                .build();
    }
}