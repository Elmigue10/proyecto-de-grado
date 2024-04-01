package umb.v1.informationandproductmanagement.business.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import umb.v1.informationandproductmanagement.business.service.IAuthService;
import umb.v1.informationandproductmanagement.domain.model.dto.AuthRequestDTO;
import umb.v1.informationandproductmanagement.domain.model.dto.AuthResponseDTO;

@RestController
@Slf4j
@RequestMapping(value = "/auth", produces = MediaType.APPLICATION_JSON_VALUE)
public class AuthController {

    private final IAuthService authService;

    public AuthController(IAuthService authService) {
        this.authService = authService;
    }

    @PostMapping
    public ResponseEntity<AuthResponseDTO> auth(@RequestBody AuthRequestDTO authRequest) {
        log.info("auth request arrived: {}", authRequest);

        AuthResponseDTO authResponse = authService.auth(authRequest);

        return new ResponseEntity<>(authResponse, HttpStatus.OK);
    }

    @PostMapping(value = "/refresh-token")
    public ResponseEntity<AuthResponseDTO> auth(HttpServletRequest request) {
        return ResponseEntity.ok(authService.refreshToken(request));
    }

}
