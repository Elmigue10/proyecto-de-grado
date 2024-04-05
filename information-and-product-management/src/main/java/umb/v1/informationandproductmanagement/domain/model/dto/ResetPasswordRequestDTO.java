package umb.v1.informationandproductmanagement.domain.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class ResetPasswordRequestDTO {

    @JsonProperty("correoElectronico")
    private String correoElectronico;

    @JsonProperty("contrasena")
    private String contrasena;

    @JsonProperty("confirmContrasena")
    private String confirmContrasena;

    @JsonProperty("token")
    private String token;

}
