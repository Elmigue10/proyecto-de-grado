package umb.v1.informationandproductmanagement.business.service.interfaces;

import umb.v1.informationandproductmanagement.domain.model.dto.*;

import java.util.Map;

public interface IProductService {
    ResponseProductListDTO findAll(int skip, int limit);

    ResponseProductDTO findById(RequestFindByIdDTO request, Map<String, String> requestHeaders);

    ResponseProductListDTO findByBrand(String brandName, int skip, int limit);

    ResponseProductListDTO findByCategory(String categoryName, int skip, int limit);

    ResponseProductListDTO findByPlatform(String platformName, int skip, int limit);

    ResponseProductListDTO findByName(RequestFindByNameDTO request, Map<String, String> requestHeaders);

    ResponseProductListDTO findByPriceRange(String minPrice, String maxPrice, String categoryName, int skip, int limit);

    ResponseProductListDTO findByBrandAndCategory(String brandName, String categoryName, int skip, int limit);

    ResponseProductListDTO findByPlatformAndCategory(String platformName, String categoryName, int skip, int limit);

    ResponseProductListDTO findByBrandCategoryAndPlatform(String brandName, String categoryName, String platformName, int skip, int limit);

    ResponseProductListDTO findByRamMemory(String ramMemory, String categoryName, int skip, int limit);

    ResponseProductListDTO findByStorageCapacity(String storageCapacity, String categoryName, int skip, int limit);

    ResponseProductListDTO findByScreenSize(String screenSize, String categoryName, int skip, int limit);

    ResponseProductListDTO findMostViewed(int skip, int limit);

    WebScraperResponseDTO webScraperBot(WebScraperRequestDTO webScraperRequest);

    ResponseProductListDTO refineProductsData();
}
