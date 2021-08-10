package by.tutin.service;

import by.tutin.api.dao.OrderDao;
import by.tutin.api.dao.SpotDao;
import by.tutin.api.dao.UserDao;
import by.tutin.api.service.UserService;
import by.tutin.exception.DaoException;
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
        try {
            log.info("Service. try to add user from requestDto");
            User user = userMapper.RegistrationRequestDtoToUser(requestDto);
            user.setRole(Role.USER);
            user.setStatus(UserStatus.ACTIVE);
            String password = passwordEncoder.encode(user.getPassword());
            user.setPassword(password);

            userDao.save(user);
        } catch (DaoException e) {
            log.warn("Service. can't add user from requestDto", e);
            throw new ServiceException(e);
        }

    }

    @Override
    public UserDto getById(Long id) {
        try {
            log.info("service. try to getById user with id " + id);
            return userMapper.UserToUserDto(userDao.getById(id));
        } catch (DaoException e) {
            log.warn("service. can't getById user with id " + id, e);
            throw new ServiceException(e);
        }
    }

    @Override
    public List<UserDto> getAll() {
        try {
            log.info("service. try to getAll user");
            return userMapper.UserToUserDto(userDao.getAll());
        } catch (DaoException e) {
            log.warn("service. can't getAll users", e);
            throw new ServiceException(e);
        }
    }

    @Override
    public UserDto update(User user) {
        try {
            log.info("service. try to update the user with id " + user.getId());
            return userMapper.UserToUserDto(userDao.update(user));
        } catch (DaoException e) {
            log.warn("service. can't update the user with id " + user.getId(), e);
            throw new ServiceException(e);
        }
    }

    @Override
    public void delete(Long id) {
        try {
            log.info("service. try to delete the user with id " + id);
            User user = userDao.getById(id);

            spotDao.checkForUserLinks(id);
            orderDao.checkForUserLinks(id);

            userDao.delete(user);
        } catch (DaoException e) {
            log.warn("service. can't delete the user with id " + id, e);
            throw new ServiceException(e);
        }
    }

    @Override
    public UserDto getSelfInfo() {
        try {
            log.info("service. try to take self info about user");
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();
            User user = userDao.getByUsername(username);

            return userMapper.UserToUserDto(user);
        } catch (DaoException e) {
            log.warn("service. can't take self info about user", e);
            throw new ServiceException(e);
        }
    }

    @Override
    public RegistrationResponseDto register(RegistrationRequestDto requestDto) {
        try {
            log.info(String.format("service. try to register user with username(%s) password (%s)", requestDto.getUsername(), requestDto.getPassword()));
            if (userDao.getByUsername(requestDto.getUsername()) != null)
                throw new ServiceException("already has this username");
            if (requestDto.getPassword().length() < 4)
                throw new ServiceException("length of pass less 4");
            add(requestDto);
            RegistrationResponseDto registrationResponseDto = userMapper.RequestToResponse(requestDto);
            return registrationResponseDto;
        } catch (DaoException e) {
            log.warn(String.format("service. try can't register user with username(%s) password (%s)", requestDto.getUsername(), requestDto.getPassword()), e);
            throw new ServiceException(e);
        }
    }

    @Override
    public UserAuthenticationResponseDto authenticate(UserAuthenticationRequestDto requestDto) {
        try {
            log.info(String.format("service. try to authenticate user with username(%s) password (%s)", requestDto.getUsername(), requestDto.getPassword()));
            User user = userDao.getByUsername(requestDto.getUsername());

            if (!passwordEncoder.matches(requestDto.getPassword(), user.getPassword()))
                throw new ServiceException("Password are not equals");
            UserAuthenticationResponseDto responseDto = new UserAuthenticationResponseDto();
            responseDto.setUsername(user.getUsername());
            responseDto.setToken(tokenProvider.generateToken(user));


            return responseDto;
        } catch (DaoException e) {
            log.warn(String.format("service. can't authenticate user with username(%s) password (%s)", requestDto.getUsername(), requestDto.getPassword()), e);
            throw new ServiceException(e);
        }
    }

    @Override
    public UserAdminInfoDto getAdminInfoByUserId(Long id) {
        try {
            log.info("service. try to get admin info by user with id" + id);
            User user = userDao.getById(id);
            UserAdminInfoDto userAdminInfoDto = userMapper.UserToUserAdminInfoDto(user);
            return userAdminInfoDto;
        } catch (DaoException e) {
            log.warn("service. can't get admin info by user with id" + id, e);
            throw new ServiceException(e);
        }
    }

    @Override
    public UserOrdersInfoDto getUserOrdersInfo(Long id) {
        try {
            log.info("service. try to take info about user and orders with userId " + id);
            User user = userDao.getById(id);
            List<Order> orders = orderDao.getOrdersByUserId(id);
            UserOrdersInfoDto userOrdersInfoDto = userMapper.UserToUserOrdersInfoDto(user.getUsername(), orders);
            return userOrdersInfoDto;
        } catch (DaoException e) {
            log.warn("service. can't take info about user and orders with userId " + id, e);
            throw new ServiceException(e);
        }
    }

    @Override
    public UserOrdersInfoDto getUserOrdersInfo() {
        try {
            log.info("service. try to take self info about user and orders with userId ");
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();
            User user = userDao.getByUsername(username);
            List<Order> orders = orderDao.getOrdersByUserId(user.getId());
            UserOrdersInfoDto userOrdersInfoDto = userMapper.UserToUserOrdersInfoDto(user.getUsername(), orders);
            return userOrdersInfoDto;

        } catch (DaoException e) {
            log.warn("service. can't take self info about user and orders with userId ", e);
            throw new ServiceException(e);
        }
    }

    @Override
    public User getByUsername(String username) {
        try {
            log.info("service. try to take user by username - " + username);
            return userDao.getByUsername(username);
        } catch (DaoException e) {
            log.warn("service. can't take user by username - " + username, e);
            throw new ServiceException(e);
        }
    }

    @Override
    public void deactivate() {
        try {
            log.info("service. try to deactivate");
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();
            User user = userDao.getByUsername(username);
            user.setStatus(UserStatus.NON_ACTIVE);
            update(user);
        } catch (DaoException e) {
            log.warn("service. cant deactivate", e);
            throw new ServiceException(e);
        }
    }

    @Override
    public void setSubscription(Long userId, Integer subscription) {
        try {
            log.info(String.format("service. try to set subscription %d to user with id %d", subscription, userId));
            if (subscription < 0 || subscription > 10) {
                log.warn("service. Bad number of subscription " + subscription);
                throw new ServiceException("Bad number of subscription");
            }
            User user = userDao.getById(userId);
            user.setSubscription(subscription);
            update(user);
        } catch (DaoException e) {
            log.warn(String.format("service. cant set subscription %d to user with id %d", subscription, userId), e);
            throw new ServiceException(e);
        }

    }

    @Override
    public void setDiscount(Long userId, Integer discount) {
        try {
            log.info(String.format("service. try to set discount %d to user with id %d", discount, userId));
            if (discount < 0 || discount > 90) {
                log.warn("service. Bad number of discount" + discount);
                throw new ServiceException("Bad number of discount");
            }
            User user = userDao.getById(userId);
            user.setDiscount(discount);
            update(user);
        } catch (DaoException e) {
            log.warn(String.format("service. cant set discount %d to user with id %d", discount, userId), e);
            throw new ServiceException(e);
        }
    }

    @Override
    public void setAdminRole(Long userId) {
        try {
            log.info("service. try to set ADMIN role to user with id" + userId);
            User user = userDao.getById(userId);
            user.setRole(Role.ADMIN);
            update(user);
        } catch (DaoException e) {
            log.warn("service. try to set ADMIN role to user with id" + userId, e);
            throw new ServiceException(e);
        }
    }

}
