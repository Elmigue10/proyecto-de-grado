package umb.v1.informationandproductmanagement.business.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import umb.v1.informationandproductmanagement.business.client.ProductClient;
import umb.v1.informationandproductmanagement.business.service.interfaces.IEmailService;
import umb.v1.informationandproductmanagement.business.service.interfaces.IJwtService;
import umb.v1.informationandproductmanagement.business.service.interfaces.IPqrsRequestService;
import umb.v1.informationandproductmanagement.business.service.interfaces.IUserService;
import umb.v1.informationandproductmanagement.domain.exception.ApiException;
import umb.v1.informationandproductmanagement.domain.model.dto.*;
import umb.v1.informationandproductmanagement.domain.model.entity.*;
import umb.v1.informationandproductmanagement.domain.repository.*;

import java.sql.Timestamp;
import java.util.*;
import java.util.stream.Collectors;

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
    private final IPqrsRequestService pqrsRequestService;
    private final UserDetailsService userDetailsService;
    private final AuthServiceImpl authService;


    public UserServiceImpl(UserRepository userRepository,
                           UserWithRoleRepository userWithRoleRepository,
                           PasswordEncoder passwordEncoder,
                           SearchHistoryRepository searchHistoryRepository,
                           SearchResultRepository searchResultRepository,
                           ViewedProductRepository viewedProductRepository,
                           IJwtService jwtService,
                           ProductClient productClient,
                           IEmailService emailService,
                           IPqrsRequestService pqrsRequestService,
                           UserDetailsService userDetailsService,
                           AuthServiceImpl authService) {
        this.userRepository = userRepository;
        this.userWithRoleRepository = userWithRoleRepository;
        this.passwordEncoder = passwordEncoder;
        this.searchHistoryRepository = searchHistoryRepository;
        this.searchResultRepository = searchResultRepository;
        this.viewedProductRepository = viewedProductRepository;
        this.jwtService = jwtService;
        this.productClient = productClient;
        this.emailService = emailService;
        this.pqrsRequestService = pqrsRequestService;
        this.userDetailsService = userDetailsService;
        this.authService = authService;
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
    public SearchHistoryResponseDTO searchHistory(Map<String, String> requestHeaders, int skip, int limit) {
        UserWithRoleEntity user = findUserByJwtTokenClaims(requestHeaders);
        List<SearchHistoryEntity> searches =
                searchHistoryRepository.findByUsuarioIdOrderByFechaDesc(user.getId(), skip, limit);
        long searchesQuantity = searchHistoryRepository.countByUsuarioIdOrderByFechaDesc(user.getId());

        List<SearchHistoryDTO> searchHistory = new ArrayList<>();
        searches.forEach(search -> {

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
                .total(searchesQuantity)
                .build();
    }

    @Override
    public ResponseProductListDTO viewedProducts(Map<String, String> requestHeaders, int skip, int limit) {
        UserWithRoleEntity user = findUserByJwtTokenClaims(requestHeaders);
        List<ViewedProductEntity> viewedProducts = viewedProductRepository.findByUsuarioIdOrderByFechaDesc(user.getId());

        List<String> productIdList = viewedProducts.stream()
                .map(ViewedProductEntity::getIdProductoMongodb).distinct().toList();

        Map<String, Integer> indexId = new HashMap<>();
        for (int i = 0; i < productIdList.size(); i++) {
            indexId.put(productIdList.get(i), i);
        }

        ResponseProductListDTO responseProducts = productClient.findByIdList(RequestFindByIdListDTO.builder()
                .idList(productIdList)
                .skip(0)
                .limit(productIdList.size())
                .build());

        List<ProductDTO> products = responseProducts.getProducts();
        products.sort(Comparator.comparingInt(p -> indexId.getOrDefault(p.getId(), Integer.MAX_VALUE)));

        int startIndex = Math.min(skip, products.size());
        int endIndex = Math.min(startIndex + limit, products.size());

        List<ProductDTO> paginateProducts = products.subList(startIndex, endIndex);

        responseProducts.setProducts(paginateProducts);
        responseProducts.setTotalProductos(products.size());

        return responseProducts;
    }

    @Override
    public ResponseProductDTO forgotPassword(String correoElectronico) {
        UserWithRoleEntity user = findUserByEmail(correoElectronico);

        PqrsRequestEntity pqrsRequest = pqrsRequestService.savePqrsRequestEntity(PqrsRequestEntity.builder()
                        .descripcionSolicitud(CAMBIO_CONTRAENA)
                        .fechaRegistro(new Timestamp(System.currentTimeMillis()))
                        .tipoSolicitudPqrsId(1L)
                        .incidenciaId(2L)
                        .build(),
                PqrsRequestUserEntity.builder()
                        .usuarioId(user.getId())
                        .build(),
                PqrsRequestUserEntity.builder()
                        .usuarioId(1L)
                        .build());

        String token = jwtService.generateToken(user);

        String content = buildContent(token, pqrsRequest);

        emailService.sendMail(correoElectronico, RESTORE_PASSWORD_REQUEST, content);

        return ResponseProductDTO.builder()
                .message(OK)
                .status(200)
                .build();
    }

    private static String buildContent(String token, PqrsRequestEntity pqrsRequest) {
        String link = "http://localhost:3000/reset-password?token=" +
                token +
                "&pqrsId=" +
                pqrsRequest.getId();

        return "<p>Hola,</p>"
                + "<p>Has solicitado restablecer tu contraseña.</p>"
                + "<p>Dirigete al siguiente enlace para cambiar tu contraseña:</p>"
                + "<p><a href=\"" + link + "\">Cambiar contraseña</a></p>"
                + "<br>"
                + "<p>Ignora este correo si recuerdas tu contraseña o no fuiste quien realizo esta solicitud.</p>";
    }

    @Override
    public ResponseProductDTO resetPassword(ResetPasswordRequestDTO resetPasswordRequest) {

        String correoElectronicoFromToken = jwtService.extractUsername(resetPasswordRequest.getToken());
        if (!Objects.equals(correoElectronicoFromToken, resetPasswordRequest.getCorreoElectronico())) {
            throw new ApiException("Correo electronico invalido", 400);
        }

        if (!Objects.equals(resetPasswordRequest.getContrasena(), resetPasswordRequest.getConfirmContrasena())) {
            throw new ApiException("Las contraseñas no coinciden", 400);
        }

        UserDetails userDetails = userDetailsService.loadUserByUsername(resetPasswordRequest.getCorreoElectronico());

        if (!jwtService.isTokenValid(resetPasswordRequest.getToken(), userDetails)) {
            throw new ApiException("El token no es valido", 400);
        }

        Optional<UserEntity> optionalUser =
                userRepository.findByCorreoElectronico(resetPasswordRequest.getCorreoElectronico());

        if (optionalUser.isPresent()) {
            UserEntity user = optionalUser.get();

            user.setContrasena(passwordEncoder.encode(resetPasswordRequest.getContrasena()));

            userRepository.save(user);

            pqrsRequestService.updatePqrsRequestUpdatePassword(resetPasswordRequest.getPqrsId());
        } else {
            throw new ApiException("Usuario no encontrado", 404);
        }

        return ResponseProductDTO.builder()
                .message(OK)
                .status(200)
                .build();
    }

    @Override
    public ResponseUserDTO findByEmail(String correoElectronico) {
        Optional<UserEntity> optionalUserEntity = userRepository.findByCorreoElectronico(correoElectronico);

        if (optionalUserEntity.isPresent()) {
            UserEntity userEntity = optionalUserEntity.get();
            return ResponseUserDTO.builder()
                    .message(OK)
                    .status(200)
                    .user(UserDTO.builder()
                            .id(userEntity.getId())
                            .nombreCompleto(userEntity.getNombreCompleto())
                            .correoElectronico(userEntity.getCorreoElectronico())
                            .fechaRegistro(userEntity.getFechaRegistro().toString())
                            .fechaActualiza(userEntity.getFechaActualiza() == null ?
                                    null :
                                    userEntity.getFechaActualiza().toString())
                            .build())
                    .build();
        } else {
            throw new ApiException("Usuario no encontrado", 404);
        }
    }

    @Override
    public ResponseUserDTO update(UserDTO user) {

        if (user.getId() == null || user.getId() == 0) {
            throw new ApiException("El campo 'id' es requerido", 400);
        }

        Optional<UserEntity> optionalUserEntity = userRepository.findById(user.getId());

        if (optionalUserEntity.isPresent()) {
            UserEntity userEntity = optionalUserEntity.get();

            if (!user.getNombreCompleto().equals(userEntity.getNombreCompleto())){
                userEntity.setNombreCompleto(user.getNombreCompleto());
            }

            if (!user.getCorreoElectronico().equals(userEntity.getCorreoElectronico())){
                userEntity.setCorreoElectronico(user.getCorreoElectronico());
            }

            userEntity.setFechaActualiza(new Timestamp(System.currentTimeMillis()));

            userRepository.save(userEntity);

            UserWithRoleEntity userWithRole = findUserByEmail(userEntity.getCorreoElectronico());
            String token = jwtService.generateToken(userWithRole);

            return ResponseUserDTO.builder()
                    .message(OK)
                    .status(200)
                    .user(user)
                    .token(token)
                    .build();
        } else {
            throw new ApiException("Usuario no encontrado", 404);
        }
    }

    @Override
    public ResponseUserDTO updatePassword(UpdatePasswordRequestDTO updatePasswordRequest) {
        if (!Objects.equals(updatePasswordRequest.getNewPassword(), updatePasswordRequest.getConfirmNewPassword())) {
            throw new ApiException("Las contraseñas no coinciden", 400);
        }

        Optional<UserEntity> optionalUserEntity =
                userRepository.findByCorreoElectronico(updatePasswordRequest.getCorreoElectronico());

        if (optionalUserEntity.isPresent()) {
            UserEntity userEntity = optionalUserEntity.get();

            authService.auth(AuthRequestDTO.builder()
                            .mail(updatePasswordRequest.getCorreoElectronico())
                            .password(updatePasswordRequest.getOldPassword())
                    .build());

            userEntity.setContrasena(passwordEncoder.encode(updatePasswordRequest.getNewPassword()));

            userRepository.save(userEntity);

            return ResponseUserDTO.builder()
                    .message(OK)
                    .status(200)
                    .build();
        } else {
            throw new ApiException("Usuario no encontrado", 404);
        }
    }

    private UserWithRoleEntity findUserByJwtTokenClaims(Map<String, String> requestHeaders) {
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
