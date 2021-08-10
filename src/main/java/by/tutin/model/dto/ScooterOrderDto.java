package by.tutin.model.dto;

import lombok.Data;

import java.util.List;
@Data
public class ScooterOrderDto {
    private String serialNumber;
    private String model;
    private Integer price;
    private Integer batteryActual;
    private Integer maxSpeed;
    private String status;
    private List<OrderInfoDto>  orders;
}
