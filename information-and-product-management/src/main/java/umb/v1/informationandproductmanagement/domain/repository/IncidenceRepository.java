package umb.v1.informationandproductmanagement.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import umb.v1.informationandproductmanagement.domain.model.entity.IncidenceEntity;

public interface IncidenceRepository extends JpaRepository<IncidenceEntity, Long> {
}
