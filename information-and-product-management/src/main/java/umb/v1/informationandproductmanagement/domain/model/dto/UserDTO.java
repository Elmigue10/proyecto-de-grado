package umb.v1.informationandproductmanagement.domain.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Builder
@Getter
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDTO {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("nombreCompleto")
    private String nombreCompleto;

    @JsonProperty("correoElectronico")
    private String correoElectronico;

    @JsonProperty("contrasena")
    private String contrasena;

    @JsonProperty("fechaRegistro")
    private String fechaRegistro;

    @JsonProperty("fechaActualiza")
    private String fechaActualiza;

    public UserDTO(Long id, String nombreCompleto, String correoElectronico, String contrasena,
                   String fechaRegistro, String fechaActualiza) {
        this.id = id;
        this.nombreCompleto = nombreCompleto;
        this.correoElectronico = correoElectronico;
        this.contrasena = contrasena;
        this.fechaRegistro = fechaRegistro;
        this.fechaActualiza = fechaActualiza;
    }

    public UserDTO() {
    }
}
