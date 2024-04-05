package umb.v1.informationandproductmanagement.domain.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.ToString;

@Builder
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseUserDTO {

    @JsonProperty("message")
    private String message;

    @JsonProperty("status")
    private int status;

    @JsonProperty("user")
    private UserDTO user;

    public ResponseUserDTO(String message, int status, UserDTO user) {
        this.message = message;
        this.status = status;
        this.user = user;
    }

    public ResponseUserDTO() {
    }
}
