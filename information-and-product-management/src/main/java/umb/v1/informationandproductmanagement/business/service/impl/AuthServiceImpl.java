package umb.v1.informationandproductmanagement.business.service.impl;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import umb.v1.informationandproductmanagement.business.service.interfaces.IAuthService;
import umb.v1.informationandproductmanagement.business.service.interfaces.IJwtService;
import umb.v1.informationandproductmanagement.domain.exception.ApiException;
import umb.v1.informationandproductmanagement.domain.model.dto.AuthRequestDTO;
import umb.v1.informationandproductmanagement.domain.model.dto.AuthResponseDTO;
import umb.v1.informationandproductmanagement.domain.model.entity.UserWithRoleEntity;
import umb.v1.informationandproductmanagement.domain.repository.UserWithRoleRepository;

import static umb.v1.informationandproductmanagement.domain.utility.Constant.OK;

@Service
@Slf4j
public class AuthServiceImpl implements IAuthService {

    private final AuthenticationManager authenticationManager;
    private final UserWithRoleRepository userRepository;
    private final IJwtService jwtService;

    public AuthServiceImpl(AuthenticationManager authenticationManager,
                           UserWithRoleRepository userRepository,
                           IJwtService jwtService) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.jwtService = jwtService;
    }

    @Override
    public AuthResponseDTO auth(AuthRequestDTO authRequest) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authRequest.getMail(),
                        authRequest.getPassword()
                )
        );

        UserWithRoleEntity user = userRepository.findByCorreoElectronico(authRequest.getMail())
                .orElseThrow(() -> new ApiException("Usuario no econtrado: " + authRequest.getMail(), 404));

        String token = jwtService.generateToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);

        return AuthResponseDTO.builder()
                .message(OK)
                .status(200)
                .accessToken(token)
                .refreshToken(refreshToken)
                .rol(user.getRole().getRol())
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
            UserWithRoleEntity user = userRepository.findByCorreoElectronico(userEmail)
                    .orElseThrow();
            if (jwtService.isTokenValid(refreshToken, user)) {
                String accessToken = jwtService.generateToken(user);
                return AuthResponseDTO.builder()
                        .message(OK)
                        .status(200)
                        .accessToken(accessToken)
                        .refreshToken(refreshToken)
                        .rol(user.getRole().getRol())
                        .build();
            }
        }
        return null;
    }
}
