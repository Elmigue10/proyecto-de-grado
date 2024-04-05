package umb.v1.informationandproductmanagement.business.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import umb.v1.informationandproductmanagement.business.client.ProductClient;
import umb.v1.informationandproductmanagement.business.service.interfaces.IEmailService;
import umb.v1.informationandproductmanagement.business.service.interfaces.IJwtService;
import umb.v1.informationandproductmanagement.business.service.interfaces.IUserService;
import umb.v1.informationandproductmanagement.domain.exception.ApiException;
import umb.v1.informationandproductmanagement.domain.model.dto.*;
import umb.v1.informationandproductmanagement.domain.model.entity.*;
import umb.v1.informationandproductmanagement.domain.repository.*;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static umb.v1.informationandproductmanagement.domain.utility.Constant.*;

@Service
@Slf4j
public class UserServiceImpl implements IUserService {

    private final UserRepository userRepository;
    private final UserWithRoleRepository userWithRoleRepository;
    private final PasswordEncoder passwordEncoder;
    private final SearchHistoryRepository searchHistoryRepository;
    private final SearchResultRepository searchResultRepository;
    private final ViewedProductRepository viewedProductRepository;
    private final IJwtService jwtService;
    private final ProductClient productClient;
    private final IEmailService emailService;

    public UserServiceImpl(UserRepository userRepository,
                           UserWithRoleRepository userWithRoleRepository,
                           PasswordEncoder passwordEncoder,
                           SearchHistoryRepository searchHistoryRepository,
                           SearchResultRepository searchResultRepository,
                           ViewedProductRepository viewedProductRepository,
                           IJwtService jwtService,
                           ProductClient productClient,
                           IEmailService emailService) {
        this.userRepository = userRepository;
        this.userWithRoleRepository = userWithRoleRepository;
        this.passwordEncoder = passwordEncoder;
        this.searchHistoryRepository = searchHistoryRepository;
        this.searchResultRepository = searchResultRepository;
        this.viewedProductRepository = viewedProductRepository;
        this.jwtService = jwtService;
        this.productClient = productClient;
        this.emailService = emailService;
    }

    @Override
    public ResponseProductDTO save(UserDTO user) {
        userRepository.save(UserEntity.builder()
                        .nombreCompleto(user.getNombreCompleto())
                        .correoElectronico(user.getCorreoElectronico())
                        .contrasena(passwordEncoder.encode(user.getContrasena()))
                        .fechaRegistro(new Timestamp(System.currentTimeMillis()))
                        .rolId(CUSTOMER_ROLE_ID)
                .build());
        return ResponseProductDTO.builder()
                .message(OK)
                .status(200)
                .build();
    }

    @Override
    public SearchHistoryResponseDTO searchHistory(Map<String, String> requestHeaders) {
        UserWithRoleEntity user = findUserByJwtTokenClaims(requestHeaders);
        List<SearchHistoryEntity> searches = searchHistoryRepository.findByUsuarioId(user.getId());

        List<SearchHistoryDTO> searchHistory = new ArrayList<>();
        searches.forEach( search -> {

            List<SearchResultEntity> searchResults =
                    searchResultRepository.findByHistorialBusquedaId(search.getId());

            List<String> productIdList = searchResults.stream()
                    .map(SearchResultEntity::getIdProductoMongodb)
                    .toList();

            ResponseProductListDTO responseProducts = productClient.findByIdList(RequestFindByIdListDTO.builder()
                            .idList(productIdList)
                            .skip(0)
                            .limit(productIdList.size())
                    .build());

            searchHistory.add(SearchHistoryDTO.builder()
                            .id(search.getId())
                            .busqueda(search.getBusqueda())
                            .fecha(search.getFecha())
                            .productos(responseProducts.getProducts())
                    .build());
        });

        return SearchHistoryResponseDTO.builder()
                .message(OK)
                .status(200)
                .searchHistory(searchHistory)
                .build();
    }

    @Override
    public ResponseProductListDTO viewedProducts(Map<String, String> requestHeaders) {
        UserWithRoleEntity user = findUserByJwtTokenClaims(requestHeaders);
        List<ViewedProductEntity> viewedProducts = viewedProductRepository.findByUsuarioId(user.getId());

        List<String> productIdList = viewedProducts.stream()
                .map(ViewedProductEntity::getIdProductoMongodb)
                .toList();

        return productClient.findByIdList(RequestFindByIdListDTO.builder()
                .idList(productIdList)
                .skip(0)
                .limit(productIdList.size())
                .build());
    }

    @Override
    public ResponseProductDTO forgotPassword(String correoElectronico) {
        UserWithRoleEntity user = findUserByEmail(correoElectronico);

        String token = jwtService.generateToken(user);

        String link = "http://localhost:3000/reset-password?token=" + token;

        String content = "<p>Hola,</p>"
                + "<p>Has solicitado restablecer tu contraseña.</p>"
                + "<p>Dirigete al siguiente enlace para cambiar tu contraseña:</p>"
                + "<p><a href=\"" + link + "\">Cambiar contraseña</a></p>"
                + "<br>"
                + "<p>Ignora este correo si recuerdas tu contraseña o no fuiste quien realizo esta solicitud.</p>";

        emailService.sendMail(correoElectronico, RESTORE_PASSWORD_REQUEST, content);

        return ResponseProductDTO.builder()
                .message(OK)
                .status(200)
                .build();
    }

    @Override
    public ResponseProductDTO resetPassword(ResetPasswordRequestDTO resetPasswordRequest) {
        return null;
    }

    private UserWithRoleEntity findUserByJwtTokenClaims(Map<String, String> requestHeaders){
        String authorizationHeader = requestHeaders.get(AUTHORIZATION_HEADER);
        String token = authorizationHeader.substring(6);
        String email = jwtService.extractUsername(token);
        return findUserByEmail(email);
    }

    private UserWithRoleEntity findUserByEmail(String email) {
        return userWithRoleRepository.findByCorreoElectronico(email)
                .orElseThrow(() -> new ApiException("Usuario no econtrado: " + email, 404));
    }

}
