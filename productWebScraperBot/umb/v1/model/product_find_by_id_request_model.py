from pydantic import BaseModel, Field


class ProductFindByIdRequestModel(BaseModel):

    product_id: str = Field("", alias="productoId")