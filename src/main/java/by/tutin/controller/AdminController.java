package by.tutin.controller;

import by.tutin.api.service.ScooterService;
import by.tutin.api.service.UserService;
import by.tutin.model.dto.ScooterOrderDto;
import by.tutin.model.dto.UserAdminInfoDto;
import by.tutin.model.dto.UserOrdersInfoDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Log4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/admin")
public class AdminController {

    private final ScooterService scooterService;
    private final UserService userService;

    @GetMapping("/scooterinfo/{scooterId}")
    public ResponseEntity<ScooterOrderDto> fullScooterInfo(@PathVariable Long scooterId) {
        log.info("/admin/scooterinfo/" + scooterId);
        ScooterOrderDto scooterOrderDto = scooterService.fullScooterInfo(scooterId);
        return ResponseEntity.ok(scooterOrderDto);
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<UserAdminInfoDto> getAdminInfoByUserId(@PathVariable Long id) {
        log.info("/admin/user?id=" + id);
        UserAdminInfoDto userAdminInfoDto = userService.getAdminInfoByUserId(id);
        return ResponseEntity.ok(userAdminInfoDto);
    }

    @GetMapping("/userOrders/{id}")
    public ResponseEntity<UserOrdersInfoDto> getAdminUserOrdersInfo(@PathVariable Long id) {
        log.info("/admin/userOrders?id=" + id);
        UserOrdersInfoDto userOrdersInfoDto = userService.getUserOrdersInfo(id);
        return ResponseEntity.ok(userOrdersInfoDto);
    }
}
