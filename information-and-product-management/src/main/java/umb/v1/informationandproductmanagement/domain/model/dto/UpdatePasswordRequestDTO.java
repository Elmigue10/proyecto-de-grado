package umb.v1.informationandproductmanagement.domain.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class UpdatePasswordRequestDTO {

    @JsonProperty("correoElectronico")
    private String correoElectronico;

    @JsonProperty("oldPassword")
    private String oldPassword;

    @JsonProperty("newPassword")
    private String newPassword;

    @JsonProperty("confirmNewPassword")
    private String confirmNewPassword;

}
