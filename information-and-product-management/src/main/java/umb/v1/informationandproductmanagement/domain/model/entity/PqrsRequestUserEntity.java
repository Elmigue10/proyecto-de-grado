package umb.v1.informationandproductmanagement.domain.model.entity;

import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Entity
@Table(name = "solicitud_pqrs_usuario")
public class PqrsRequestUserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "usuario_id")
    private Long usuarioId;

    @Column(name = "solicitud_pqrs_id")
    private Long solicitudPqrsId;

}
