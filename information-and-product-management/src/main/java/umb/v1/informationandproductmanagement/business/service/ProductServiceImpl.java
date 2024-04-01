package umb.v1.informationandproductmanagement.business.service;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import umb.v1.informationandproductmanagement.business.client.ProductClient;
import umb.v1.informationandproductmanagement.domain.model.dto.RequestFindByIdDTO;
import umb.v1.informationandproductmanagement.domain.model.dto.RequestFindByNameDTO;
import umb.v1.informationandproductmanagement.domain.model.dto.ResponseProductDTO;
import umb.v1.informationandproductmanagement.domain.model.dto.ResponseProductListDTO;

@Service
@Slf4j
public class ProductServiceImpl implements IProductService {

    private final ProductClient productClient;
    private final ObjectMapper objectMapper;

    public ProductServiceImpl(ProductClient productClient) {
        this.productClient = productClient;
        this.objectMapper = new ObjectMapper()
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    @Override
    public ResponseProductListDTO findAll(int skip, int limit) {
        return productClient.findAll(skip, limit);
    }

    @Override
    public ResponseProductDTO findById(RequestFindByIdDTO request) {
        return productClient.findById(request);
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
    public ResponseProductListDTO findByName(RequestFindByNameDTO request) {
        return productClient.findByName(request);
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
}
