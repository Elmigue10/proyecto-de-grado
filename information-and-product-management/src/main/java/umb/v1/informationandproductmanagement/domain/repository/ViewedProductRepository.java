package umb.v1.informationandproductmanagement.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import umb.v1.informationandproductmanagement.domain.model.entity.ViewedProductEntity;

import java.util.List;

public interface ViewedProductRepository extends JpaRepository<ViewedProductEntity, Long> {

    List<ViewedProductEntity> findByUsuarioIdOrderByFechaDesc(Long usuarioId);

    @Query(value = "SELECT pv.*, views.views_count FROM producto_visto pv JOIN (" +
            "SELECT id_producto_mongodb, COUNT(id_producto_mongodb) AS views_count " +
            "FROM producto_visto GROUP BY id_producto_mongodb) AS views ON " +
            "pv.id_producto_mongodb = views.id_producto_mongodb ORDER BY views_count DESC;", nativeQuery = true)
    List<ViewedProductEntity> findByViews();

    int countByUsuarioId(Long id);
}
