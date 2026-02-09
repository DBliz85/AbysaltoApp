package hr.abysalto.hiring.mid.user.web;

import hr.abysalto.hiring.mid.common.mapper.UserMapper;
import hr.abysalto.hiring.mid.user.app.usecase.UserService;
import hr.abysalto.hiring.mid.user.domain.User;
import hr.abysalto.hiring.mid.user.dto.RegisterRequest;
import hr.abysalto.hiring.mid.user.dto.UserDto;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;

@RestController
@RequestMapping("/api/auth")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/me")
    public UserDto me(Authentication auth) {
        return userService.findByUsername(auth.getName());
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public void register(@Valid @RequestBody RegisterRequest req) {
        userService.register(req.username(), req.password());
    }
}
