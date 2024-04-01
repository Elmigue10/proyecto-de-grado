package umb.v1.informationandproductmanagement.business.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import umb.v1.informationandproductmanagement.business.service.ICustomerService;
import umb.v1.informationandproductmanagement.domain.model.dto.CustomerDTO;
import umb.v1.informationandproductmanagement.domain.model.dto.ResponseProductDTO;

@RestController
@Slf4j
@RequestMapping(value = "/customer", produces = MediaType.APPLICATION_JSON_VALUE)
public class CustomerController {

    private final ICustomerService customerService;

    public CustomerController(ICustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping("/save")
    public ResponseEntity<ResponseProductDTO> save(@RequestBody CustomerDTO customer){
        log.info("arrived request for save...");

        ResponseProductDTO response = customerService.save(customer);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
