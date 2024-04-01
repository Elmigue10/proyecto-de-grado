package umb.v1.informationandproductmanagement.business.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.FeignException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import umb.v1.informationandproductmanagement.domain.exception.ApiException;
import umb.v1.informationandproductmanagement.domain.model.dto.ResponseProductDTO;

import static umb.v1.informationandproductmanagement.domain.utility.Constant.*;

@RestControllerAdvice
@Slf4j
public class ExceptionHandlerController {

    private final ObjectMapper objectMapper = new ObjectMapper()
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    @ExceptionHandler(value = ApiException.class)
    public ResponseEntity<ResponseProductDTO> apiExceptionHandler(ApiException exception){
        log.info(EXCEPTION_HANDLED, exception.toString());
        return new ResponseEntity<>(ResponseProductDTO.builder()
                .message(exception.getMessage())
                .status(exception.getStatus())
                .build(),
                HttpStatusCode.valueOf(exception.getStatus()));
    }

    @ExceptionHandler(value = FeignException.class)
    public ResponseEntity<ResponseProductDTO> feignExceptionHandler(FeignException exception){
        log.info(EXCEPTION_HANDLED, exception.toString());

        if (exception.status() == 404) {
            try {
                ResponseProductDTO exceptionBody =
                        objectMapper.readValue(exception.contentUTF8(), ResponseProductDTO.class);
                return new ResponseEntity<>(ResponseProductDTO.builder()
                        .message(exceptionBody.getMessage())
                        .status(exceptionBody.getStatus())
                        .build(),
                        HttpStatusCode.valueOf(exception.status()));
            } catch (JsonProcessingException e) {
                throw new ApiException(INTERNAL_SERVER_ERROR, 500);
            }
        }

        return new ResponseEntity<>(ResponseProductDTO.builder()
                .message(exception.getMessage())
                .status(exception.status())
                .build(),
                HttpStatusCode.valueOf(exception.status()));
    }

    @ExceptionHandler(value = BadCredentialsException.class)
    public ResponseEntity<ResponseProductDTO> badCredentialsExceptionHandler(BadCredentialsException exception){
        log.info(EXCEPTION_HANDLED, exception.toString());

        if (exception.getMessage().contains("Bad credentials")){
            return new ResponseEntity<>(ResponseProductDTO.builder()
                    .message(BAD_CREDENTIALS)
                    .status(400)
                    .build(),
                    HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(ResponseProductDTO.builder()
                .message(exception.getMessage())
                .status(500)
                .build(),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(value = DataIntegrityViolationException.class)
    public ResponseEntity<ResponseProductDTO> dataIntegrityViolationExceptionHandler(DataIntegrityViolationException exception){
        log.info(EXCEPTION_HANDLED, exception.toString());

        if (exception.getMessage().contains("duplica")){
            return new ResponseEntity<>(ResponseProductDTO.builder()
                    .message(USER_ALREADY_EXISTS)
                    .status(400)
                    .build(),
                    HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(ResponseProductDTO.builder()
                .message(exception.getMessage())
                .status(500)
                .build(),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
