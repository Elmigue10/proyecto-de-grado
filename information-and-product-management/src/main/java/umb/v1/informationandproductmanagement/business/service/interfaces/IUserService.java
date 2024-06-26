package umb.v1.informationandproductmanagement.business.service.interfaces;

import umb.v1.informationandproductmanagement.domain.model.dto.*;

import java.util.Map;

public interface IUserService {
    ResponseProductDTO save(UserDTO user);

    SearchHistoryResponseDTO searchHistory(Map<String, String> requestHeaders, int skip, int limit);

    ResponseProductListDTO viewedProducts(Map<String, String> requestHeaders, int skip, int limit);

    ResponseProductDTO forgotPassword(String correoElectronico);

    ResponseProductDTO resetPassword(ResetPasswordRequestDTO resetPasswordRequest);

    ResponseUserDTO findByEmail(String correoElectronico);

    ResponseUserDTO update(UserDTO userDTO);

    ResponseUserDTO updatePassword(UpdatePasswordRequestDTO updatePasswordRequest);
}
