package umb.v1.informationandproductmanagement.domain.model.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@Entity
@Table(name = "resultado_busqueda")
public class SearchResultEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "id_producto_mongodb")
    private String idProductoMongodb;

    @Column(name = "historial_busqueda_id")
    private Long historialBusquedaId;

    public SearchResultEntity(Long id, String idProductoMongodb, Long historialBusquedaId) {
        this.id = id;
        this.idProductoMongodb = idProductoMongodb;
        this.historialBusquedaId = historialBusquedaId;
    }

    public SearchResultEntity() {
    }
}
