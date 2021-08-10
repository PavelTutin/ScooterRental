package by.tutin.api.service;

import by.tutin.model.Order;
import by.tutin.model.dto.OrderDto;
import by.tutin.model.dto.OrderScooterInfoDto;

import java.util.List;

public interface OrderService{

    void add( OrderDto orderDto);
    OrderScooterInfoDto getById(Long id);
    List<OrderScooterInfoDto> getAll();
    OrderScooterInfoDto update(Order order);
    void delete(Long id);

}
