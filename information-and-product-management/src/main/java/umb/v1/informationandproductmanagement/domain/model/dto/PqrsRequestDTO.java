package umb.v1.informationandproductmanagement.domain.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PqrsRequestDTO {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("descripcionSolicitud")
    private String descripcionSolicitud;

    @JsonProperty("fechaRegistro")
    private String fechaRegistro;

    @JsonProperty("fechaActualiza")
    private String fechaActualiza;

    @JsonProperty("correoElectronico")
    private String correoElectronico;

    @JsonProperty("incidencia")
    private String incidencia;

    @JsonProperty("tipoSolicitud")
    private String tipoSolicitud;

    public PqrsRequestDTO(Long id, String descripcionSolicitud, String fechaRegistro, String fechaActualiza,
                          String correoElectronico, String incidencia, String tipoSolicitud) {
        this.id = id;
        this.descripcionSolicitud = descripcionSolicitud;
        this.fechaRegistro = fechaRegistro;
        this.fechaActualiza = fechaActualiza;
        this.correoElectronico = correoElectronico;
        this.incidencia = incidencia;
        this.tipoSolicitud = tipoSolicitud;
    }

    public PqrsRequestDTO() {
    }
}
