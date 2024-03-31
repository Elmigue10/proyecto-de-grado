from umb.v1.config import chrome_driver_config
from umb.v1.model.scraper_request_model import ScraperRequest
from umb.v1.service.scraper.computer import ktronix_web_scraper_computer_service, \
    falabella_web_scraper_computer_service, exito_web_scraper_computer_service, \
    mercado_libre_web_scraper_computer_service
from umb.v1.service.scraper.phone import ktronix_web_scraper_phone_service, falabella_web_scraper_phone_service, \
    mercado_libre_web_scraper_phone_service, exito_web_scraper_phone_service


def webScraperBot(scraper_request: ScraperRequest):

    driver = chrome_driver_config.driver_config()

    # PHONES
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

    # COMPUTERS
    ktronix_web_scraper_computers_total = 0
    if scraper_request.ktronix_computers:
        ktronix_web_scraper_computers_total += (
            ktronix_web_scraper_computer_service.ktronix_web_scraper_bot(driver))

    falabella_web_scraper_computers_total = 0
    if scraper_request.falabella_computers:
        falabella_web_scraper_computers_total += (
            falabella_web_scraper_computer_service.falabella_web_scraper_bot(driver))

    exito_web_scraper_computers_total = 0
    if scraper_request.exito_computers:
        exito_web_scraper_computers_total += (
            exito_web_scraper_computer_service.exito_web_scraper_bot(driver))

    mercado_libre_web_scraper_computers_total = 0
    if scraper_request.mercado_libre_computers:
        mercado_libre_web_scraper_computers_total += (
            mercado_libre_web_scraper_computer_service.mercado_web_scraper_bot(driver))

    return {
        "ktronixWebScraperPhonesTotal": ktronix_web_scraper_phones_total,
        "falabellaWebScraperPhonesTotal": falabella_web_scraper_phones_total,
        "exitoWebScraperPhonesTotal": exito_web_scraper_phones_total,
        "mercadoLibreWebScraperPhonesTotal": mercado_libre_web_scraper_phones_total,
        "ktronixWebScraperComputerTotal": ktronix_web_scraper_computers_total,
        "falabellaWebScraperComputerTotal": falabella_web_scraper_computers_total,
        "exitoWebScraperComputerTotal": exito_web_scraper_computers_total,
        "mercadoLibreWebScraperComputerTotal": mercado_libre_web_scraper_computers_total
    }
