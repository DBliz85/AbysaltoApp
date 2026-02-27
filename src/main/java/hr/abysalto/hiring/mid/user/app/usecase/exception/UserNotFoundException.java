package hr.abysalto.hiring.mid.user.app.usecase.exception;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String message) {
        super(message);
    }
}
