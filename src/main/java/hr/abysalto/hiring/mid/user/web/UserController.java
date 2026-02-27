package hr.abysalto.hiring.mid.user.web;

import hr.abysalto.hiring.mid.user.app.usecase.FindUserByUsernameService;
import hr.abysalto.hiring.mid.user.app.usecase.LoginUserService;
import hr.abysalto.hiring.mid.user.app.usecase.RegisterUserUseCase;
import hr.abysalto.hiring.mid.user.dto.LoginResponse;
import hr.abysalto.hiring.mid.user.dto.RegisterRequest;
import hr.abysalto.hiring.mid.user.dto.UserDto;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("v1/api/auth")
public class UserController {

    private final RegisterUserUseCase registerUser;
    private final LoginUserService loginUser;
    private final FindUserByUsernameService findUser;

    public UserController(RegisterUserUseCase registerUser, LoginUserService loginUser, FindUserByUsernameService findUser) {
        this.registerUser = registerUser;
        this.loginUser = loginUser;
        this.findUser = findUser;
    }

    @GetMapping("/me")
    public UserDto getCurrentUser(Authentication authentication) {
        return findUser.findUser(authentication.getName());
    }

    @PostMapping("/login")
    public LoginResponse login(@RequestBody @Valid RegisterRequest request) {
        return loginUser.login(request.username(), request.password());
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public void register(@Valid @RequestBody RegisterRequest request) {
        registerUser.register(request.username(), request.password());
    }
}
