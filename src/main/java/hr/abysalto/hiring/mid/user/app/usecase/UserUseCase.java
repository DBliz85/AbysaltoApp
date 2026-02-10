package hr.abysalto.hiring.mid.user.app.usecase;

import hr.abysalto.hiring.mid.user.dto.LoginResponse;
import hr.abysalto.hiring.mid.user.dto.RegisterRequest;

public interface UserUseCase {
    LoginResponse login(RegisterRequest request);
}