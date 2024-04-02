package umb.v1.informationandproductmanagement.domain.model.entity;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Table(name = "rol")
public class RoleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Getter
    @Column(name = "rol", nullable = false)
    private String rol;

}
