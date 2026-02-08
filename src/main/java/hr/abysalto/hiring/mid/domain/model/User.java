package hr.abysalto.hiring.mid.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@AllArgsConstructor
public class User {
    private final Long id;
    private final String username;
    private final String password;
}
