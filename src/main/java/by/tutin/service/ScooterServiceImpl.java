package by.tutin.service;

import by.tutin.api.dao.OrderDao;
import by.tutin.api.dao.ScooterDao;
import by.tutin.api.dao.SpotDao;
import by.tutin.api.service.ScooterService;
import by.tutin.exception.DaoException;
import by.tutin.exception.ServiceException;
import by.tutin.model.Order;
import by.tutin.model.Scooter;
import by.tutin.model.Spot;
import by.tutin.model.dto.ScooterDto;
import by.tutin.model.dto.ScooterOrderDto;
import by.tutin.model.enums.ScooterStatus;
import by.tutin.service.mappers.ScooterMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Log4j
@RequiredArgsConstructor
@Transactional
@Service
public class ScooterServiceImpl implements ScooterService {

    private final ScooterDao scooterDao;
    private final SpotDao spotDao;
    private final OrderDao orderDao;

    private final ScooterMapper scooterMapper;


    @Override
    public void add(ScooterDto scooterDto) {
        log.info("try to add scooter from scooterDto");

        Scooter scooter = scooterMapper.ScooterDtoToScooter(scooterDto);
        scooterDao.save(scooter);
    }

    @Override
    public ScooterDto getById(Long id) {
        log.info("try to getById scooter with id " + id);

        return scooterMapper.ScooterToScooterDto(scooterDao.getById(id));
    }

    @Override
    public List<ScooterDto> getAll() {
        log.info("try to getAll scooters");

        return scooterMapper.ScooterToScooterDto(scooterDao.getAll());
    }

    @Override
    public ScooterDto update(Scooter scooter) {
        log.info("try to update the scooter with id " + scooter.getId());

        return scooterMapper.ScooterToScooterDto(scooterDao.update(scooter));
    }

    @Override
    public void delete(Long id) {
        log.info("try to delete the scooter with id " + id);

        Scooter scooter = scooterDao.getById(id);
        List<Order> orders = orderDao.getOrdersByScooterId(id);
        for (Order order : orders) {
            order.setScooter(null);
        }
        scooterDao.delete(scooter);
    }

    @Override
    public void setSpot(Long scooterId, Long spotId) {
        log.info(String.format("try to set scooter with %d spot with %d", scooterId, spotId));

        Spot spot = spotDao.getById(spotId);
        if (spot.getCapacity() > spotDao.getScootersNumber(spotId)) {
            Scooter scooter = scooterDao.getById(scooterId);
            scooter.setSpot(spot);
            update(scooter);
        } else {
            throw new ServiceException("No place for scooter");
        }
    }

    @Override
    public ScooterOrderDto fullScooterInfo(Long id) {
        log.info("try to full info about scooter with id " + id);

        Scooter scooter = scooterDao.getById(id);
        List<Order> orders = orderDao.getOrdersByScooterId(id);

        return scooterMapper.ScooterToScooterOrderDto(scooter, orders);
    }

    @Override
    public void editPrice(Long scooterId, int price) {
        log.info(String.format("try to set scooter with %d price  %d", scooterId, price));

        if (price >= 0) {
            Scooter scooter = scooterDao.getById(scooterId);
            scooter.setPrice(price);
            update(scooter);
        } else {
            log.warn("bad value of price " + price);
            throw new ServiceException("Bad value for price");
        }
    }


    @Override
    public void changeStatus(Long scooterId, String status) {
        try {
            log.info(String.format("try to set scooter with %d status  %s", scooterId, status));

            Scooter scooter = scooterDao.getById(scooterId);
            scooter.setStatus(ScooterStatus.valueOf(status));

            update(scooter);
        } catch (IllegalArgumentException e) {
            log.warn("bad status " + status, e);
            throw new ServiceException("Bad status", e);
        }
    }
}
