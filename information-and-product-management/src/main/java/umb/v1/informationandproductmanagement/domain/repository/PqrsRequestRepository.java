package umb.v1.informationandproductmanagement.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import umb.v1.informationandproductmanagement.domain.model.entity.PqrsRequestEntity;

import java.util.List;

public interface PqrsRequestRepository extends JpaRepository<PqrsRequestEntity, Long> {

    @Query(value = "SELECT sp.id, sp.descripcion_solicitud, sp.fecha_registro, sp.fecha_actualiza, " +
            "sp.tipo_solicitud_pqrs_id, sp.incidencia_id FROM solicitud_pqrs sp JOIN solicitud_pqrs_usuario spu " +
            "ON sp.id = spu.solicitud_pqrs_id WHERE spu.usuario_id = :userId " +
            "ORDER BY sp.fecha_registro DESC OFFSET :skip LIMIT :limit", nativeQuery = true)
    List<PqrsRequestEntity> findByUserId(@Param("userId") Long userId, @Param("skip") int skip, @Param("limit") int limit);

    @Query(value = "SELECT * FROM solicitud_pqrs ORDER BY fecha_registro DESC OFFSET :skip LIMIT :limit", nativeQuery = true)
    List<PqrsRequestEntity> findAll(@Param("skip") int skip, @Param("limit") int limit);

    @Query(value = "SELECT count(*) FROM solicitud_pqrs sp JOIN solicitud_pqrs_usuario spu " +
            "ON sp.id = spu.solicitud_pqrs_id WHERE spu.usuario_id = :userId", nativeQuery = true)
    long countByUserId(@Param("userId") Long userId);
}
