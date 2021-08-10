package by.tutin.model.dto;

import lombok.Data;

import java.util.List;

@Data
public class UserOrdersInfoDto {
    private String username;
    private List<UserOrderDto> orders;
}
