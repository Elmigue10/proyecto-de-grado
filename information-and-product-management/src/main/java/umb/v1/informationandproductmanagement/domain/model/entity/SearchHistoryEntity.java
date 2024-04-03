package umb.v1.informationandproductmanagement.domain.model.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;

import java.sql.Timestamp;

@Getter
@Builder
@Entity
@Table(name = "historial_busqueda")
public class SearchHistoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "fecha")
    private Timestamp fecha;

    @Column(name = "busqueda")
    private String busqueda;

    @Column(name = "usuario_id")
    private Long usuarioId;

    public SearchHistoryEntity(Long id, Timestamp fecha, String busqueda, Long usuarioId) {
        this.id = id;
        this.fecha = fecha;
        this.busqueda = busqueda;
        this.usuarioId = usuarioId;
    }

    public SearchHistoryEntity() {
    }
}
