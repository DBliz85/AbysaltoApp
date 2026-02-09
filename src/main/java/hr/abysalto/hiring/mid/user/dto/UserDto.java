package hr.abysalto.hiring.mid.user.dto;

public record UserDto(Long id, String username) {
    public static UserDto from(String id, String username) {
        return new UserDto(Long.valueOf(id), username);
    }
}