package umb.v1.informationandproductmanagement.business.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import umb.v1.informationandproductmanagement.domain.model.dto.UserDTO;
import umb.v1.informationandproductmanagement.domain.model.dto.ResponseProductDTO;
import umb.v1.informationandproductmanagement.domain.model.entity.UserEntity;
import umb.v1.informationandproductmanagement.domain.repository.UserRepository;

import java.sql.Date;

import static umb.v1.informationandproductmanagement.domain.utility.Constant.CUSTOMER_ROLE_ID;
import static umb.v1.informationandproductmanagement.domain.utility.Constant.OK;
import static umb.v1.informationandproductmanagement.domain.utility.DateUtil.getCurrentDate;

@Service
@Slf4j
public class UserServiceImpl implements IUserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public ResponseProductDTO save(UserDTO user) {
        userRepository.save(UserEntity.builder()
                        .nombreCompleto(user.getNombreCompleto())
                        .correoElectronico(user.getCorreoElectronico())
                        .contrasena(passwordEncoder.encode(user.getContrasena()))
                        .fechaRegistro(new Date(getCurrentDate().getTime()))
                        .rolId(CUSTOMER_ROLE_ID)
                .build());
        return ResponseProductDTO.builder()
                .message(OK)
                .status(200)
                .build();
    }
}
