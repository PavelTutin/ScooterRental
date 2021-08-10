package by.tutin.api.service;


import by.tutin.model.Spot;
import by.tutin.model.dto.SpotDto;
import by.tutin.model.dto.SpotFullDto;
import by.tutin.model.dto.SpotScooterInformationDto;

import java.util.List;

public interface SpotService {

    void add(SpotDto spotDto);
    SpotFullDto getById(Long id);
    List<SpotFullDto> getAll();
    SpotFullDto update(Spot spot);
    void delete(Long id);

    void changeParent(Long spotId,Long parentId);
    SpotScooterInformationDto spotScooterInformation(Long spotId);
    void setAdmin(Long spotId,Long userId);


}
