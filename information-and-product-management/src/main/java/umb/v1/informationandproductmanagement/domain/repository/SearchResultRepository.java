package umb.v1.informationandproductmanagement.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import umb.v1.informationandproductmanagement.domain.model.entity.SearchResultEntity;

import java.util.List;

public interface SearchResultRepository extends JpaRepository<SearchResultEntity, Long> {

    List<SearchResultEntity> findByHistorialBusquedaId(Long historialBusquedaId);

}
