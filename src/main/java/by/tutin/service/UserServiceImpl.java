package by.tutin.service;

import by.tutin.api.dao.OrderDao;
import by.tutin.api.dao.SpotDao;
import by.tutin.api.dao.UserDao;
import by.tutin.api.service.UserService;
import by.tutin.exception.ServiceException;
import by.tutin.model.Order;
import by.tutin.model.User;
import by.tutin.model.dto.UserAdminInfoDto;
import by.tutin.model.dto.UserDto;
import by.tutin.model.dto.UserOrdersInfoDto;
import by.tutin.model.dto.auth.RegistrationRequestDto;
import by.tutin.model.dto.auth.RegistrationResponseDto;
import by.tutin.model.dto.auth.UserAuthenticationRequestDto;
import by.tutin.model.dto.auth.UserAuthenticationResponseDto;
import by.tutin.model.enums.Role;
import by.tutin.model.enums.UserStatus;
import by.tutin.security.jwt.JwtTokenProvider;
import by.tutin.service.mappers.UserMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Log4j
@RequiredArgsConstructor
@Transactional
@Service
public class UserServiceImpl implements UserService {

    private final UserDao userDao;
    private final SpotDao spotDao;
    private final OrderDao orderDao;

    private final UserMapper userMapper;

    private final BCryptPasswordEncoder passwordEncoder;

    private final JwtTokenProvider tokenProvider;

    @Override
    public void add(RegistrationRequestDto requestDto) {
        log.info("try to add user from requestDto");

        User user = userMapper.RegistrationRequestDtoToUser(requestDto);
        user.setRole(Role.USER);
        user.setStatus(UserStatus.ACTIVE);
        String password = passwordEncoder.encode(user.getPassword());
        user.setPassword(password);

        userDao.save(user);
    }

    @Override
    public UserDto getById(Long id) {
        log.info("try to getById user with id " + id);

        return userMapper.UserToUserDto(userDao.getById(id));
    }

    @Override
    public List<UserDto> getAll() {
        log.info("try to getAll user");

        return userMapper.UserToUserDto(userDao.getAll());
    }

    @Override
    public UserDto update(User user) {
        log.info("try to update the user with id " + user.getId());

        return userMapper.UserToUserDto(userDao.update(user));
    }

    @Override
    public void delete(Long id) {
        log.info("try to delete the user with id " + id);

        User user = userDao.getById(id);

        spotDao.checkForUserLinks(id);
        orderDao.checkForUserLinks(id);

        userDao.delete(user);
    }

    @Override
    public UserDto getSelfInfo() {
        log.info("try to take self info about user");

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = userDao.getByUsername(username);

        return userMapper.UserToUserDto(user);
    }

    @Override
    public RegistrationResponseDto register(RegistrationRequestDto requestDto) {
        log.info("try to register user with username  "+requestDto.getUsername());

        if (userDao.getByUsername(requestDto.getUsername()) != null)
            throw new ServiceException("already has this username");
        if (requestDto.getPassword().length() < 4)
            throw new ServiceException("length of pass less 4");
        add(requestDto);

        return userMapper.RequestToResponse(requestDto);
    }

    @Override
    public UserAuthenticationResponseDto authenticate(UserAuthenticationRequestDto requestDto) {
        log.info("try to authenticate user with username "+requestDto.getUsername());

        User user = userDao.getByUsername(requestDto.getUsername());

        if (!passwordEncoder.matches(requestDto.getPassword(), user.getPassword()))
            throw new ServiceException("Bad login or password");
        UserAuthenticationResponseDto responseDto = new UserAuthenticationResponseDto();
        responseDto.setUsername(user.getUsername());
        responseDto.setToken(tokenProvider.generateToken(user));

        return responseDto;
    }

    @Override
    public UserAdminInfoDto getAdminInfoByUserId(Long id) {
        log.info("try to get admin info by user with id" + id);

        User user = userDao.getById(id);

        return userMapper.UserToUserAdminInfoDto(user);
    }

    @Override
    public UserOrdersInfoDto getUserOrdersInfo(Long id) {
        log.info("try to take info about user and orders with userId " + id);

        User user = userDao.getById(id);
        List<Order> orders = orderDao.getOrdersByUserId(id);

        return  userMapper.UserToUserOrdersInfoDto(user.getUsername(), orders);
    }

    @Override
    public UserOrdersInfoDto getUserOrdersInfo() {
        log.info("try to take self info about user and orders with userId ");

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = userDao.getByUsername(username);
        List<Order> orders = orderDao.getOrdersByUserId(user.getId());

        return userMapper.UserToUserOrdersInfoDto(user.getUsername(), orders);
    }

    @Override
    public User getByUsername(String username) {
        log.info("try to take user by username - " + username);

        return userDao.getByUsername(username);
    }

    @Override
    public void deactivate() {
        log.info("try to deactivate");

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = userDao.getByUsername(username);
        user.setStatus(UserStatus.NON_ACTIVE);

        update(user);
    }

    @Override
    public void setSubscription(Long userId, Integer subscription) {
        log.info(String.format("try to set subscription %d to user with id %d", subscription, userId));

        if (subscription < 0 || subscription > 10) {
            log.warn("service. Bad number of subscription " + subscription);
            throw new ServiceException("Bad number of subscription");
        }
        User user = userDao.getById(userId);
        user.setSubscription(subscription);

        update(user);
    }

    @Override
    public void setDiscount(Long userId, Integer discount) {
        log.info(String.format("try to set discount %d to user with id %d", discount, userId));

        if (discount < 0 || discount > 90) {
            log.warn("service. Bad number of discount" + discount);
            throw new ServiceException("Bad number of discount");
        }
        User user = userDao.getById(userId);
        user.setDiscount(discount);

        update(user);
    }

    @Override
    public void setAdminRole(Long userId) {
        log.info("try to set ADMIN role to user with id" + userId);

        User user = userDao.getById(userId);
        user.setRole(Role.ADMIN);

        update(user);
    }

}
