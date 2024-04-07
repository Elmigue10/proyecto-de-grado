from umb.v1.config import chrome_driver_config
from umb.v1.model.scraper_request_model import ScraperRequest
from umb.v1.service.scraper.computer import ktronix_web_scraper_computer_service, \
    falabella_web_scraper_computer_service, exito_web_scraper_computer_service, \
    mercado_libre_web_scraper_computer_service
from umb.v1.service.scraper.monitor import ktronix_web_scraper_monitor_service, falabella_web_scraper_monitor_service, \
    exito_web_scraper_monitor_service, mercado_libre_web_scraper_monitor_service
from umb.v1.service.scraper.phone import ktronix_web_scraper_phone_service, falabella_web_scraper_phone_service, \
    mercado_libre_web_scraper_phone_service, exito_web_scraper_phone_service
from umb.v1.service.scraper.tablet import ktronix_web_scraper_tablet_service, falabella_web_scraper_tablet_service, \
    exito_web_scraper_tablet_service, mercado_libre_web_scraper_tablet_service


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

    # MONITORS
    ktronix_web_scraper_monitors_total = 0
    if scraper_request.ktronix_monitors:
        ktronix_web_scraper_monitors_total += (
            ktronix_web_scraper_monitor_service.ktronix_web_scraper_bot(driver))

    falabella_web_scraper_monitors_total = 0
    if scraper_request.falabella_monitors:
        falabella_web_scraper_monitors_total += (
            falabella_web_scraper_monitor_service.falabella_web_scraper_bot(driver))

    exito_web_scraper_monitors_total = 0
    if scraper_request.exito_monitors:
        exito_web_scraper_monitors_total += (
            exito_web_scraper_monitor_service.exito_web_scraper_bot(driver))

    mercado_libre_web_scraper_monitors_total = 0
    if scraper_request.mercado_libre_monitors:
        mercado_libre_web_scraper_monitors_total += (
            mercado_libre_web_scraper_monitor_service.mercado_web_scraper_bot(driver))

    # TABLETS
    ktronix_web_scraper_tablets_total = 0
    if scraper_request.ktronix_tablets:
        ktronix_web_scraper_tablets_total += (
            ktronix_web_scraper_tablet_service.ktronix_web_scraper_bot(driver))

    falabella_web_scraper_tablets_total = 0
    if scraper_request.falabella_tablets:
        falabella_web_scraper_tablets_total += (
            falabella_web_scraper_tablet_service.falabella_web_scraper_bot(driver))

    exito_web_scraper_tablets_total = 0
    if scraper_request.exito_tablets:
        exito_web_scraper_tablets_total += (
            exito_web_scraper_tablet_service.exito_web_scraper_bot(driver))

    mercado_libre_web_scraper_tablets_total = 0
    if scraper_request.mercado_libre_tablets:
        mercado_libre_web_scraper_tablets_total += (
            mercado_libre_web_scraper_tablet_service.mercado_web_scraper_bot(driver))

    return {
        "ktronixWebScraperPhonesTotal": ktronix_web_scraper_phones_total,
        "falabellaWebScraperPhonesTotal": falabella_web_scraper_phones_total,
        "exitoWebScraperPhonesTotal": exito_web_scraper_phones_total,
        "mercadoLibreWebScraperPhonesTotal": mercado_libre_web_scraper_phones_total,
        "ktronixWebScraperComputerTotal": ktronix_web_scraper_computers_total,
        "falabellaWebScraperComputerTotal": falabella_web_scraper_computers_total,
        "exitoWebScraperComputerTotal": exito_web_scraper_computers_total,
        "mercadoLibreWebScraperComputerTotal": mercado_libre_web_scraper_computers_total,
        "ktronixWebScraperMonitorTotal": ktronix_web_scraper_monitors_total,
        "falabellaWebScraperMonitorTotal": falabella_web_scraper_monitors_total,
        "exitoWebScraperMonitorTotal": exito_web_scraper_monitors_total,
        "mercadoLibreWebScraperMonitorTotal": mercado_libre_web_scraper_monitors_total,
        "ktronixWebScraperTabletTotal": ktronix_web_scraper_tablets_total,
        "falabellaWebScraperTabletTotal": falabella_web_scraper_tablets_total,
        "exitoWebScraperTabletTotal": exito_web_scraper_tablets_total,
        "mercadoLibreWebScraperTabletTotal": mercado_libre_web_scraper_tablets_total
    }
