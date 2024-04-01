package umb.v1.informationandproductmanagement.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import umb.v1.informationandproductmanagement.domain.model.entity.AdminEntity;

import java.util.Optional;

public interface AdminRepository extends JpaRepository<AdminEntity, Long> {

    Optional<AdminEntity> findByCorreoElectronico(String correoElectronico);

}
