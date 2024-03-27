from fastapi import APIRouter

from app.v1.service import product_web_scraper_bot_service

api_router = APIRouter()


@api_router.get("/")
async def root():
    return {"message": "Hello World"}

@api_router.post("/web-scraper-bot")
async def web_scraper_bot():
    product_web_scraper_bot_service.webScraperBot()