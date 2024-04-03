package umb.v1.informationandproductmanagement.domain.model.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;

import java.sql.Timestamp;

@Getter
@Builder
@Entity
@Table(name = "producto_visto")
public class ViewedProductEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "id_producto_mongodb")
    private String idProductoMongodb;

    @Column(name = "nombre_producto_mongodb")
    private String nombreProductoMongodb;

    @Column(name = "fecha")
    private Timestamp fecha;

    @Column(name = "usuario_id")
    private Long usuarioId;

    public ViewedProductEntity(Long id, String idProductoMongodb, String nombreProductoMongodb,
                               Timestamp fecha, Long usuarioId) {
        this.id = id;
        this.idProductoMongodb = idProductoMongodb;
        this.nombreProductoMongodb = nombreProductoMongodb;
        this.fecha = fecha;
        this.usuarioId = usuarioId;
    }

    public ViewedProductEntity() {
    }
}
