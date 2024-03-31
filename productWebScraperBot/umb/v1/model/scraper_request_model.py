from pydantic import BaseModel, Field


class ScraperRequest(BaseModel):

    ktronix_phones: bool = Field(alias="ktronixPhones")
    falabella_phones: bool = Field(alias="falabellaPhones")
    exito_phones: bool = Field(alias="exitoPhones")
    mercado_libre_phones: bool = Field(alias="mercadoLibrePhones")
    ktronix_computers: bool = Field(alias="ktronixComputers")
    falabella_computers: bool = Field(alias="falabellaComputers")
    exito_computers: bool = Field(alias="exitoComputers")
    mercado_libre_computers: bool = Field(alias="mercadoLibreComputers")