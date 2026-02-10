package hr.abysalto.hiring.mid.product.app.usecase.exception.dto;

import java.time.LocalDateTime;

public record ApiErrorResponse(
        int status,
        String error,
        String message,
        LocalDateTime timestamp
) {}
