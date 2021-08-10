package by.tutin.model.dto;

import lombok.Data;

@Data
public class OrderInfoDto {
    private Integer distance;
    private String tariff;
    private Integer price;
    private String timeStart;
    private String timeEnd;
    private String startSpotAddress;
    private String endSpotAddress;
}
