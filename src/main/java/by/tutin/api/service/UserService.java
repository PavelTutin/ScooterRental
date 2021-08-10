package by.tutin.api.service;

import by.tutin.model.User;
import by.tutin.model.dto.UserAdminInfoDto;
import by.tutin.model.dto.UserDto;
import by.tutin.model.dto.UserOrdersInfoDto;
import by.tutin.model.dto.auth.RegistrationRequestDto;
import by.tutin.model.dto.auth.RegistrationResponseDto;
import by.tutin.model.dto.auth.UserAuthenticationRequestDto;
import by.tutin.model.dto.auth.UserAuthenticationResponseDto;

import java.util.List;


public interface UserService {

    void add(RegistrationRequestDto requestDto);
    UserDto getById(Long id);
    List<UserDto> getAll();
    UserDto update(User user);
    void delete(Long id);

    UserDto getSelfInfo();

    RegistrationResponseDto register(RegistrationRequestDto requestDto);
    UserAuthenticationResponseDto authenticate(UserAuthenticationRequestDto requestDto);
    UserAdminInfoDto getAdminInfoByUserId(Long id);
    UserOrdersInfoDto getUserOrdersInfo(Long id);
    UserOrdersInfoDto getUserOrdersInfo();
    User getByUsername(String username);

    void deactivate();

    void setSubscription(Long userId, Integer subscription);
    void setDiscount(Long userId,Integer discount);
    void setAdminRole(Long userId);
}
