package by.tutin.model.dto.auth;

import lombok.Data;

@Data
public class UserAuthenticationRequestDto {
    private String username;
    private String password;
}
