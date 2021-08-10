package by.tutin.controller;

import by.tutin.api.service.ScooterService;
import by.tutin.model.dto.ScooterDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Log4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/scooters")
public class ScooterController {

    private final ScooterService scooterService;

    @GetMapping("/{id}")
    public ResponseEntity<ScooterDto> getById(@PathVariable Long id){
        log.info("/scooters/@get"+id);
        ScooterDto scooterDto = scooterService.getById(id);
        return ResponseEntity.ok(scooterDto);
    }

    @PostMapping("/admin")
    public ResponseEntity<Void> add(@RequestBody ScooterDto scooterDto){
        log.info("/scooters/admin/@post");
        scooterService.add(scooterDto);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("")
    public ResponseEntity<List<ScooterDto>> getAll(){
        log.info("/scooters/@getAll");
        List<ScooterDto> fullDtoList = scooterService.getAll();
        return ResponseEntity.ok(fullDtoList);
    }

    @DeleteMapping("/admin/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        log.info("/scooters/admin/@delete"+id);
        scooterService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/admin/setPrice")
    public ResponseEntity<Void> editPrice(@RequestParam(name = "scooterId") Long scooterId
            ,@RequestParam(name = "price") Integer price){
        log.info(String.format("scooters/admin/setPrice?scooterId= %d &price= %d",scooterId,price));
        scooterService.editPrice(scooterId,price);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/admin/setStatus")
    public ResponseEntity<Void> changeStatus(@RequestParam(name = "scooterId") Long scooterId
            ,@RequestParam(name = "status") String status){
        log.info(String.format("scooters/admin/setStatus?scooterId= %d &status= %s",scooterId,status));
        scooterService.changeStatus(scooterId,status);
        return ResponseEntity.noContent().build();
    }
}
