package by.tutin.service.mappers;

import by.tutin.api.dao.ScooterDao;
import by.tutin.api.dao.SpotDao;
import by.tutin.model.Order;
import by.tutin.model.dto.OrderDto;
import by.tutin.model.dto.OrderScooterInfoDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Mapper(componentModel = "spring")
public abstract class OrderMapper {

    @Autowired
    protected SpotDao spotDao;
    @Autowired
    protected ScooterDao scooterDao;


    @Mappings({@Mapping(target = "scooter", expression = "java(scooterDao.getById(orderDto.getScooterId()))"),
            @Mapping(target = "startSpot", expression = "java(spotDao.getById(orderDto.getStartSpotId()))"),
            @Mapping(target = "endSpot", expression = "java(spotDao.getById(orderDto.getEndSpotId()))")})
    public abstract Order OrderDtoToOrder(OrderDto orderDto);

    @Mappings({@Mapping(target = "scooterModel",expression = "java(order.getScooter().getModel())"),
    @Mapping(target = "startSpotAddress",expression = "java(order.getStartSpot().getAddress())"),
    @Mapping(target = "endSpotAddress",expression = "java(order.getEndSpot().getAddress())")})
    public abstract OrderScooterInfoDto OrderToOrderScooterInfoDto(Order order);

    public abstract List<OrderScooterInfoDto> OrderToOrderScooterInfoDto(List<Order> order);
}
