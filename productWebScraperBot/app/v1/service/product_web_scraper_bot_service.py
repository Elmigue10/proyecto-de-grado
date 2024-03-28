from app.v1.config import chrome_driver_config
from app.v1.model.scraper_request_model import ScraperRequest
from app.v1.service.scraper.phone import ktronix_web_scraper_phone_service, falabella_web_scraper_phone_service, \
    mercado_libre_web_scraper_phone_service, exito_web_scraper_phone_service


def webScraperBot(scraper_request: ScraperRequest):

    driver = chrome_driver_config.driver_config()

    ktronix_web_scraper_phones_total = 0
    if scraper_request.ktronix_phones:
        ktronix_web_scraper_phones_total += (
            ktronix_web_scraper_phone_service.ktronix_web_scraper_bot(driver))

    falabella_web_scraper_phones_total = 0
    if scraper_request.falabella_phones:
        falabella_web_scraper_phones_total += (
            falabella_web_scraper_phone_service.falabella_web_scraper_bot(driver))

    exito_web_scraper_phones_total = 0
    if scraper_request.exito_phones:
        exito_web_scraper_phones_total += (
            exito_web_scraper_phone_service.exito_web_scraper_bot(driver))

    mercado_libre_web_scraper_phones_total = 0
    if scraper_request.mercado_libre_phones:
        mercado_libre_web_scraper_phones_total += (
            mercado_libre_web_scraper_phone_service.mercado_web_scraper_bot(driver))

    return {
        "ktronixWebScraperPhonesTotal": ktronix_web_scraper_phones_total,
        "falabellaWebScraperPhonesTotal": falabella_web_scraper_phones_total,
        "exitoWebScraperPhonesTotal": exito_web_scraper_phones_total,
        "mercadoLibreWebScraperPhonesTotal": mercado_libre_web_scraper_phones_total
    }
