package umb.v1.informationandproductmanagement.business.service.impl;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import umb.v1.informationandproductmanagement.business.client.ProductClient;
import umb.v1.informationandproductmanagement.business.service.interfaces.IJwtService;
import umb.v1.informationandproductmanagement.business.service.interfaces.IProductService;
import umb.v1.informationandproductmanagement.domain.exception.ApiException;
import umb.v1.informationandproductmanagement.domain.model.dto.RequestFindByIdDTO;
import umb.v1.informationandproductmanagement.domain.model.dto.RequestFindByNameDTO;
import umb.v1.informationandproductmanagement.domain.model.dto.ResponseProductDTO;
import umb.v1.informationandproductmanagement.domain.model.dto.ResponseProductListDTO;
import umb.v1.informationandproductmanagement.domain.model.entity.SearchHistoryEntity;
import umb.v1.informationandproductmanagement.domain.model.entity.SearchResultEntity;
import umb.v1.informationandproductmanagement.domain.model.entity.UserWithRoleEntity;
import umb.v1.informationandproductmanagement.domain.model.entity.ViewedProductEntity;
import umb.v1.informationandproductmanagement.domain.repository.SearchHistoryRepository;
import umb.v1.informationandproductmanagement.domain.repository.SearchResultRepository;
import umb.v1.informationandproductmanagement.domain.repository.UserWithRoleRepository;
import umb.v1.informationandproductmanagement.domain.repository.ViewedProductRepository;

import java.sql.Timestamp;
import java.util.Map;

import static umb.v1.informationandproductmanagement.domain.utility.Constant.AUTHORIZATION_HEADER;

@Service
@Slf4j
public class ProductServiceImpl implements IProductService {

    private final ProductClient productClient;
    private final ViewedProductRepository viewedProductRepository;
    private final SearchHistoryRepository searchHistoryRepository;
    private final SearchResultRepository searchResultRepository;
    private final UserWithRoleRepository userRepository;
    private final IJwtService jwtService;
    private final ObjectMapper objectMapper;

    public ProductServiceImpl(ProductClient productClient,
                              ViewedProductRepository viewedProductRepository,
                              SearchHistoryRepository searchHistoryRepository,
                              SearchResultRepository searchResultRepository,
                              UserWithRoleRepository userRepository,
                              IJwtService jwtService) {
        this.productClient = productClient;
        this.viewedProductRepository = viewedProductRepository;
        this.searchHistoryRepository = searchHistoryRepository;
        this.searchResultRepository = searchResultRepository;
        this.userRepository = userRepository;
        this.jwtService = jwtService;
        this.objectMapper = new ObjectMapper()
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    @Override
    public ResponseProductListDTO findAll(int skip, int limit) {
        return productClient.findAll(skip, limit);
    }

    @Override
    public ResponseProductDTO findById(RequestFindByIdDTO request, Map<String, String> requestHeaders) {
        ResponseProductDTO responseProduct = productClient.findById(request);

        UserWithRoleEntity user = findUserByJwtTokenClaims(requestHeaders);

        viewedProductRepository.save(ViewedProductEntity.builder()
                        .idProductoMongodb(responseProduct.getProduct().getId())
                        .nombreProductoMongodb(responseProduct.getProduct().getNombre())
                        .fecha(new Timestamp(System.currentTimeMillis()))
                        .usuarioId(user.getId())
                .build());

        return responseProduct;
    }

    @Override
    public ResponseProductListDTO findByBrand(String brandName, int skip, int limit) {
        return productClient.findByBrand(brandName, skip, limit);
    }

    @Override
    public ResponseProductListDTO findByCategory(String categoryName, int skip, int limit) {
        return productClient.findByCategory(categoryName, skip, limit);
    }

    @Override
    public ResponseProductListDTO findByPlatform(String platformName, int skip, int limit) {
        return productClient.findByPlatform(platformName, skip, limit);
    }

    @Override
    public ResponseProductListDTO findByName(RequestFindByNameDTO request, Map<String, String> requestHeaders) {
        ResponseProductListDTO responseProducts = productClient.findByName(request);

        UserWithRoleEntity user = findUserByJwtTokenClaims(requestHeaders);

        SearchHistoryEntity searchHistory = searchHistoryRepository.save(SearchHistoryEntity.builder()
                .fecha(new Timestamp(System.currentTimeMillis()))
                .busqueda(request.getProductName())
                .usuarioId(user.getId())
                .build());

        responseProducts.getProducts().forEach( product -> searchResultRepository.save(SearchResultEntity.builder()
                .historialBusquedaId(searchHistory.getId())
                .idProductoMongodb(product.getId())
                .build()));


        return responseProducts;
    }

    @Override
    public ResponseProductListDTO findByPriceRange(String minPrice, String maxPrice, int skip, int limit) {
        return productClient.findByPriceRange(minPrice, maxPrice, skip, limit);
    }

    @Override
    public ResponseProductListDTO findByBrandAndCategory(String brandName, String categoryName, int skip, int limit) {
        return productClient.findByBrandAndCategory(brandName, categoryName, skip, limit);
    }

    @Override
    public ResponseProductListDTO findByPlatformAndCategory(String platformName, String categoryName, int skip, int limit) {
        return productClient.findByPlatformAndCategory(platformName, categoryName, skip, limit);
    }

    @Override
    public ResponseProductListDTO findByBrandCategoryAndPlatform(String brandName, String categoryName, String platformName, int skip, int limit) {
        return productClient.findByBrandCategoryAndPlatform(brandName, categoryName, platformName, skip, limit);
    }

    @Override
    public ResponseProductListDTO findByRamMemory(String ramMemory, String categoryName, int skip, int limit) {
        return productClient.findByRamMemory(ramMemory, categoryName, skip, limit);
    }

    private UserWithRoleEntity findUserByJwtTokenClaims(Map<String, String> requestHeaders){
        String authorizationHeader = requestHeaders.get(AUTHORIZATION_HEADER);
        String token = authorizationHeader.substring(6);
        String email = jwtService.extractUsername(token);
        return userRepository.findByCorreoElectronico(email)
                .orElseThrow(() -> new ApiException("Usuario no econtrado: " + email, 404));
    }
}
