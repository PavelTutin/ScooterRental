package by.tutin.controller;

import by.tutin.api.service.UserService;
import by.tutin.model.dto.auth.RegistrationRequestDto;
import by.tutin.model.dto.auth.UserAuthenticationRequestDto;
import by.tutin.model.dto.auth.UserAuthenticationResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Log4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;

    @PostMapping("/registration")
    public ResponseEntity<Void> register(@RequestBody RegistrationRequestDto requestDto) {
        log.info("/auth/registration username " + requestDto.getUsername());
        userService.register(requestDto);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/authenticate")
    public ResponseEntity<UserAuthenticationResponseDto> authenticate(@RequestBody UserAuthenticationRequestDto requestDto) {
        log.info(String.format("/auth/authenticate username(%s) password(%s)", requestDto.getUsername(), requestDto.getPassword()));
        UserAuthenticationResponseDto responseDto = userService.authenticate(requestDto);
        return ResponseEntity.ok(responseDto);
    }
}
