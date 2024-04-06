package umb.v1.informationandproductmanagement.domain.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Builder
@Getter
@ToString
public class AuthRequestDTO {

    @JsonProperty("correoElectronico")
    private String mail;

    @JsonProperty("contrasena")
    private String password;

    public AuthRequestDTO(String mail, String password) {
        this.mail = mail;
        this.password = password;
    }

    public AuthRequestDTO() {
    }
}
