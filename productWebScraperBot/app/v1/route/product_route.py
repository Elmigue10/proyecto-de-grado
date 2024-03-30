from fastapi import APIRouter, Query

from app.v1.model.product_find_by_id_request_model import ProductFindByIdRequestModel
from app.v1.model.scraper_request_model import ScraperRequest
from app.v1.service import product_web_scraper_bot_service, product_service

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

@api_router.post("/umb/v1/product/find-by-id")
async def find_by_id(prorduct_find_by_id_request: ProductFindByIdRequestModel):
    return product_service.find_by_id(prorduct_find_by_id_request)
