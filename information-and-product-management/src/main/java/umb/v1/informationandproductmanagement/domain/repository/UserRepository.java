package umb.v1.informationandproductmanagement.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import umb.v1.informationandproductmanagement.domain.model.entity.UserEntity;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity> findByCorreoElectronico(String correoElectronico);

}
