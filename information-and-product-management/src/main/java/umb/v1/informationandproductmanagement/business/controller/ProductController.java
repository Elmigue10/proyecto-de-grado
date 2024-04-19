package umb.v1.informationandproductmanagement.business.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import umb.v1.informationandproductmanagement.business.service.interfaces.IProductService;
import umb.v1.informationandproductmanagement.domain.model.dto.*;

import java.util.Map;

@RestController
@Slf4j
@RequestMapping(value = "/product", produces = MediaType.APPLICATION_JSON_VALUE)
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
    public ResponseEntity<ResponseProductDTO> findById(@RequestBody RequestFindByIdDTO request,
                                                       @RequestHeader Map<String, String> requestHeaders) {
        log.info("arrived request for find-by-id. {}", request);

        ResponseProductDTO productResponse = productService.findById(request, requestHeaders);

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
    public ResponseEntity<ResponseProductListDTO> findByName(@RequestBody RequestFindByNameDTO requestBody,
                                                             @RequestHeader Map<String, String> requestHeaders) {
        log.info("arrived request for find-by-name. {}", requestBody);

        ResponseProductListDTO productsResponse = productService.findByName(requestBody, requestHeaders);

        log.info("find-by-name response: {}", productsResponse);

        return new ResponseEntity<>(productsResponse, HttpStatus.OK);
    }

    @GetMapping("/find-by-price-range")
    public ResponseEntity<ResponseProductListDTO> findByPriceRange(@RequestParam("min_price") String minPrice,
                                                                   @RequestParam("max_price") String maxPrice,
                                                                   @RequestParam("category_name") String categoryName,
                                                                   @RequestParam("skip") int skip,
                                                                   @RequestParam("limit") int limit) {
        log.info("arrived request for find-by-price-range. min_prince: {}, max_price: {}, " +
                "skip: {}, limit: {}", minPrice, maxPrice, skip, limit);

        ResponseProductListDTO productsResponse =
                productService.findByPriceRange(minPrice, maxPrice, categoryName, skip, limit);

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
                                                                                 @RequestParam("limit") int limit) {
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
                                                                  @RequestParam("limit") int limit) {
        log.info("arrived request for find-by-ram-memory. ram_memory: {}, category_name: {}, " +
                "skip: {}, limit: {}", ramMemory, categoryName, skip, limit);

        ResponseProductListDTO productsResponse = productService.findByRamMemory(ramMemory, categoryName, skip, limit);

        log.info("find-by-ram-memory response: {}", productsResponse);

        return new ResponseEntity<>(productsResponse, HttpStatus.OK);
    }

    @GetMapping("/find-by-storage-capacity")
    public ResponseEntity<ResponseProductListDTO> findByStorageCapacity(@RequestParam("storage_capacity") String storageCapacity,
                                                                        @RequestParam("category_name") String categoryName,
                                                                        @RequestParam("skip") int skip,
                                                                        @RequestParam("limit") int limit) {
        log.info("arrived request for find-by-storage-capacity. ram_memory: {}, category_name: {}, " +
                "skip: {}, limit: {}", storageCapacity, categoryName, skip, limit);

        ResponseProductListDTO productsResponse =
                productService.findByStorageCapacity(storageCapacity, categoryName, skip, limit);

        log.info("find-by-storage-capacity response: {}", productsResponse);

        return new ResponseEntity<>(productsResponse, HttpStatus.OK);
    }

    @GetMapping("/find-by-screen-size")
    public ResponseEntity<ResponseProductListDTO> findByScreenSize(@RequestParam("screen_size") String screenSize,
                                                                   @RequestParam("category_name") String categoryName,
                                                                   @RequestParam("skip") int skip,
                                                                   @RequestParam("limit") int limit) {
        log.info("arrived request for find-by-screen-size. ram_memory: {}, category_name: {}, " +
                "skip: {}, limit: {}", screenSize, categoryName, skip, limit);

        ResponseProductListDTO productsResponse =
                productService.findByScreenSize(screenSize, categoryName, skip, limit);

        log.info("find-by-screen-size response: {}", productsResponse);

        return new ResponseEntity<>(productsResponse, HttpStatus.OK);
    }

    @GetMapping("/find-most-viewed")
    public ResponseEntity<ResponseProductListDTO> findMostViewed(@RequestParam("skip") int skip,
                                                                 @RequestParam("limit") int limit) {
        log.info("arrived request for find-most-viewed");

        ResponseProductListDTO productsResponse = productService.findMostViewed(skip, limit);

        log.info("find-most-viewed response: {}", productsResponse);

        return new ResponseEntity<>(productsResponse, HttpStatus.OK);
    }

    @PostMapping("/web-scraper-bot")
    public ResponseEntity<WebScraperResponseDTO> webScraperBot(@RequestBody WebScraperRequestDTO webScraperRequest) {
        log.info("arrived request for web-scraper-bot");

        WebScraperResponseDTO webScraperResponse = productService.webScraperBot(webScraperRequest);

        log.info("web-scraper-bot response: {}", webScraperResponse);

        return new ResponseEntity<>(webScraperResponse, HttpStatus.OK);
    }

    @PostMapping("/refine-products-data")
    public ResponseEntity<ResponseProductListDTO> refineProductsData() {
        log.info("arrived request for refine-products-data");

        ResponseProductListDTO refineProductsDataResponse = productService.refineProductsData();

        log.info("web-scraper-bot response: {}", refineProductsDataResponse);

        return new ResponseEntity<>(refineProductsDataResponse, HttpStatus.OK);
    }
}
