package by.tutin.model.dto.auth;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class RegistrationRequestDto {
    private String username;
    private String password;
    private String firstName;
    private String lastName;
}
