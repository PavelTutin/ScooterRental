package by.tutin.service.mappers;

import by.tutin.model.Order;
import by.tutin.model.User;
import by.tutin.model.dto.UserAdminInfoDto;
import by.tutin.model.dto.UserDto;
import by.tutin.model.dto.UserOrderDto;
import by.tutin.model.dto.UserOrdersInfoDto;
import by.tutin.model.dto.auth.RegistrationRequestDto;
import by.tutin.model.dto.auth.RegistrationResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User RegistrationRequestDtoToUser(RegistrationRequestDto requestDto);

    RegistrationResponseDto UserToRegistrationResponseDto(User user);

    UserDto UserToUserDto(User user);

    List<UserDto> UserToUserDto(List<User> user);

    RegistrationResponseDto RequestToResponse(RegistrationRequestDto requestDto);

    UserAdminInfoDto UserToUserAdminInfoDto(User user);

    UserOrdersInfoDto UserToUserOrdersInfoDto(String username, List<Order> orders);

    @Mappings({@Mapping(target = "startSpotAddress", expression = "java(order.getStartSpot().getAddress())"),
            @Mapping(target = "endSpotAddress", expression = "java(order.getEndSpot().getAddress())"),
            @Mapping(target = "scooterModel", expression = "java(order.getScooter().getModel())")})
    UserOrderDto orderToUserOrderDto(Order order);
}
