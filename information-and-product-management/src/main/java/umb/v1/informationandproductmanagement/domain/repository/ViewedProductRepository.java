package umb.v1.informationandproductmanagement.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import umb.v1.informationandproductmanagement.domain.model.entity.ViewedProductEntity;

import java.util.List;

public interface ViewedProductRepository extends JpaRepository<ViewedProductEntity, Long> {

    List<ViewedProductEntity> findByUsuarioId(Long usuarioId);

}
