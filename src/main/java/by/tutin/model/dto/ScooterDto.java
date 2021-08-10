package by.tutin.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class ScooterDto {
    private String serialNumber;
    private String model;
    private Integer price;
    private Integer battery;
    private Integer batteryActual;
    private Integer maxSpeed;
    private String status;
    private String spot;
}
