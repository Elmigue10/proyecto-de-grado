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
    ktronix_monitors: bool = Field(alias="ktronixMonitors")
    falabella_monitors: bool = Field(alias="falabellaMonitors")
    exito_monitors: bool = Field(alias="exitoMonitors")
    mercado_libre_monitors: bool = Field(alias="mercadoLibreMonitors")
    ktronix_tablets: bool = Field(alias="ktronixTablets")
    falabella_tablets: bool = Field(alias="falabellaTablets")
    exito_tablets: bool = Field(alias="exitoTablets")
    mercado_libre_tablets: bool = Field(alias="mercadoLibreTablets")