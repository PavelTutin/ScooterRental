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
import by.tutin.service.mappers.SpotMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

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
        try {
            log.info("Service. try to add spot from spotDto");
            Spot spot = spotMapper.SpotDtoToSpot(spotDto);
            spotDao.save(spot);
        } catch (DaoException e) {
            log.warn("Service. can't add spot from spotDto",e);
            throw new ServiceException(e);
        }
    }

    @Override
    public SpotFullDto getById(Long id) {
        try {
            log.info("service. try to getById spot with id "+id);
            Spot spot = spotDao.getById(id);
            return spotMapper.SpotToSpotFullDto(spot,spot.getMaintainer());
        } catch (DaoException e) {
            log.warn("service. can't getById spot with id "+id,e);
            throw new ServiceException(e);
        }
    }

    @Override
    public List<SpotFullDto> getAll() {
        try {
            log.info("service. try to getAll spots");
            List<SpotFullDto> list = new ArrayList<>();
            List<Spot> spots = spotDao.getAll();
            for (Spot spot: spots){
                list.add(spotMapper.SpotToSpotFullDto(spot,spot.getMaintainer()));
            }
            return list;
        } catch (DaoException e) {
            log.warn("service. can't getAll spots",e);
            throw new ServiceException(e);
        }
    }

    @Override
    public SpotFullDto update(Spot spot) {
        try {
            log.info("service. try to update the spot with id "+spot.getId());
            Spot spot1 = spotDao.update(spot);
            return spotMapper.SpotToSpotFullDto(spot1,spot.getMaintainer());
        } catch (DaoException e) {
            log.warn("service. can't update the spot with id "+spot.getId(),e);
            throw new ServiceException(e);
        }
    }

    @Override
    public void delete(Long id) {
        try {
            log.info("service. try to delete the spot with id "+id);
            Spot spot = spotDao.getById(id);

            List<Scooter> scooters=scooterDao.getScootersBySpot(id);
            orderDao.checkForSpot(id);

            for (Scooter scooter : scooters){
                scooter.setSpot(null);
                scooterDao.update(scooter);
            }

            spotDao.delete(spot);
        } catch (DaoException e) {
            log.warn("service. can't delete the spot with id "+id,e);
            throw new ServiceException(e);
        }
    }

    @Override
    public void changeParent(Long spotId, Long parentId) {
        try {
            log.info(String.format("try to set parent with id %d in spot with id %d",parentId,spotId));
            Spot parentSpot = spotDao.getById(parentId);
            Spot currentSpot = spotDao.getById(spotId);
            int scootersNumInParent = spotDao.getScootersNumber(parentId);
            int scootersNumInCurrent = spotDao.getScootersNumber(spotId);
            if (parentSpot.getCapacity() - scootersNumInParent > scootersNumInCurrent) {
                currentSpot.setParent(parentSpot);
                update(currentSpot);
            } else {
                throw new ServiceException("A lot of scooters in current spot");
            }
        } catch (DaoException e) {
            log.warn(String.format("can't set parent with id %d in spot with id %d",parentId,spotId),e);
            throw new ServiceException(e);
        }

    }

    @Override
    public SpotScooterInformationDto spotScooterInformation(Long spotId) {
        try {
            log.info("try to take info about scooters in spot with id %d"+spotId);
            Spot spot = spotDao.getById(spotId);
            List<Scooter> scooters = scooterDao.getScootersBySpot(spotId);
            SpotScooterInformationDto spotScooterInformationDto = spotMapper.SpotToSpotScooterInformationDto(spot, scooters.size(), scooters);
            return spotScooterInformationDto;
        } catch (DaoException e) {
            log.warn("try to take info about scooters in spot with id %d"+spotId,e);
            throw new ServiceException(e);
        }
    }

    @Override
    public void setAdmin(Long spotId, Long userId) {
        try {
            log.info(String.format("try to set maintainer with id %d in spot with id %d",userId,spotId));
            Spot spot = spotDao.getById(spotId);
            User user = userDao.getById(userId);
            spot.setMaintainer(user);
            update(spot);
        } catch (DaoException e) {
            log.warn(String.format("can't set maintainer with id %d in spot with id %d",userId,spotId));
            throw new ServiceException(e);
        }
    }

}
