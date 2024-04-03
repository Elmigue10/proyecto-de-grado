package umb.v1.informationandproductmanagement.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import umb.v1.informationandproductmanagement.domain.model.entity.SearchHistoryEntity;

import java.util.List;

public interface SearchHistoryRepository extends JpaRepository<SearchHistoryEntity, Long> {

    List<SearchHistoryEntity> findByUsuarioId(Long usuarioId);

}
