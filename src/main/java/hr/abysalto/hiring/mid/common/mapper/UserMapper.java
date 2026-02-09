package hr.abysalto.hiring.mid.common.mapper;

import hr.abysalto.hiring.mid.user.User;
import hr.abysalto.hiring.mid.user.UserEntity;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public static User toDomain(UserEntity entity) {
        return new User(entity.getId(), entity.getUsername(), entity.getPassword());
    }

    public static UserEntity toEntity(User domain) {
        if (domain == null) return null;

        UserEntity entity = new UserEntity();
        entity.setId(domain.getId());
        entity.setUsername(domain.getUsername());
        entity.setPassword(domain.getPassword());

        return entity;
    }
}