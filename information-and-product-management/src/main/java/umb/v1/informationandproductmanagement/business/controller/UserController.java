package umb.v1.informationandproductmanagement.business.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import umb.v1.informationandproductmanagement.business.service.interfaces.IUserService;
import umb.v1.informationandproductmanagement.domain.model.dto.*;

import java.util.Map;

@RestController
@Slf4j
@RequestMapping(value = "/user", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserController {

    private final IUserService userService;

    public UserController(IUserService userService) {
        this.userService = userService;
    }

    @PostMapping("/save")
    public ResponseEntity<ResponseProductDTO> save(@RequestBody UserDTO user) {
        log.info("arrived request for save...");

        ResponseProductDTO response = userService.save(user);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/search-history")
    public ResponseEntity<SearchHistoryResponseDTO> searchHistory(@RequestHeader Map<String, String> requestHeaders) {
        log.info("arrived request for search-history");

        SearchHistoryResponseDTO searchHistoryResponse = userService.searchHistory(requestHeaders);

        return new ResponseEntity<>(searchHistoryResponse, HttpStatus.OK);
    }

    @GetMapping("/viewed-products")
    public ResponseEntity<ResponseProductListDTO> viewedProducts(@RequestHeader Map<String, String> requestHeaders) {
        log.info("arrived request for viewed-products");

        ResponseProductListDTO responseProducts = userService.viewedProducts(requestHeaders);

        return new ResponseEntity<>(responseProducts, HttpStatus.OK);
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<ResponseProductDTO> forgotPassword(@RequestParam("correoElectronico") String correoElectronico) {
        log.info("arrived request for forgot-password: {}", correoElectronico);

        ResponseProductDTO response = userService.forgotPassword(correoElectronico);

        log.info("forgot-pasword response: {}", response);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/reset-password")
    public ResponseEntity<ResponseProductDTO> resetPassword(@RequestBody ResetPasswordRequestDTO resetPasswordRequest) {
        log.info("arrived request for reset-password: {}", resetPasswordRequest);

        ResponseProductDTO response = userService.resetPassword(resetPasswordRequest);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/find-by-email")
    public ResponseEntity<ResponseUserDTO> findByEmail(@RequestParam(name = "correoElectronico") String correoElectronico) {
        log.info("arrived request for find-by-email: {}", correoElectronico);

        ResponseUserDTO response = userService.findByEmail(correoElectronico);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/update")
    public ResponseEntity<ResponseUserDTO> update(@RequestBody UserDTO userDTO) {
        log.info("arrived request for update: {}", userDTO.getCorreoElectronico());

        ResponseUserDTO response = userService.update(userDTO);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/update-password")
    public ResponseEntity<ResponseUserDTO> updatePassword(@RequestBody UpdatePasswordRequestDTO updatePasswordRequest) {
        log.info("arrived request for update: {}", updatePasswordRequest.getCorreoElectronico());

        ResponseUserDTO response = userService.updatePassword(updatePasswordRequest);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
