from pydantic import BaseModel, Field


class ProductFindByNameRequestModel(BaseModel):

    product_name: str = Field("", alias="productName")
    skip: int = Field(0, alias="skip")
    limit: int = Field(10, alias="limit")
