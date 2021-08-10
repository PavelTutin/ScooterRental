package by.tutin.service.mappers;

import by.tutin.model.Order;
import by.tutin.model.Scooter;
import by.tutin.model.dto.OrderInfoDto;
import by.tutin.model.dto.ScooterDto;
import by.tutin.model.dto.ScooterOrderDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ScooterMapper {

    @Mapping(target = "spot", ignore = true)
    Scooter ScooterDtoToScooter(ScooterDto scooterDto);

    @Mapping(target = "spot", expression ="java(scooter.getSpot().getAddress())")
    ScooterDto ScooterToScooterDto(Scooter scooter);

    List<ScooterDto> ScooterToScooterDto(List<Scooter> scooter);

    ScooterOrderDto ScooterToScooterOrderDto(Scooter scooter, List<Order> orders);

    @Mappings({@Mapping(target = "startSpotAddress",expression ="java(order.getStartSpot().getAddress())"),
            @Mapping(target = "endSpotAddress",expression ="java(order.getEndSpot().getAddress())")    })
    OrderInfoDto orderToOrderInfoDto(Order order);
}
