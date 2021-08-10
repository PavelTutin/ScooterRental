package by.tutin.controller;

import by.tutin.api.service.ScooterService;
import by.tutin.api.service.UserService;
import by.tutin.model.dto.ScooterOrderDto;
import by.tutin.model.dto.UserAdminInfoDto;
import by.tutin.model.dto.UserOrdersInfoDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Log4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/admin")
public class AdminController {

    private final ScooterService scooterService;
    private final UserService userService;

    @GetMapping("/scooterinfo")
    public ResponseEntity<ScooterOrderDto> fullScooterInfo(@RequestParam(name = "scooterId") Long scooterId){
        log.info("/admin/scooterinfo?scooterId"+scooterId);
        ScooterOrderDto scooterOrderDto = scooterService.fullScooterInfo(scooterId);
        return ResponseEntity.ok(scooterOrderDto);
    }
    @GetMapping("/user")
    public ResponseEntity<UserAdminInfoDto> getAdminInfoByUserId(@RequestParam(name = "id") Long id) {
        log.info("/admin/user?id="+id);
        UserAdminInfoDto userAdminInfoDto = userService.getAdminInfoByUserId(id);
        return ResponseEntity.ok(userAdminInfoDto);
    }

    @GetMapping("/userOrders")
    public ResponseEntity<UserOrdersInfoDto> getAdminUserOrdersInfo(@RequestParam(name = "id") Long id) {
        log.info("/admin/userOrders?id="+id);
        UserOrdersInfoDto userOrdersInfoDto = userService.getUserOrdersInfo(id);
        return ResponseEntity.ok(userOrdersInfoDto);
    }
}
