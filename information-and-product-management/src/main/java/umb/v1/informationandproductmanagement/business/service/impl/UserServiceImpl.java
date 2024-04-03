package umb.v1.informationandproductmanagement.business.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import umb.v1.informationandproductmanagement.business.client.ProductClient;
import umb.v1.informationandproductmanagement.business.service.interfaces.IJwtService;
import umb.v1.informationandproductmanagement.business.service.interfaces.IUserService;
import umb.v1.informationandproductmanagement.domain.exception.ApiException;
import umb.v1.informationandproductmanagement.domain.model.dto.*;
import umb.v1.informationandproductmanagement.domain.model.entity.SearchHistoryEntity;
import umb.v1.informationandproductmanagement.domain.model.entity.SearchResultEntity;
import umb.v1.informationandproductmanagement.domain.model.entity.UserEntity;
import umb.v1.informationandproductmanagement.domain.model.entity.ViewedProductEntity;
import umb.v1.informationandproductmanagement.domain.repository.SearchHistoryRepository;
import umb.v1.informationandproductmanagement.domain.repository.SearchResultRepository;
import umb.v1.informationandproductmanagement.domain.repository.UserRepository;
import umb.v1.informationandproductmanagement.domain.repository.ViewedProductRepository;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static umb.v1.informationandproductmanagement.domain.utility.Constant.*;

@Service
@Slf4j
public class UserServiceImpl implements IUserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final SearchHistoryRepository searchHistoryRepository;
    private final SearchResultRepository searchResultRepository;
    private final ViewedProductRepository viewedProductRepository;
    private final IJwtService jwtService;
    private final ProductClient productClient;

    public UserServiceImpl(UserRepository userRepository,
                           PasswordEncoder passwordEncoder,
                           SearchHistoryRepository searchHistoryRepository,
                           SearchResultRepository searchResultRepository,
                           ViewedProductRepository viewedProductRepository,
                           IJwtService jwtService,
                           ProductClient productClient) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.searchHistoryRepository = searchHistoryRepository;
        this.searchResultRepository = searchResultRepository;
        this.viewedProductRepository = viewedProductRepository;
        this.jwtService = jwtService;
        this.productClient = productClient;
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
        UserEntity user = findUserByJwtTokenClaims(requestHeaders);
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
        UserEntity user = findUserByJwtTokenClaims(requestHeaders);
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

    private UserEntity findUserByJwtTokenClaims(Map<String, String> requestHeaders){
        String authorizationHeader = requestHeaders.get(AUTHORIZATION_HEADER);
        String token = authorizationHeader.substring(6);
        String email = jwtService.extractUsername(token);
        return userRepository.findByCorreoElectronico(email)
                .orElseThrow(() -> new ApiException("Usuario no econtrado: " + email, 404));
    }

}
