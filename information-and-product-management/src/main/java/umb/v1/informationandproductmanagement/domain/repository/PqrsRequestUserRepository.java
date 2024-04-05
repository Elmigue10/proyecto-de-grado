package umb.v1.informationandproductmanagement.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import umb.v1.informationandproductmanagement.domain.model.entity.PqrsRequestUserEntity;

import java.util.List;

public interface PqrsRequestUserRepository extends JpaRepository<PqrsRequestUserEntity, Long> {

    List<PqrsRequestUserEntity> findBySolicitudPqrsId(Long solicitudPqrsId);

}
