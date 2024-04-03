package umb.v1.informationandproductmanagement.business.service.interfaces;

import umb.v1.informationandproductmanagement.domain.model.dto.ResponseProductListDTO;
import umb.v1.informationandproductmanagement.domain.model.dto.SearchHistoryResponseDTO;
import umb.v1.informationandproductmanagement.domain.model.dto.UserDTO;
import umb.v1.informationandproductmanagement.domain.model.dto.ResponseProductDTO;

import java.util.Map;

public interface IUserService {
    ResponseProductDTO save(UserDTO user);

    SearchHistoryResponseDTO searchHistory(Map<String, String> requestHeaders);

    ResponseProductListDTO viewedProducts(Map<String, String> requestHeaders);

}
