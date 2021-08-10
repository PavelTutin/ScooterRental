package by.tutin.api.service;



import by.tutin.model.Scooter;
import by.tutin.model.dto.ScooterDto;
import by.tutin.model.dto.ScooterOrderDto;

import java.util.List;

public interface ScooterService {
    void add(ScooterDto scooterDto);
    ScooterDto getById(Long id);
    List<ScooterDto> getAll();
    ScooterDto update(Scooter scooter);
    void delete(Long id);

    void setSpot(Long scooterId,Long spotId);
    ScooterOrderDto fullScooterInfo(Long id);
    void editPrice(Long scooterId,int price);
    void changeStatus(Long scooterId,String status);


}
