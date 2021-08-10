package by.tutin.model.dto;

import lombok.Data;

@Data
public class UserDto {
    private String username;
    private String firstName;
    private String lastName;
    private Integer subscription;
    private Integer discount;
}
