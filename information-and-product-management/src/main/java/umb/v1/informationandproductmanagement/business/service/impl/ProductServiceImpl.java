package umb.v1.informationandproductmanagement.business.service.impl;

import feign.FeignException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import umb.v1.informationandproductmanagement.business.client.ProductClient;
import umb.v1.informationandproductmanagement.business.service.interfaces.IEmailService;
import umb.v1.informationandproductmanagement.business.service.interfaces.IJwtService;
import umb.v1.informationandproductmanagement.business.service.interfaces.IProductService;
import umb.v1.informationandproductmanagement.domain.exception.ApiException;
import umb.v1.informationandproductmanagement.domain.model.dto.*;
import umb.v1.informationandproductmanagement.domain.model.entity.SearchHistoryEntity;
import umb.v1.informationandproductmanagement.domain.model.entity.SearchResultEntity;
import umb.v1.informationandproductmanagement.domain.model.entity.UserWithRoleEntity;
import umb.v1.informationandproductmanagement.domain.model.entity.ViewedProductEntity;
import umb.v1.informationandproductmanagement.domain.repository.SearchHistoryRepository;
import umb.v1.informationandproductmanagement.domain.repository.SearchResultRepository;
import umb.v1.informationandproductmanagement.domain.repository.UserWithRoleRepository;
import umb.v1.informationandproductmanagement.domain.repository.ViewedProductRepository;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import static umb.v1.informationandproductmanagement.domain.utility.Constant.*;

@Service
@Slf4j
public class ProductServiceImpl implements IProductService {

    private final ProductClient productClient;
    private final ViewedProductRepository viewedProductRepository;
    private final SearchHistoryRepository searchHistoryRepository;
    private final SearchResultRepository searchResultRepository;
    private final UserWithRoleRepository userRepository;
    private final IJwtService jwtService;
    private final IEmailService emailService;

