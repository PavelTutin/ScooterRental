package by.tutin.controller;

import by.tutin.api.service.SpotService;
import by.tutin.model.dto.SpotDto;
import by.tutin.model.dto.SpotFullDto;
import by.tutin.model.dto.SpotScooterInformationDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Log4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/spots")
public class SpotController {

    private final SpotService spotService;

    @GetMapping("/{id}")
    public ResponseEntity<SpotFullDto> getById(@PathVariable Long id){
        log.info("/spots/@get"+id);
        SpotFullDto spotFullDto = spotService.getById(id);
        return ResponseEntity.ok(spotFullDto);
    }

    @PostMapping("/admin")
    public ResponseEntity<Void> add(@RequestBody SpotDto spotDto){
        log.info("/spots/admin@post");
        spotService.add(spotDto);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<SpotFullDto>> getAll(){
        log.info("/spots@getAll");
        List<SpotFullDto> fullDtoList = spotService.getAll();
        return ResponseEntity.ok(fullDtoList);
    }

    @DeleteMapping("/admin/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        log.info("/spots/admin/@delete"+id);
        spotService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/admin/setParent/{spotId}")
    public ResponseEntity<Void> changeParent(@PathVariable Long spotId
            ,@RequestParam(name = "parentId") Long parentId){
        log.info(String.format("/spots/admin/setParent?spotId= %d &parentId= %d",spotId,parentId));
        spotService.changeParent(spotId,parentId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/info/{spotId}")
    public ResponseEntity<SpotScooterInformationDto> spotScooterInformation(@PathVariable Long spotId){
        log.info("/spots/info?spotId="+spotId);
        SpotScooterInformationDto spotScooterInformationDto = spotService.spotScooterInformation(spotId);
        return ResponseEntity.ok(spotScooterInformationDto);
    }

    @PatchMapping("/admin/setAdmin/{spotId}")
    public ResponseEntity<Void> setAdmin(@PathVariable() Long spotId
            ,@RequestParam(name = "userId") Long userId){
        log.info(String.format("/spots/admin/setAdmin?spotId= %d & userId= %d",spotId,userId));
        spotService.setAdmin(spotId,userId);
        return ResponseEntity.noContent().build();
    }
}
