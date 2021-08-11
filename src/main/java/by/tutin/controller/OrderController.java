package by.tutin.controller;

import by.tutin.api.service.OrderService;
import by.tutin.model.dto.OrderDto;
import by.tutin.model.dto.OrderScooterInfoDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Log4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

    @GetMapping("/{id}")
    public ResponseEntity<OrderScooterInfoDto> getById(@PathVariable Long id) {
        log.info("/orders/" + id);
        OrderScooterInfoDto orderScooterInfoDto = orderService.getById(id);
        return ResponseEntity.ok(orderScooterInfoDto);
    }

    @PostMapping
    public ResponseEntity<Void> add(@RequestBody OrderDto orderDto) {
        log.info("controller. /orders/@path orderDto");
        orderService.add(orderDto);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/admin")
    public ResponseEntity<List<OrderScooterInfoDto>> getAll() {
        log.info("/orders/admin/(getAll)");
        List<OrderScooterInfoDto> orderScooterInfoDtos = orderService.getAll();
        return ResponseEntity.ok(orderScooterInfoDtos);
    }

    @DeleteMapping("/admin/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        log.info("/orders/admin/@delete" + id);
        orderService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
