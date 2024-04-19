package umb.v1.informationandproductmanagement.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import umb.v1.informationandproductmanagement.domain.model.entity.SearchHistoryEntity;

import java.util.List;

public interface SearchHistoryRepository extends JpaRepository<SearchHistoryEntity, Long> {

    @Query(value = "SELECT * FROM historial_busqueda hb where hb.usuario_id = :userId ORDER BY hb.fecha DESC OFFSET :skip LIMIT :limit", nativeQuery = true)
    List<SearchHistoryEntity> findByUsuarioIdOrderByFechaDesc(@Param("userId") Long userId, @Param("skip") int skip, @Param("limit") int limit);

    @Query(value = "SELECT count(*) FROM historial_busqueda hb where hb.usuario_id = :userId", nativeQuery = true)
    long countByUsuarioIdOrderByFechaDesc(@Param("userId") Long userId);

}
