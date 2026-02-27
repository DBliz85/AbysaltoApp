package hr.abysalto.hiring.mid.user.app.usecase;

import hr.abysalto.hiring.mid.user.dto.UserDto;

public interface FindUserByUsernameUseCase {
    UserDto findUser(String username);
}
