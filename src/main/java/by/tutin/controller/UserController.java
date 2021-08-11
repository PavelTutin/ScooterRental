package by.tutin.controller;

import by.tutin.api.service.UserService;
import by.tutin.model.dto.UserDto;
import by.tutin.model.dto.UserOrdersInfoDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Log4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @GetMapping("")
    public ResponseEntity<List<UserDto>> getAll() {
        log.info("/users@getAll");
        List<UserDto> userDtoList = userService.getAll();
        return ResponseEntity.ok(userDtoList);
    }

    @DeleteMapping("/admin/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        log.info("/users/admin/@delete" + id);
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/selfInfo")
    public ResponseEntity<UserDto> getSelfInfo() {
        log.info("/users/selfInfo");
        UserDto UserDto = userService.getSelfInfo();
        return ResponseEntity.ok(UserDto);
    }

    @GetMapping("/orders")
    public ResponseEntity<UserOrdersInfoDto> getUserOrdersInfo() {
        log.info("/users/orders");
        UserOrdersInfoDto userOrdersInfoDto = userService.getUserOrdersInfo();
        return ResponseEntity.ok(userOrdersInfoDto);
    }

    @PatchMapping("/admin/setSubscription/{userId}")
    public ResponseEntity<Void> setSubscription(@PathVariable Long userId
            , @RequestParam(name = "subscription") Integer subscription) {
        log.info(String.format("/users/admin/setSubscription?userId= %d &subscription= %d", userId, subscription));
        userService.setSubscription(userId, subscription);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/admin/setDiscount/{userId}")
    public ResponseEntity<Void> setDiscount(@PathVariable Long userId
            , @RequestParam(name = "discount") Integer discount) {
        log.info(String.format("/users/admin/setDiscount?userId= %d &discount= %d", userId, discount));
        userService.setDiscount(userId, discount);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/admin/setAdmin/{userId}")
    public ResponseEntity<Void> setDiscount(@PathVariable Long userId) {
        log.info("/users/admin/setAdmin?userId=" + userId);
        userService.setAdminRole(userId);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/deactivate")
    public ResponseEntity<Void> deactivate() {
        log.info("/users/deactivate");
        userService.deactivate();
        return ResponseEntity.noContent().build();
    }
}
