package hr.abysalto.hiring.mid.presentation.controller;

import hr.abysalto.hiring.mid.app.service.UserService;
import hr.abysalto.hiring.mid.domain.model.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.core.Authentication;

@RestController
@RequestMapping("/api/auth")
public class UserController {

    private final UserService userService;
    public UserController(UserService userService) { this.userService = userService; }

    @GetMapping("/me")
    public User me(Authentication auth) {
        return userService.findByUsername(auth.getName());
    }
}
