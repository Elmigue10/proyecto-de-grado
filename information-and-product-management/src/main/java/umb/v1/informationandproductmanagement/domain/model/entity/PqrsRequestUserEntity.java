package umb.v1.informationandproductmanagement.domain.model.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
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

    public PqrsRequestUserEntity(Long id, Long usuarioId, Long solicitudPqrsId) {
        this.id = id;
        this.usuarioId = usuarioId;
        this.solicitudPqrsId = solicitudPqrsId;
    }

    public PqrsRequestUserEntity() {
    }
}
