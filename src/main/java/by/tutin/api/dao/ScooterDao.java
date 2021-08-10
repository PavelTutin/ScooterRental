package by.tutin.api.dao;

import by.tutin.model.Scooter;

import java.util.List;

public interface ScooterDao extends GenericDao<Scooter>{
    List<Scooter> getScootersBySpot(Long spotId);

}
