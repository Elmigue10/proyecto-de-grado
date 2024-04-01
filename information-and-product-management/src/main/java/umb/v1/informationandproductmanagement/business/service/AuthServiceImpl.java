package umb.v1.informationandproductmanagement.business.service;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import umb.v1.informationandproductmanagement.domain.exception.ApiException;
import umb.v1.informationandproductmanagement.domain.model.dto.AuthRequestDTO;
import umb.v1.informationandproductmanagement.domain.model.dto.AuthResponseDTO;
import umb.v1.informationandproductmanagement.domain.model.entity.CustomerEntity;
import umb.v1.informationandproductmanagement.domain.repository.AdminRepository;
import umb.v1.informationandproductmanagement.domain.repository.CustomerRepository;

import static umb.v1.informationandproductmanagement.domain.utility.Constant.OK;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthServiceImpl implements IAuthService {

    private final AuthenticationManager authenticationManager;
    private final CustomerRepository customerRepository;
    private final AdminRepository adminRepository;
    private final JwtService jwtService;

    @Override
    public AuthResponseDTO auth(AuthRequestDTO authRequest) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authRequest.getMail(),
                        authRequest.getPassword()
                )
        );

        CustomerEntity user = customerRepository.findByCorreoElectronico(authRequest.getMail())
                .orElseThrow(() -> new ApiException("Usuario no econtrado: " + authRequest.getMail(), 404));

        String token = jwtService.generateToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);

        return AuthResponseDTO.builder()
                .message(OK)
                .status(200)
                .accessToken(token)
                .refreshToken(refreshToken)
                .build();
    }

    @Override
    public AuthResponseDTO refreshToken(HttpServletRequest request) {
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        final String refreshToken;
        final String userEmail;
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return null;
        }
        refreshToken = authHeader.substring(7);
        userEmail = jwtService.extractUsername(refreshToken);
        if (userEmail != null) {
            CustomerEntity user = customerRepository.findByCorreoElectronico(userEmail)
                    .orElseThrow();
            if (jwtService.isTokenValid(refreshToken, user)) {
                String accessToken = jwtService.generateToken(user);
                return AuthResponseDTO.builder()
                        .message(OK)
                        .status(200)
                        .accessToken(accessToken)
                        .refreshToken(refreshToken)
                        .build();
            }
        }
        return null;
    }
}