    public ProductServiceImpl(ProductClient productClient,
                              ViewedProductRepository viewedProductRepository,
                              SearchHistoryRepository searchHistoryRepository,
                              SearchResultRepository searchResultRepository,
                              UserWithRoleRepository userRepository,
                              IJwtService jwtService, IEmailService emailService) {
        this.productClient = productClient;
        this.viewedProductRepository = viewedProductRepository;
        this.searchHistoryRepository = searchHistoryRepository;
        this.searchResultRepository = searchResultRepository;
        this.userRepository = userRepository;
        this.jwtService = jwtService;
        this.emailService = emailService;
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
    public ResponseProductListDTO findByPriceRange(String minPrice, String maxPrice, String categoryName, int skip, int limit) {
        return productClient.findByPriceRange(minPrice, maxPrice, categoryName, skip, limit);
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

    @Override
    public ResponseProductListDTO findMostViewed() {
        List<ViewedProductEntity> viewedProducts = viewedProductRepository.findByViews();

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
    public WebScraperResponseDTO webScraperBot(WebScraperRequestDTO webScraperRequest) {

        WebScraperResponseDTO webScraperResponse;
        try {
            webScraperResponse = productClient.webScraperBot(webScraperRequest);
        } catch (FeignException e) {
            emailService.sendMail(ADMIN, PRODUCTS_WEB_SEARCH_RESULT, buildErrorWebScraperEmailContent());
            return null;
        }
        String content = buildWebScraperEmailContent(webScraperRequest, webScraperResponse);

        emailService.sendMail(ADMIN_EMAIL, PRODUCTS_WEB_SEARCH_RESULT, content);

        return webScraperResponse;
    }

    private UserWithRoleEntity findUserByJwtTokenClaims(Map<String, String> requestHeaders){
        String authorizationHeader = requestHeaders.get(AUTHORIZATION_HEADER);
        String token = authorizationHeader.substring(6);
        String email = jwtService.extractUsername(token);
        return userRepository.findByCorreoElectronico(email)
                .orElseThrow(() -> new ApiException("Usuario no econtrado: " + email, 404));
    }

    private String buildWebScraperEmailContent(WebScraperRequestDTO webScraperRequest,
                                               WebScraperResponseDTO webScraperResponse) {
        StringBuilder content = new StringBuilder("<p><strong>Resultado de la búsqueda:</strong></p>");

        if (webScraperRequest.isKtronixPhones() ||
                webScraperRequest.isKtronixComputers() ||
                webScraperRequest.isKtronixMonitors() ||
                webScraperRequest.isKtronixTablets()) {
            content.append("<p><strong>Ktronix:</strong></p>");

            if (webScraperRequest.isKtronixPhones()) {
                content.append("<p>");
                content.append(SMARTPHONES);
                content.append(webScraperResponse.getKtronixWebScraperPhonesTotal());
                content.append("</p>");
            }

            if (webScraperRequest.isKtronixComputers()) {
                content.append("<p>");
                content.append(COMPUTERS);
                content.append(webScraperResponse.getKtronixWebScraperComputerTotal());
                content.append("</p>");
            }

            if (webScraperRequest.isKtronixMonitors()) {
                content.append("<p>");
                content.append(MONITORS);
                content.append(webScraperResponse.getKtronixWebScraperMonitorTotal());
                content.append("</p>");
            }

            if (webScraperRequest.isKtronixTablets()) {
                content.append("<p>");
                content.append(TABLETS);
                content.append(webScraperResponse.getKtronixWebScraperTabletTotal());
                content.append("</p>");
            }
        }

        if (webScraperRequest.isExitoPhones() ||
                webScraperRequest.isExitoComputers() ||
                webScraperRequest.isExitoMonitors() ||
                webScraperRequest.isExitoTablets()) {
            content.append("<p><strong>Exito:</strong></p>");

            if (webScraperRequest.isExitoPhones()) {
                content.append("<p>");
                content.append(SMARTPHONES);
                content.append(webScraperResponse.getExitoWebScraperPhonesTotal());
                content.append("</p>");
            }

            if (webScraperRequest.isExitoComputers()) {
                content.append("<p>");
                content.append(COMPUTERS);
                content.append(webScraperResponse.getExitoWebScraperComputerTotal());
                content.append("</p>");
            }

            if (webScraperRequest.isExitoMonitors()) {
                content.append("<p>");
                content.append(MONITORS);
                content.append(webScraperResponse.getExitoWebScraperMonitorTotal());
                content.append("</p>");
            }

            if (webScraperRequest.isExitoTablets()) {
                content.append("<p>");
                content.append(TABLETS);
                content.append(webScraperResponse.getExitoWebScraperTabletTotal());
                content.append("</p>");
            }
        }

        if (webScraperRequest.isFalabellaPhones() ||
                webScraperRequest.isFalabellaComputers() ||
                webScraperRequest.isFalabellaMonitors() ||
                webScraperRequest.isFalabellaTablets()) {
            content.append("<p><strong>Falabella:</strong></p>");

            if (webScraperRequest.isFalabellaPhones()) {
                content.append("<p>");
                content.append(SMARTPHONES);
                content.append(webScraperResponse.getFalabellaWebScraperPhonesTotal());
                content.append("</p>");
            }

            if (webScraperRequest.isFalabellaComputers()) {
                content.append("<p>");
                content.append(COMPUTERS);
                content.append(webScraperResponse.getFalabellaWebScraperComputerTotal());
                content.append("</p>");
            }

            if (webScraperRequest.isFalabellaMonitors()) {
                content.append("<p>");
                content.append(MONITORS);
                content.append(webScraperResponse.getFalabellaWebScraperMonitorTotal());
                content.append("</p>");
            }

            if (webScraperRequest.isFalabellaTablets()) {
                content.append("<p>");
                content.append(TABLETS);
                content.append(webScraperResponse.getFalabellaWebScraperTabletTotal());
                content.append("</p>");
            }
        }

        if (webScraperRequest.isMercadoLibrePhones() ||
                webScraperRequest.isMercadoLibreComputers() ||
                webScraperRequest.isMercadoLibreMonitors() ||
                webScraperRequest.isMercadoLibreTablets()) {
            content.append("<p><strong>Mercado libre:</strong></p>");

            if (webScraperRequest.isMercadoLibrePhones()) {
                content.append("<p>");
                content.append(SMARTPHONES);
                content.append(webScraperResponse.getMercadoLibreWebScraperPhonesTotal());
                content.append("</p>");
            }

            if (webScraperRequest.isMercadoLibreComputers()) {
                content.append("<p>");
                content.append(COMPUTERS);
                content.append(webScraperResponse.getMercadoLibreWebScraperComputerTotal());
                content.append("</p>");
            }

            if (webScraperRequest.isMercadoLibreMonitors()) {
                content.append("<p>");
                content.append(MONITORS);
                content.append(webScraperResponse.getMercadoLibreWebScraperMonitorTotal());
                content.append("</p>");
            }

            if (webScraperRequest.isMercadoLibreTablets()) {
                content.append("<p>");
                content.append(TABLETS);
                content.append(webScraperResponse.getMercadoLibreWebScraperTabletTotal());
                content.append("</p>");
            }
        }

        return content.toString();
    }

    private String buildErrorWebScraperEmailContent() {
        return "<p><strong>Resultado de la búsqueda:</strong></p>" +
                "<p>Ocurrió un error en la búsqueda de productos en la web.</p>";
    }
}
