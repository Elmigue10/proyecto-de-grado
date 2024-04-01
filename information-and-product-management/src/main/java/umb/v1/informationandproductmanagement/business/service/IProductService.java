package umb.v1.informationandproductmanagement.business.service;

import umb.v1.informationandproductmanagement.domain.model.dto.RequestFindByIdDTO;
import umb.v1.informationandproductmanagement.domain.model.dto.RequestFindByNameDTO;
import umb.v1.informationandproductmanagement.domain.model.dto.ResponseProductDTO;
import umb.v1.informationandproductmanagement.domain.model.dto.ResponseProductListDTO;

public interface IProductService {
    ResponseProductListDTO findAll(int skip, int limit);

    ResponseProductDTO findById(RequestFindByIdDTO request);

    ResponseProductListDTO findByBrand(String brandName, int skip, int limit);

    ResponseProductListDTO findByCategory(String categoryName, int skip, int limit);

    ResponseProductListDTO findByPlatform(String platformName, int skip, int limit);

    ResponseProductListDTO findByName(RequestFindByNameDTO request);

    ResponseProductListDTO findByPriceRange(String minPrice, String maxPrice, int skip, int limit);

    ResponseProductListDTO findByBrandAndCategory(String brandName, String categoryName, int skip, int limit);

    ResponseProductListDTO findByPlatformAndCategory(String platformName, String categoryName, int skip, int limit);

    ResponseProductListDTO findByBrandCategoryAndPlatform(String brandName, String categoryName, String platformName, int skip, int limit);

    ResponseProductListDTO findByRamMemory(String ramMemory, String categoryName, int skip, int limit);
}
