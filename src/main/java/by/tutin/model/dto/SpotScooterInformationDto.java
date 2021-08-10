package by.tutin.model.dto;


import lombok.Data;

import java.util.List;


@Data
public class SpotScooterInformationDto {
    private String address;
    private Integer capacity;
    private Integer scooterNum;
    private List<ScooterDto> scooters;

}
