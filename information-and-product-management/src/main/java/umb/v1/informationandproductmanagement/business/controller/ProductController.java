package umb.v1.informationandproductmanagement.business.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import umb.v1.informationandproductmanagement.business.service.IProductService;
import umb.v1.informationandproductmanagement.domain.model.dto.RequestFindByIdDTO;
import umb.v1.informationandproductmanagement.domain.model.dto.RequestFindByNameDTO;
import umb.v1.informationandproductmanagement.domain.model.dto.ResponseProductDTO;
import umb.v1.informationandproductmanagement.domain.model.dto.ResponseProductListDTO;

@RestController
@Slf4j
@RequestMapping("/product")
public class ProductController {

    private final IProductService productService;

    public ProductController(IProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public ResponseEntity<ResponseProductListDTO> findAll(@RequestParam("skip") int skip,
                                                          @RequestParam("limit") int limit) {
        log.info("arrived request for find-all. skip: {}, limit: {}", skip, limit);
        ResponseProductListDTO productsResponse = productService.findAll(skip, limit);

        log.info("find-all response: {}", productsResponse);
        return new ResponseEntity<>(productsResponse, HttpStatus.OK);
    }

    @PostMapping("/find-by-id")
    public ResponseEntity<ResponseProductDTO> findById(@RequestBody RequestFindByIdDTO request) {
        log.info("arrived request for find-by-id. {}", request);

        ResponseProductDTO productResponse = productService.findById(request);

        log.info("find-by-id response: {}", productResponse);

        return new ResponseEntity<>(productResponse, HttpStatus.OK);
    }

    @GetMapping("/find-by-brand")
    public ResponseEntity<ResponseProductListDTO> findByBrand(@RequestParam("brand_name") String brandName,
                                                              @RequestParam("skip") int skip,
                                                              @RequestParam("limit") int limit) {
        log.info("arrived request for find-by-brand. brand_name: {}, skip: {}, limit: {}", brandName, skip, limit);

        ResponseProductListDTO productsResponse = productService.findByBrand(brandName, skip, limit);

        log.info("find-by-brand response: {}", productsResponse);

        return new ResponseEntity<>(productsResponse, HttpStatus.OK);
    }

    @GetMapping("/find-by-category")
    public ResponseEntity<ResponseProductListDTO> findByCategory(@RequestParam("category_name") String categoryName,
                                                                 @RequestParam("skip") int skip,
                                                                 @RequestParam("limit") int limit) {
        log.info("arrived request for find-by-category. category_name: {}, skip: {}, limit: {}",
                categoryName, skip, limit);

        ResponseProductListDTO productsResponse = productService.findByCategory(categoryName, skip, limit);

        log.info("find-by-category response: {}", productsResponse);

        return new ResponseEntity<>(productsResponse, HttpStatus.OK);
    }

    @GetMapping("/find-by-platform")
    public ResponseEntity<ResponseProductListDTO> findByPlatform(@RequestParam("platform_name") String platformName,
                                                                 @RequestParam("skip") int skip,
                                                                 @RequestParam("limit") int limit) {
        log.info("arrived request for find-by-platform. platform_name: {}, skip: {}, limit: {}",
                platformName, skip, limit);

        ResponseProductListDTO productsResponse = productService.findByPlatform(platformName, skip, limit);

        log.info("find-by-platform response: {}", productsResponse);

        return new ResponseEntity<>(productsResponse, HttpStatus.OK);
    }

    @PostMapping("/find-by-name")
    public ResponseEntity<ResponseProductListDTO> findByName(@RequestBody RequestFindByNameDTO request) {
        log.info("arrived request for find-by-name. {}", request);

        ResponseProductListDTO productsResponse = productService.findByName(request);

        log.info("find-by-name response: {}", productsResponse);

        return new ResponseEntity<>(productsResponse, HttpStatus.OK);
    }

    @GetMapping("/find-by-price-range")
    public ResponseEntity<ResponseProductListDTO> findByPriceRange(@RequestParam("min_price") String minPrice,
                                                                   @RequestParam("max_price") String maxPrice,
                                                                   @RequestParam("skip") int skip,
                                                                   @RequestParam("limit") int limit) {
        log.info("arrived request for find-by-price-range. min_prince: {}, max_price: {}, " +
                "skip: {}, limit: {}", minPrice, maxPrice, skip, limit);

        ResponseProductListDTO productsResponse = productService.findByPriceRange(minPrice, maxPrice, skip, limit);

        log.info("find-by-price-range response: {}", productsResponse);

        return new ResponseEntity<>(productsResponse, HttpStatus.OK);
    }

    @GetMapping("/find-by-brand-and-category")
    public ResponseEntity<ResponseProductListDTO> findByBrandAndCategory(@RequestParam("brand_name") String brandName,
                                                                         @RequestParam("category_name") String categoryName,
                                                                         @RequestParam("skip") int skip,
                                                                         @RequestParam("limit") int limit) {
        log.info("arrived request for find-by-brand-and-category. brand_name: {}, category_name: {}, " +
                "skip: {}, limit: {}", brandName, categoryName, skip, limit);

        ResponseProductListDTO productsResponse =
                productService.findByBrandAndCategory(brandName, categoryName, skip, limit);

        log.info("find-by-brand-and-category response: {}", productsResponse);

        return new ResponseEntity<>(productsResponse, HttpStatus.OK);
    }

    @GetMapping("/find-by-platform-and-category")
    public ResponseEntity<ResponseProductListDTO> findByPlatformAndCategory(@RequestParam("platform_name") String platformName,
                                                                            @RequestParam("category_name") String categoryName,
                                                                            @RequestParam("skip") int skip,
                                                                            @RequestParam("limit") int limit) {
        log.info("arrived request for find-by-platform-and-category. platform_name: {}, category_name: {}," +
                " skip: {}, limit: {}", platformName, categoryName, skip, limit);

        ResponseProductListDTO productsResponse =
                productService.findByPlatformAndCategory(platformName, categoryName, skip, limit);

        log.info("find-by-platform-and-category response: {}", productsResponse);

        return new ResponseEntity<>(productsResponse, HttpStatus.OK);
    }

    @GetMapping("/find-by-brand-category-and-platform")
    public ResponseEntity<ResponseProductListDTO> findByBrandCategoryAndPlatform(@RequestParam("brand_name") String brandName,
                                                          @RequestParam("category_name") String categoryName,
                                                          @RequestParam("platform_name") String platformName,
                                                          @RequestParam("skip") int skip,
                                                          @RequestParam("limit") int limit){
        log.info("arrived request for find-by-brand-category-and-platform. brand_name: {}, category_name: {}, " +
                "platform_name: {} skip: {}, limit: {}", brandName, categoryName, platformName, skip, limit);
        
        ResponseProductListDTO productsResponse = productService
                .findByBrandCategoryAndPlatform(brandName, categoryName, platformName, skip, limit);

        log.info("find-by-brand-category-and-platform response: {}", productsResponse);

        return new ResponseEntity<>(productsResponse, HttpStatus.OK);
    }

    @GetMapping("/find-by-ram-memory")
    public ResponseEntity<ResponseProductListDTO> findByRamMemory(@RequestParam("ram_memory") String ramMemory,
                                                                  @RequestParam("category_name") String categoryName,
                                                                  @RequestParam("skip") int skip,
                                                                  @RequestParam("limit") int limit){
        log.info("arrived request for find-by-ram-memory. ram_memory: {}, category_name: {}, " +
                "skip: {}, limit: {}", ramMemory, categoryName, skip, limit);
        
        ResponseProductListDTO productsResponse = productService.findByRamMemory(ramMemory, categoryName, skip, limit);

        log.info("find-by-ram-memory response: {}", productsResponse);

        return new ResponseEntity<>(productsResponse, HttpStatus.OK);
    }
}
