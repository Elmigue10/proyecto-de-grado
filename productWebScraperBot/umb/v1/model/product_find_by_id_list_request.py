from typing import List

from pydantic import BaseModel, Field


class ProductFindByIdListRequestModel(BaseModel):

    id_list: List[str] = Field([], alias="idList")
    skip: int = Field(0, alias="skip")
    limit: int = Field(10, alias="limit")
