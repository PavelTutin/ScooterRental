package by.tutin.model.dto.auth;

import lombok.Data;

@Data
public class UserAuthenticationResponseDto {
    private String username;
    private String token;
}
