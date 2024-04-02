package umb.v1.informationandproductmanagement.business.service;

import umb.v1.informationandproductmanagement.domain.model.dto.UserDTO;
import umb.v1.informationandproductmanagement.domain.model.dto.ResponseProductDTO;

public interface IUserService {
    ResponseProductDTO save(UserDTO user);
}
