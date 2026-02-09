package hr.abysalto.hiring.mid.user.dto;

import hr.abysalto.hiring.mid.user.domain.User;
import hr.abysalto.hiring.mid.user.infrastructure.persistance.entity.UserEntity;

public record UserDto(Long id, String username) {
    public static UserDto from(UserEntity entity) {
        if (entity == null) return null;
        return new UserDto(entity.getId(), entity.getUsername());
    }

    public static UserDto from(User domain) {
        if (domain == null) return null;
        return new UserDto(domain.getId(), domain.getUsername());
    }
}