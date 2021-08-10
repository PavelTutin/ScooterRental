package by.tutin.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDto {
    private Long scooterId;
    private Long userId;
    private Integer distance;
    private String tariff;
    private Integer price;
    private String timeStart;
    private String timeEnd;
    private Long startSpotId;
    private Long endSpotId;

}
