package by.tutin.service;

import by.tutin.api.dao.OrderDao;
import by.tutin.api.dao.ScooterDao;
import by.tutin.api.dao.SpotDao;
import by.tutin.api.dao.UserDao;
import by.tutin.api.service.SpotService;
import by.tutin.exception.DaoException;
import by.tutin.exception.ServiceException;
import by.tutin.model.Scooter;
import by.tutin.model.Spot;
import by.tutin.model.User;
import by.tutin.model.dto.SpotDto;
import by.tutin.model.dto.SpotFullDto;
import by.tutin.model.dto.SpotScooterInformationDto;
import by.tutin.model.enums.SpotStatus;
import by.tutin.service.mappers.SpotMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Log4j
@Transactional
@Service
@RequiredArgsConstructor
public class SpotServiceImpl implements SpotService {

    private final SpotDao spotDao;
    private final ScooterDao scooterDao;
    private final OrderDao orderDao;
    private final UserDao userDao;

    private final SpotMapper spotMapper;


    @Override
    public void add(SpotDto spotDto) {
        log.info("try to add spot from spotDto");

        Spot spot = spotMapper.SpotDtoToSpot(spotDto);
        spotDao.save(spot);
    }

    @Override
    public SpotFullDto getById(Long id) {
        log.info("try to getById spot with id " + id);

        Spot spot = spotDao.getById(id);

        return spotMapper.SpotToSpotFullDto(spot, spot.getMaintainer());
    }

    @Override
    public List<SpotFullDto> getAll() {
        log.info("try to getAll spots");

        List<Spot> spots = spotDao.getAll();
        List<SpotFullDto> list = spots.stream()
                .map(x -> spotMapper.SpotToSpotFullDto(x, x.getMaintainer()))
                .collect(Collectors.toList());

        return list;
    }

    @Override
    public SpotFullDto update(Spot spot) {
        log.info("try to update the spot with id " + spot.getId());

        Spot spot1 = spotDao.update(spot);

        return spotMapper.SpotToSpotFullDto(spot1, spot.getMaintainer());
    }

    @Override
    public void delete(Long id) {
        log.info("try to delete the spot with id " + id);

        Spot spot = spotDao.getById(id);
        spot.setStatus(SpotStatus.INACTIVE);

        update(spot);
    }

    @Override
    public void changeParent(Long spotId, Long parentId) {
        log.info(String.format("try to set parent with id %d in spot with id %d", parentId, spotId));

        Spot parentSpot = spotDao.getById(parentId);
        Spot currentSpot = spotDao.getById(spotId);
        int scootersNumInParent = spotDao.getScootersNumber(parentId);
        int scootersNumInCurrent = spotDao.getScootersNumber(spotId);
        if (parentSpot.getCapacity() - scootersNumInParent > scootersNumInCurrent && parentSpot.getCapacity() > currentSpot.getCapacity()) {
            currentSpot.setParent(parentSpot);
            update(currentSpot);
        } else {
            throw new ServiceException("A lot of scooters in current spot");
        }
    }

    @Override
    public SpotScooterInformationDto spotScooterInformation(Long spotId) {
        log.info("try to take info about scooters in spot with id %d" + spotId);

        Spot spot = spotDao.getById(spotId);
        List<Scooter> scooters = scooterDao.getScootersBySpot(spotId);

        return spotMapper.SpotToSpotScooterInformationDto(spot, scooters.size(), scooters);
    }

    @Override
    public void setAdmin(Long spotId, Long userId) {
        log.info(String.format("try to set maintainer with id %d in spot with id %d", userId, spotId));

        Spot spot = spotDao.getById(spotId);
        User user = userDao.getById(userId);
        spot.setMaintainer(user);

        update(spot);
    }

}
