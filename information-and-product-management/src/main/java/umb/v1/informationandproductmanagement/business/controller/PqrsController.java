package umb.v1.informationandproductmanagement.business.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import umb.v1.informationandproductmanagement.business.service.interfaces.IPqrsRequestService;
import umb.v1.informationandproductmanagement.domain.model.dto.PqrsRequestDTO;
import umb.v1.informationandproductmanagement.domain.model.dto.PqrsRequestResponseDTO;

@RestController
@Slf4j
@RequestMapping(value = "/pqrs", produces = MediaType.APPLICATION_JSON_VALUE)
public class PqrsController {

    private final IPqrsRequestService pqrsRequestService;

    public PqrsController(IPqrsRequestService pqrsRequestService) {
        this.pqrsRequestService = pqrsRequestService;
    }

    @GetMapping("/find-all")
    public ResponseEntity<PqrsRequestResponseDTO> findAll(@RequestParam(name = "skip") int skip,
                                                          @RequestParam (name = "limit") int limit) {
        log.info("arrived request for find-all");
        PqrsRequestResponseDTO response = pqrsRequestService.findAll(skip, limit);

        log.info("find-all response: {}", response);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/save")
    public ResponseEntity<PqrsRequestResponseDTO> save(@RequestBody PqrsRequestDTO pqrsRequest) {
        log.info("arrived request for save");
        PqrsRequestResponseDTO response = pqrsRequestService.save(pqrsRequest);

        log.info("save response: {}", response);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/update")
    public ResponseEntity<PqrsRequestResponseDTO> update(@RequestBody PqrsRequestDTO pqrsRequest) {
        log.info("arrived request for update");
        PqrsRequestResponseDTO response = pqrsRequestService.update(pqrsRequest);

        log.info("update response: {}", response);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/find-by-email")
    public ResponseEntity<PqrsRequestResponseDTO> findByEmail(@RequestParam("correoElectronico") String correoElectronico,
                                                              @RequestParam(name = "skip") int skip,
                                                              @RequestParam (name = "limit") int limit){
        log.info("arrived request for find-by-email");
        PqrsRequestResponseDTO response = pqrsRequestService.findByEmail(correoElectronico, skip, limit);

        log.info("find-by-email response: {}", response);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
