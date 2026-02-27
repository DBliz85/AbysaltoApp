package hr.abysalto.hiring.mid.user.app.usecase.exception;

public class InvalidCredentialsException extends RuntimeException {
    public InvalidCredentialsException(String message) {
        super(message);
    }
}
