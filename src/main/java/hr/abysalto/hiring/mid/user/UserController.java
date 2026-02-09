package hr.abysalto.hiring.mid.user;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;

@RestController
@RequestMapping("/api/auth")
public class UserController {

    private final UserService userService;
    public UserController(UserService userService) { this.userService = userService; }

    @GetMapping("/userDetails")
    public User userDetails(Authentication auth) {
        return userService.findByUsername(auth.getName());
    }

    @PostMapping("/register")
    public void register(@RequestBody @Valid RegisterRequest req) {
        userService.register(req.username(), req.password());
    }
}
