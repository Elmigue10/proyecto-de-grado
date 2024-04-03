package umb.v1.informationandproductmanagement.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import umb.v1.informationandproductmanagement.domain.model.entity.UserWithRoleEntity;

import java.util.Optional;

public interface UserWithRoleRepository extends JpaRepository<UserWithRoleEntity, Long> {

    Optional<UserWithRoleEntity> findByCorreoElectronico(String correoElectronico);

}
