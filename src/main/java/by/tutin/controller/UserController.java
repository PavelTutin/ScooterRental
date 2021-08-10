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

    @GetMapping("/{id}") // TODO надо ли он вообще
    public ResponseEntity<UserDto> getById(@PathVariable Long id) {
        log.info("/users/"+id);
        UserDto userDto = userService.getById(id);
        return ResponseEntity.ok(userDto);
    }

    @GetMapping("")
    public ResponseEntity<List<UserDto>> getAll() {
        log.info("/users@getAll");
        List<UserDto> userDtoList = userService.getAll();
        return ResponseEntity.ok(userDtoList);
    }

    @DeleteMapping("/admin/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        log.info("/users/admin/@delete"+id);
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

    @PatchMapping("/admin/setSubscription")
    public ResponseEntity<Void> setSubscription(@RequestParam(name = "userId") Long userId
            , @RequestParam(name = "subscription") Integer subscription) {
        log.info(String.format("/users/admin/setSubscription?userId= %d &subscription= %d",userId,subscription));
        userService.setSubscription(userId, subscription);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/admin/setDiscount")
    public ResponseEntity<Void> setDiscount(@RequestParam(name = "userId") Long userId
            , @RequestParam(name = "discount") Integer discount) {
        log.info(String.format("/users/admin/setDiscount?userId= %d &discount= %d",userId,discount));
        userService.setDiscount(userId, discount);
        return ResponseEntity.noContent().build();
    }
    @PatchMapping("/admin/setAdmin")
    public ResponseEntity<Void> setDiscount(@RequestParam(name = "userId") Long userId) {
        log.info("/users/admin/setAdmin?userId="+userId);
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
