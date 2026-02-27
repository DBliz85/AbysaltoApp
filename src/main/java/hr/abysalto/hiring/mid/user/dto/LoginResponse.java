package hr.abysalto.hiring.mid.user.dto;

public record LoginResponse(
        String username,
        String token
) {}