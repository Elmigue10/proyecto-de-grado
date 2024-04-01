package umb.v1.informationandproductmanagement.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import umb.v1.informationandproductmanagement.domain.model.entity.CustomerEntity;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<CustomerEntity, Long> {

    Optional<CustomerEntity> findByCorreoElectronico(String correoElectronico);

}
