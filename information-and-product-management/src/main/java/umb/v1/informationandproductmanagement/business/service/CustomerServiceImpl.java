package umb.v1.informationandproductmanagement.business.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import umb.v1.informationandproductmanagement.domain.model.dto.CustomerDTO;
import umb.v1.informationandproductmanagement.domain.model.dto.ResponseProductDTO;
import umb.v1.informationandproductmanagement.domain.model.entity.CustomerEntity;
import umb.v1.informationandproductmanagement.domain.repository.CustomerRepository;

import static umb.v1.informationandproductmanagement.domain.utility.Constant.OK;

@Service
@Slf4j
public class CustomerServiceImpl implements ICustomerService {

    private final CustomerRepository customerRepository;
    private final PasswordEncoder passwordEncoder;

    public CustomerServiceImpl(CustomerRepository customerRepository, PasswordEncoder passwordEncoder) {
        this.customerRepository = customerRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public ResponseProductDTO save(CustomerDTO customer) {
        customerRepository.save(CustomerEntity.builder()
                        .correoElectronico(customer.getCorreoElectronico())
                        .contrasena(passwordEncoder.encode(customer.getContrasena()))
                .build());
        return ResponseProductDTO.builder()
                .message(OK)
                .status(200)
                .build();
    }
}
