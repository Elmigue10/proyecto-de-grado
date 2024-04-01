package umb.v1.informationandproductmanagement.business.service;

import umb.v1.informationandproductmanagement.domain.model.dto.CustomerDTO;
import umb.v1.informationandproductmanagement.domain.model.dto.ResponseProductDTO;

public interface ICustomerService {
    ResponseProductDTO save(CustomerDTO customer);
}
