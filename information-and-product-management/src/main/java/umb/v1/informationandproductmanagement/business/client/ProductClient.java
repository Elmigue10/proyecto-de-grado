package umb.v1.informationandproductmanagement.business.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import umb.v1.informationandproductmanagement.domain.model.dto.RequestFindByIdDTO;
import umb.v1.informationandproductmanagement.domain.model.dto.RequestFindByNameDTO;
import umb.v1.informationandproductmanagement.domain.model.dto.ResponseProductDTO;
import umb.v1.informationandproductmanagement.domain.model.dto.ResponseProductListDTO;

@FeignClient(value = "product-client", url = "${service.values.client.product.url}")
public interface ProductClient {

    @GetMapping
    ResponseProductListDTO findAll(@RequestParam("skip") int skip, @RequestParam("limit") int limit);

    @PostMapping("/find-by-id")
    ResponseProductDTO findById(@RequestBody RequestFindByIdDTO request);

    @GetMapping("/find-by-brand")
    ResponseProductListDTO findByBrand(@RequestParam("brand_name") String brandName,
                                       @RequestParam("skip") int skip,
                                       @RequestParam("limit") int limit);

    @GetMapping("/find-by-category")
    ResponseProductListDTO findByCategory(@RequestParam("category_name") String categoryName,
                                          @RequestParam("skip") int skip,
                                          @RequestParam("limit") int limit);

    @GetMapping("/find-by-platform")
    ResponseProductListDTO findByPlatform(@RequestParam("platform_name") String platformName,
                                          @RequestParam("skip") int skip,
                                          @RequestParam("limit") int limit);

    @PostMapping("/find-by-name")
    ResponseProductListDTO findByName(@RequestBody RequestFindByNameDTO request);

    @GetMapping("/find-by-price-range")
    ResponseProductListDTO findByPriceRange(@RequestParam("min_price") String minPrice,
                                            @RequestParam("max_price") String maxPrice,
                                            @RequestParam("skip") int skip,
                                            @RequestParam("limit") int limit);

    @GetMapping("/find-by-brand-and-category")
    ResponseProductListDTO findByBrandAndCategory(@RequestParam("brand_name") String brandName,
                                                  @RequestParam("category_name") String categoryName,
                                                  @RequestParam("skip") int skip,
                                                  @RequestParam("limit") int limit);

    @GetMapping("/find-by-platform-and-category")
    ResponseProductListDTO findByPlatformAndCategory(@RequestParam("platform_name") String platformName,
                                                     @RequestParam("category_name") String categoryName,
                                                     @RequestParam("skip") int skip,
                                                     @RequestParam("limit") int limit);

    @GetMapping("/find-by-brand-category-and-platform")
    ResponseProductListDTO findByBrandCategoryAndPlatform(@RequestParam("brand_name") String brandName,
                                                          @RequestParam("category_name") String categoryName,
                                                          @RequestParam("platform_name") String platformName,
                                                          @RequestParam("skip") int skip,
                                                          @RequestParam("limit") int limit);

    @GetMapping("/find-by-ram-memory")
    ResponseProductListDTO findByRamMemory(@RequestParam("ram_memory") String ramMemory,
                                           @RequestParam("category_name") String categoryName,
                                           @RequestParam("skip") int skip,
                                           @RequestParam("limit") int limit);
}
