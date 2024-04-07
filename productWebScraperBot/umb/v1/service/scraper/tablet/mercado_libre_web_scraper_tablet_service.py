import time
from datetime import datetime

from selenium.webdriver.common.by import By

from umb.v1.config.mongodb_config import insert_product
from umb.v1.model.characteristic_model import Caracteristica
from umb.v1.model.comment_model import Comentario
from umb.v1.model.product_model import Producto


def mercado_web_scraper_bot(driver):
    mercado_libre_tablet_url = "https://www.mercadolibre.com.co/mas-vendidos/MCO82067?new_bestseller_landing=false#origin=pdp"
    tablet_scraper_counter = 0

    print("scraping tablets from mercado libre...")

    driver.get(mercado_libre_tablet_url)
    time.sleep(1)

    links_tablets = driver.find_elements(By.CSS_SELECTOR,
                                            "a.ui-recommendations-card__link")

    tablets_urls = []
    for link_tablet in links_tablets:
        tablets_urls.append(link_tablet.get_attribute("href"))

    for tablet_url in tablets_urls:

        try:
            driver.get(tablet_url)

            producto = Producto()

            nombre = driver.find_element(By.CSS_SELECTOR, "h1.ui-pdp-title").text.strip()

            producto.nombre = nombre

            producto.id = nombre + "-mercado-libre"

            producto.url = tablet_url

            imagen_url = (driver.find_element(By.CSS_SELECTOR, "img.ui-pdp-image.ui-pdp-gallery__figure__image")
                          .get_attribute("src"))

            producto.imagen_url = imagen_url

            precio = (driver.find_element(By.CSS_SELECTOR, "span.andes-money-amount__fraction").text
                      .strip().replace(".", ""))

            producto.precio = precio

            producto.categoria = "tablet"

            producto.plataforma = "mercado-libre"
            producto.created_or_updated_at = datetime.now()

            specifications_names = driver.find_elements(By.CSS_SELECTOR, "div.andes-table__header__container")
            specifications_values = driver.find_elements(By.CSS_SELECTOR, "span.andes-table__column--value")

            tablet_characteristics = []
            for i in range(len(specifications_names)):

                try:
                    if specifications_names[i].get_attribute("innerHTML").strip() == "Marca":
                        producto.marca = specifications_values[i].get_attribute("innerHTML").strip()

                    elif specifications_names[i].get_attribute("innerHTML").strip() == "Línea":
                        tablet_characteristics.append(Caracteristica("Línea",
                                                                    specifications_values[i].get_attribute("innerHTML").strip()))

                    elif specifications_names[i].get_attribute("innerHTML").strip() == "Modelo":
                        tablet_characteristics.append(Caracteristica("Modelo",
                                                                    specifications_values[i].get_attribute("innerHTML").strip()))

                    elif specifications_names[i].get_attribute("innerHTML").strip() == "Nombre del sistema operativo":
                        tablet_characteristics.append(Caracteristica("Nombre del sistema operativo",
                                                                    specifications_values[i].get_attribute("innerHTML").strip()))

                    elif specifications_names[i].get_attribute("innerHTML").strip() == "Versión original del sistema operativo":
                        tablet_characteristics.append(Caracteristica("Versión original del sistema operativo",
                                                                    specifications_values[i].get_attribute("innerHTML").strip()))

                    elif specifications_names[i].get_attribute("innerHTML").strip() == "Versión del sistema operativo":
                        tablet_characteristics.append(Caracteristica("Versión del sistema operativo",
                                                                    specifications_values[i].get_attribute("innerHTML").strip()))

                    elif specifications_names[i].get_attribute("innerHTML").strip() == "Memoria interna":
                        tablet_characteristics.append(Caracteristica("Memoria interna",
                                                                    specifications_values[i].get_attribute("innerHTML").strip()))

                    elif specifications_names[i].get_attribute("innerHTML").strip() == "Capacidad":
                        tablet_characteristics.append(Caracteristica("Capacidad",
                                                                    specifications_values[i].get_attribute("innerHTML").strip()))

                    elif specifications_names[i].get_attribute("innerHTML").strip() == "Memoria RAM":
                        tablet_characteristics.append(Caracteristica("Memoria RAM",
                                                                    specifications_values[i].get_attribute("innerHTML").strip()))

                    elif specifications_names[i].get_attribute("innerHTML").strip() == "Conectividad":
                        tablet_characteristics.append(Caracteristica("Conectividad",
                                                                    specifications_values[i].get_attribute("innerHTML").strip()))

                    elif specifications_names[i].get_attribute("innerHTML").strip() == "Versión de Bluetooth":
                        tablet_characteristics.append(Caracteristica("Versión de Bluetooth",
                                                                    specifications_values[i].get_attribute("innerHTML").strip()))

                    elif specifications_names[i].get_attribute("innerHTML").strip() == "Tipos de lectores de tarjetas de memoria":
                        tablet_characteristics.append(Caracteristica("Tipos de lectores de tarjetas de memoria",
                                                                    specifications_values[i].get_attribute("innerHTML").strip()))

                    elif specifications_names[i].get_attribute("innerHTML").strip() == "Tamaño de la pantalla":
                        tablet_characteristics.append(Caracteristica("Tamaño de la pantalla",
                                                                    specifications_values[i].get_attribute("innerHTML").strip()))

                    elif specifications_names[i].get_attribute("innerHTML").strip() == "Máxima resolución de pantalla":
                        tablet_characteristics.append(Caracteristica("Máxima resolución de pantalla",
                                                                    specifications_values[i].get_attribute("innerHTML").strip()))

                    elif specifications_names[i].get_attribute("innerHTML").strip() == "Cantidad de cámaras traseras":
                        tablet_characteristics.append(Caracteristica("Cantidad de cámaras traseras",
                                                                    specifications_values[i].get_attribute("innerHTML").strip()))

                    elif specifications_names[i].get_attribute("innerHTML").strip() == "Resoluciones de las cámaras traseras":
                        tablet_characteristics.append(Caracteristica("Resoluciones de las cámaras traseras",
                                                                    specifications_values[i].get_attribute("innerHTML").strip()))

                    elif specifications_names[i].get_attribute("innerHTML").strip() == "Accesorios incluidos":
                        tablet_characteristics.append(Caracteristica("Accesorios incluidos",
                                                                    specifications_values[i].get_attribute("innerHTML").strip()))

                    elif specifications_names[i].get_attribute("innerHTML").strip() == "Procesador gráfico":
                        tablet_characteristics.append(Caracteristica("Procesador gráfico",
                                                                    specifications_values[i].get_attribute("innerHTML").strip()))

                    elif specifications_names[i].get_attribute("innerHTML").strip() == "Procesadores":
                        tablet_characteristics.append(Caracteristica("Procesadores",
                                                                    specifications_values[i].get_attribute("innerHTML").strip()))

                    elif specifications_names[i].get_attribute("innerHTML").strip() == "Chipset":
                        tablet_characteristics.append(Caracteristica("Chipset",
                                                                    specifications_values[i].get_attribute("innerHTML").strip()))

                    elif specifications_names[i].get_attribute("innerHTML").strip() == "Velocidad del procesador":
                        tablet_characteristics.append(Caracteristica("Velocidad del procesador",
                                                                    specifications_values[i].get_attribute("innerHTML").strip()))

                    elif specifications_names[i].get_attribute("innerHTML").strip() == "Cantidad de núcleos":
                        tablet_characteristics.append(Caracteristica("Cantidad de núcleos",
                                                                    specifications_values[i].get_attribute("innerHTML").strip()))

                    elif specifications_names[i].get_attribute("innerHTML").strip() == "Duración de la batería de la tablet":
                        tablet_characteristics.append(Caracteristica("Duración de la batería de la tablet",
                                                                    specifications_values[i].get_attribute("innerHTML").strip()))

                    elif specifications_names[i].get_attribute("innerHTML").strip() == "Capacidad de la batería":
                        tablet_characteristics.append(Caracteristica("Capacidad de la batería",
                                                                    specifications_values[i].get_attribute("innerHTML").strip()))

                    elif specifications_names[i].get_attribute("innerHTML").strip() == "Cantidad de cámaras frontales":
                        tablet_characteristics.append(Caracteristica("Cantidad de cámaras frontales",
                                                                    specifications_values[i].get_attribute("innerHTML").strip()))

                    elif specifications_names[i].get_attribute("innerHTML").strip() == "Resolución de las cámaras frontales":
                        tablet_characteristics.append(Caracteristica("Resolución de las cámaras frontales",
                                                                    specifications_values[i].get_attribute("innerHTML").strip()))

                    elif specifications_names[i].get_attribute("innerHTML").strip() == "Peso":
                        tablet_characteristics.append(Caracteristica("Peso",
                                                                    specifications_values[i].get_attribute("innerHTML").strip()))

                except IndexError:
                    print("IndexError captured...")

            producto.caracteristicas = tablet_characteristics

            # comments_buttons = driver.find_elements(By.CSS_SELECTOR, "button.show-more-click")
            #
            # if comments_buttons:
            #     comments_button = comments_buttons[0]
            #
            #     driver.execute_script("arguments[0].click();", comments_button)
            #
            #     time.sleep(10)
            #
            #     comments_html = driver.find_elements(By.TAG_NAME, "html")
            #
            #     driver.execute_script("arguments[0].scrollTop += 1000", comments_html[1])
            #
            #     time.sleep(10)

            comments_text = driver.find_elements(By.CSS_SELECTOR, "p.ui-review-capability-comments__comment__content")

            tablet_comments = []
            for comment in comments_text:
                tablet_comments.append(Comentario("", comment.text.strip()))

            producto.comentarios = tablet_comments

            result = insert_product(producto.to_dict())

            if result.upserted_id is not None or result.modified_count > 0:
                tablet_scraper_counter += 1

        except Exception as e:
            print("an exception occurred in " + tablet_url, e)

    print("tablet scraping complete. Total: " + str(tablet_scraper_counter))

    return tablet_scraper_counter
