package umb.v1.informationandproductmanagement.domain.exception;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class ApiException extends RuntimeException {

    private final int status;

    public ApiException(String message, int status) {
        super(message);
        this.status = status;
    }

}
