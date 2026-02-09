package hr.abysalto.hiring.mid.user;

import jakarta.validation.constraints.NotBlank;

public record RegisterRequest(@NotBlank(message = "Username cannot be blank")String username, @NotBlank(message = "Password cannot be blank") String password) { }

