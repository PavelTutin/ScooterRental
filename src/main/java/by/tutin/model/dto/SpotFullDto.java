package by.tutin.model.dto;

import lombok.Data;

@Data
public class SpotFullDto {
    private String address;
    private Integer capacity;
    private UserDto user;
}
