package umb.v1.informationandproductmanagement.domain.model.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;

import java.sql.Timestamp;

@Builder
@Getter
@Entity
@Table(name = "usuario")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "nombre_completo")
    private String nombreCompleto;

    @Column(name = "correo_electronico")
    private String correoElectronico;

    @Column(name = "contrasena")
    private String contrasena;

    @Column(name = "fecha_registro")
    private Timestamp fechaRegistro;

    @Column(name = "fecha_actualiza")
    private Timestamp fechaActualiza;

    @Column(name = "rol_id")
    private int rolId;

    public UserEntity(Long id, String nombreCompleto, String correoElectronico, String contrasena,
                      Timestamp fechaRegistro, Timestamp fechaActualiza, int rolId) {
        this.id = id;
        this.nombreCompleto = nombreCompleto;
        this.correoElectronico = correoElectronico;
        this.contrasena = contrasena;
        this.fechaRegistro = fechaRegistro;
        this.fechaActualiza = fechaActualiza;
        this.rolId = rolId;
    }

    public UserEntity() {
    }
}
