package hr.abysalto.hiring.mid.presentation.controller;

import hr.abysalto.hiring.mid.app.dto.RegisterRequest;
import hr.abysalto.hiring.mid.app.service.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserService userService;
    public AuthController(UserService userService) { this.userService = userService; }

    @PostMapping("/register")
    public void register(@RequestBody RegisterRequest req) {
        userService.register(req.username(), req.password());
    }
}
