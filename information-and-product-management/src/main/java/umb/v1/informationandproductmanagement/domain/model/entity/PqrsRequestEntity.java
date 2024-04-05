package umb.v1.informationandproductmanagement.domain.model.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Builder
@Getter
@Setter
@Entity
@Table(name = "solicitud_pqrs")
public class PqrsRequestEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "descripcion_solicitud")
    private String descripcionSolicitud;

    @Column(name = "fecha_registro")
    private Timestamp fechaRegistro;

    @Column(name = "fecha_actualiza")
    private Timestamp fechaActualiza;

    @Column(name = "tipo_solicitud_pqrs_id")
    private Long tipoSolicitudPqrsId;

    @Column(name = "incidencia_id")
    private Long incidenciaId;

    public PqrsRequestEntity(Long id, String descripcionSolicitud, Timestamp fechaRegistro, Timestamp fechaActualiza,
                             Long tipoSolicitudPqrsId, Long incidenciaId) {
        this.id = id;
        this.descripcionSolicitud = descripcionSolicitud;
        this.fechaRegistro = fechaRegistro;
        this.fechaActualiza = fechaActualiza;
        this.tipoSolicitudPqrsId = tipoSolicitudPqrsId;
        this.incidenciaId = incidenciaId;
    }

    public PqrsRequestEntity() {
    }
}
