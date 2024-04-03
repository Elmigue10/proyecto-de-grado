package umb.v1.informationandproductmanagement.business.service.interfaces;

import jakarta.servlet.http.HttpServletRequest;
import umb.v1.informationandproductmanagement.domain.model.dto.AuthRequestDTO;
import umb.v1.informationandproductmanagement.domain.model.dto.AuthResponseDTO;

public interface IAuthService {
    AuthResponseDTO auth(AuthRequestDTO authRequest);

    AuthResponseDTO refreshToken(HttpServletRequest request);
}
