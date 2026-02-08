package hr.abysalto.hiring.mid.infrastructure.persistance.mapper;

import hr.abysalto.hiring.mid.domain.model.User;
import hr.abysalto.hiring.mid.infrastructure.persistance.entity.UserEntity;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public User toDomain(UserEntity entity) {
        return new User(entity.getId(), entity.getUsername(), entity.getPassword());
    }

    public UserEntity toEntity(User user) {
        UserEntity entity = new UserEntity();
        entity.setId(user.getId());
        entity.setUsername(user.getUsername());
        entity.setPassword(user.getPassword());
        return entity;
    }
}