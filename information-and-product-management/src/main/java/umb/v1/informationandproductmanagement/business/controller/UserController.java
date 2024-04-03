package umb.v1.informationandproductmanagement.business.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import umb.v1.informationandproductmanagement.business.service.interfaces.IUserService;
import umb.v1.informationandproductmanagement.domain.model.dto.ResponseProductListDTO;
import umb.v1.informationandproductmanagement.domain.model.dto.SearchHistoryResponseDTO;
import umb.v1.informationandproductmanagement.domain.model.dto.UserDTO;
import umb.v1.informationandproductmanagement.domain.model.dto.ResponseProductDTO;

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
    public ResponseEntity<ResponseProductDTO> save(@RequestBody UserDTO user){
        log.info("arrived request for save...");

        ResponseProductDTO response = userService.save(user);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/search-history")
    public ResponseEntity<SearchHistoryResponseDTO> searchHistory(@RequestHeader Map<String, String> requestHeaders){
        log.info("arrived request for search-history");

        SearchHistoryResponseDTO searchHistoryResponse = userService.searchHistory(requestHeaders);

        return new ResponseEntity<>(searchHistoryResponse, HttpStatus.OK);
    }

    @GetMapping("/viewed-products")
    public ResponseEntity<ResponseProductListDTO> viewedProducts(@RequestHeader Map<String, String> requestHeaders){
        log.info("arrived request for viewed-products");

        ResponseProductListDTO responseProducts = userService.viewedProducts(requestHeaders);

        return new ResponseEntity<>(responseProducts, HttpStatus.OK);
    }


}
