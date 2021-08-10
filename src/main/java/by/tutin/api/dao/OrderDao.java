package by.tutin.api.dao;

import by.tutin.model.Order;

import java.util.List;

public interface OrderDao extends GenericDao<Order>{
    List<Order> getOrdersByScooterId(Long scooterId);

    void checkForSpot(Long id);

    void checkForUserLinks(Long id);

    List<Order> getOrdersByUserId(Long userId);
}
