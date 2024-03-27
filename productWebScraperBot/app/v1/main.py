from fastapi import FastAPI

from app.v1.route import product_route

app = FastAPI()

app.include_router(product_route.api_router)
