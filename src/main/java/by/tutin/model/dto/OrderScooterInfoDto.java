package by.tutin.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class OrderScooterInfoDto {

    private String scooterModel;
    private Integer distance;
    private String tariff;
    private Integer price;
    private String timeStart;
    private String timeEnd;
    private String startSpotAddress;
    private String endSpotAddress;
}
