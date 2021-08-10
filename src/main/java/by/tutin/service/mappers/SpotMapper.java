package by.tutin.service.mappers;

import by.tutin.api.dao.SpotDao;
import by.tutin.model.Scooter;
import by.tutin.model.Spot;
import by.tutin.model.User;
import by.tutin.model.dto.ScooterDto;
import by.tutin.model.dto.SpotDto;
import by.tutin.model.dto.SpotFullDto;
import by.tutin.model.dto.SpotScooterInformationDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Mapper(componentModel = "spring")
public abstract class SpotMapper {

    @Autowired
    protected SpotDao spotDao;

    @Mapping(target = "parent",expression = "java(spotDao.getById(spotDto.getParent()))")
    public abstract Spot SpotDtoToSpot(SpotDto spotDto);

    public abstract SpotScooterInformationDto SpotToSpotScooterInformationDto(Spot spot,int scooterNum, List<Scooter> scooters);

    @Mapping(target = "spot", expression ="java(scooter.getSpot().getAddress())")
    public abstract  ScooterDto ScooterToScooterDto(Scooter scooter);

    public abstract SpotFullDto SpotToSpotFullDto(Spot spot,User user);

    public abstract List<SpotFullDto> SpotToSpotFullDto(List<Spot> spot);

}
