package umb.v1.informationandproductmanagement.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import umb.v1.informationandproductmanagement.domain.model.entity.PqrsRequestUserEntity;

public interface PqrsRequestUserRepository extends JpaRepository<PqrsRequestUserEntity, Long> {
}
