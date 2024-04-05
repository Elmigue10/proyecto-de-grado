package umb.v1.informationandproductmanagement.business.service.interfaces;

import umb.v1.informationandproductmanagement.domain.model.dto.*;

import java.util.Map;

public interface IUserService {
    ResponseProductDTO save(UserDTO user);

    SearchHistoryResponseDTO searchHistory(Map<String, String> requestHeaders);

    ResponseProductListDTO viewedProducts(Map<String, String> requestHeaders);

    ResponseProductDTO forgotPassword(String correoElectronico);

    ResponseProductDTO resetPassword(ResetPasswordRequestDTO resetPasswordRequest);
}
