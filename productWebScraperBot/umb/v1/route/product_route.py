from fastapi import APIRouter, Response, status

from umb.v1.model.product_find_by_id_list_request import ProductFindByIdListRequestModel
from umb.v1.model.product_find_by_id_request_model import ProductFindByIdRequestModel
from umb.v1.model.product_find_by_name_request_model import ProductFindByNameRequestModel
from umb.v1.model.scraper_request_model import ScraperRequest
from umb.v1.service import product_web_scraper_bot_service, product_service

api_router = APIRouter()


@api_router.get("/")
async def root():
    return {"message": "Hello World"}


@api_router.post("/umb/v1/product/web-scraper-bot")
async def web_scraper_bot(scraper_request: ScraperRequest):
    return product_web_scraper_bot_service.webScraperBot(scraper_request)


@api_router.get("/umb/v1/product")
async def find_all(skip: int = 0, limit: int = 10):
    return product_service.find_all(skip, limit)


@api_router.post("/umb/v1/product/find-by-id", status_code=200)
async def find_by_id(product_find_by_id_request: ProductFindByIdRequestModel, response: Response):
    product_response = product_service.find_by_id(product_find_by_id_request)

    if product_response["status"] == 404:
        response.status_code = status.HTTP_404_NOT_FOUND

    return product_response


@api_router.get("/umb/v1/product/find-by-brand")
def find_by_brand(brand_name: str = '', skip: int = 0, limit: int = 10):
    return product_service.find_by_brand(brand_name, skip, limit)


@api_router.get("/umb/v1/product/find-by-category")
def find_by_category(category_name: str = '', skip: int = 0, limit: int = 10):
    return product_service.find_by_category(category_name, skip, limit)


@api_router.get("/umb/v1/product/find-by-platform")
def find_by_platform(platform_name: str = '', skip: int = 0, limit: int = 10):
    return product_service.find_by_platform(platform_name, skip, limit)


@api_router.post("/umb/v1/product/find-by-name")
def find_by_name(product_find_by_name_request: ProductFindByNameRequestModel):
    return product_service.find_by_name(product_find_by_name_request)


@api_router.get("/umb/v1/product/find-by-price-range")
def find_by_price_range(min_price: str = '0', max_price: str = '0', category_name: str = '', skip: int = 0, limit: int = 10):
    return product_service.find_by_price_range(min_price, max_price, category_name, skip, limit)


@api_router.get("/umb/v1/product/find-by-brand-and-category")
def find_by_brand_and_category(brand_name: str = '', category_name: str = '', skip: int = 0, limit: int = 10):
    return product_service.find_by_brand_and_category(brand_name, category_name, skip, limit)


@api_router.get("/umb/v1/product/find-by-platform-and-category")
def find_by_platform_and_category(platform_name: str = '', category_name: str = '', skip: int = 0, limit: int = 10):
    return product_service.find_by_platform_and_category(platform_name, category_name, skip, limit)


@api_router.get("/umb/v1/product/find-by-brand-category-and-platform")
def find_by_brand_category_and_platform(brand_name: str = '', category_name: str = '', platform_name: str = '', skip: int = 0, limit: int = 10):
    return product_service.find_by_brand_category_and_platform(brand_name, category_name, platform_name, skip, limit)


@api_router.get("/umb/v1/product/find-by-ram-memory")
def find_by_ram_memory(ram_memory: str = '', category_name: str = '', skip: int = 0, limit: int = 10):
    return product_service.find_by_ram_memory(ram_memory, category_name, skip, limit)

@api_router.get("/umb/v1/product/find-by-storage-capacity")
def find_by_storage_capacity(storage_capacity: str = '', category_name: str = '', skip: int = 0, limit: int = 10):
    return product_service.find_by_storage_capacity(storage_capacity, category_name, skip, limit)

@api_router.get("/umb/v1/product/find-by-screen-size")
def find_by_screen_size(screen_size: str = '', category_name: str = '', skip: int = 0, limit: int = 10):
    return product_service.find_by_screen_size(screen_size, category_name, skip, limit)

@api_router.post("/umb/v1/product/find-by-id-list")
def find_by_id_list(product_find_id_list_request: ProductFindByIdListRequestModel):
    return product_service.find_by_id_list(product_find_id_list_request)


@api_router.post("/umb/v1/product/reorganize")
async def reorganize():
    return product_service.reorganize()

@api_router.post("/umb/v1/product/refine-id")
async def refine_id():
    return product_service.refine_id()
