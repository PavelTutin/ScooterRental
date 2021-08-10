package by.tutin.model.dto;

import lombok.Data;


@Data
public class UserAdminInfoDto {
    private String username;
    private String firstName;
    private String lastName;
    private String status;
    private int subscription;
    private int discount;
    private String role;
}
