package by.tutin.service;

import by.tutin.api.dao.OrderDao;
import by.tutin.api.dao.ScooterDao;
import by.tutin.api.dao.UserDao;
import by.tutin.api.service.OrderService;
import by.tutin.exception.DaoException;
import by.tutin.exception.ServiceException;
import by.tutin.model.Order;
import by.tutin.model.Scooter;
import by.tutin.model.User;
import by.tutin.model.dto.OrderDto;
import by.tutin.model.dto.OrderScooterInfoDto;
import by.tutin.model.enums.Tariff;
import by.tutin.model.enums.UserStatus;
import by.tutin.service.mappers.OrderMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.temporal.ChronoUnit;
import java.util.List;

@Log4j
@RequiredArgsConstructor
@Service
@Transactional
public class OrderServiceImpl implements OrderService {

    private final UserDao userDao;
    private final OrderDao orderDao;
    private final OrderMapper orderMapper;
    private final ScooterDao scooterDao;


    @Override
    public void add(OrderDto orderDto) {
        log.info("Service. try to add order from orderDto");

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        User user = userDao.getByUsername(username);

        if (user.getStatus().equals(UserStatus.NON_ACTIVE))
            throw new ServiceException("User is deactivated");

        Order order = orderMapper.OrderDtoToOrder(orderDto);
        Scooter scooter = order.getScooter();

        order.setUser(userDao.getByUsername(username));

        double minutes = ChronoUnit.MINUTES.between(order.getTimeStart(), order.getTimeEnd());
        int battery = (int) minutes / 15;
        scooter.setBattery(scooter.getBattery() - battery);
        scooterDao.update(scooter);

        if (order.getTariff().equals(Tariff.SUBSCRIPTION)) {
            if (user.getSubscription() > 0) {
                user.setSubscription(user.getSubscription() - 1);
                userDao.update(user);
            } else {
                order.setTariff(Tariff.ONCE);
            }

        }

        orderDao.save(order);
    }

    @Override
    public OrderScooterInfoDto getById(Long id) {
        log.info("service. try to getById order with id " + id);

        return orderMapper.OrderToOrderScooterInfoDto(orderDao.getById(id));
    }

    @Override
    public List<OrderScooterInfoDto> getAll() {
        log.info("service. try to getAll orders");

        return orderMapper.OrderToOrderScooterInfoDto(orderDao.getAll());
    }

    @Override
    public OrderScooterInfoDto update(Order order) {
        log.info("service. try to update the order with id " + order.getId());

        return orderMapper.OrderToOrderScooterInfoDto(orderDao.update(order));
    }

    @Override
    public void delete(Long id) {
        log.info("service. try to delete the order with id " + id);

        Order order = orderDao.getById(id);
        orderDao.delete(order);
    }
}
