package umb.v1.informationandproductmanagement.domain.model.entity;

import jakarta.persistence.*;
import lombok.Getter;

import java.sql.Timestamp;

@Getter
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

}
