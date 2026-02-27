package hr.abysalto.hiring.mid.user.app.usecase;

import hr.abysalto.hiring.mid.user.dto.LoginResponse;

public interface LoginUserUseCase {
    LoginResponse login(String username, String password);
}
