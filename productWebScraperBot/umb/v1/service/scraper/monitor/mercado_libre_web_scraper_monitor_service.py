import time
from datetime import datetime

from selenium.webdriver.common.by import By

from umb.v1.config.mongodb_config import insert_product
from umb.v1.model.characteristic_model import Caracteristica
from umb.v1.model.comment_model import Comentario
from umb.v1.model.product_model import Producto


def mercado_web_scraper_bot(driver):
    mercado_libre_monitor_url = "https://www.mercadolibre.com.co/mas-vendidos/MCO1656?new_bestseller_landing=false#origin=pdp"
    monitor_scraper_counter = 0

    print("scraping monitors from mercado libre...")

    driver.get(mercado_libre_monitor_url)
    time.sleep(1)

    links_monitors = driver.find_elements(By.CSS_SELECTOR,
                                            "a.ui-recommendations-card__link")

    monitors_urls = []
    for link_monitor in links_monitors:
        monitors_urls.append(link_monitor.get_attribute("href"))

    for monitor_url in monitors_urls:

        try:
            driver.get(monitor_url)

            producto = Producto()

            nombre = driver.find_element(By.CSS_SELECTOR, "h1.ui-pdp-title").text.strip()

            producto.nombre = nombre

            producto.id = nombre + "-mercado-libre"

            producto.url = monitor_url

            imagen_url = (driver.find_element(By.CSS_SELECTOR, "img.ui-pdp-image.ui-pdp-gallery__figure__image")
                          .get_attribute("src"))

            producto.imagen_url = imagen_url

            precio = (driver.find_element(By.CSS_SELECTOR, "span.andes-money-amount__fraction").text
                      .strip().replace(".", ""))

            producto.precio = precio

            producto.categoria = "monitor"

            producto.plataforma = "mercado-libre"
            producto.created_or_updated_at = datetime.now()

            specifications_names = driver.find_elements(By.CSS_SELECTOR, "div.andes-table__header__container")
            specifications_values = driver.find_elements(By.CSS_SELECTOR, "span.andes-table__column--value")

            monitor_characteristics = []
            for i in range(len(specifications_names)):

                try:
                    if specifications_names[i].get_attribute("innerHTML").strip() == "Marca":
                        producto.marca = specifications_values[i].get_attribute("innerHTML").strip()

                    elif specifications_names[i].get_attribute("innerHTML").strip() == "Modelo":
                        monitor_characteristics.append(Caracteristica("Modelo",
                                                                    specifications_values[i].get_attribute("innerHTML").strip()))

                    elif specifications_names[i].get_attribute("innerHTML").strip() == "Voltaje":
                        monitor_characteristics.append(Caracteristica("Voltaje",
                                                                    specifications_values[i].get_attribute("innerHTML").strip()))

                    elif specifications_names[i].get_attribute("innerHTML").strip() == "Tamaño de la pantalla":
                        monitor_characteristics.append(Caracteristica("Tamaño de la pantalla",
                                                                    specifications_values[i].get_attribute("innerHTML").strip()))

                    elif specifications_names[i].get_attribute("innerHTML").strip() == "Resolución de la pantalla":
                        monitor_characteristics.append(Caracteristica("Resolución de la pantalla",
                                                                    specifications_values[i].get_attribute("innerHTML").strip()))

                    elif specifications_names[i].get_attribute("innerHTML").strip() == "Frecuencia de actualización recomendada":
                        monitor_characteristics.append(Caracteristica("Frecuencia de actualización recomendada",
                                                                    specifications_values[i].get_attribute("innerHTML").strip()))

                    elif specifications_names[i].get_attribute("innerHTML").strip() == "Frecuencia máxima de actualización":
                        monitor_characteristics.append(Caracteristica("Frecuencia máxima de actualización",
                                                                    specifications_values[i].get_attribute("innerHTML").strip()))

                    elif specifications_names[i].get_attribute("innerHTML").strip() == "Tipo de resolución":
                        monitor_characteristics.append(Caracteristica("Tipo de resolución",
                                                                    specifications_values[i].get_attribute("innerHTML").strip()))

                    elif specifications_names[i].get_attribute("innerHTML").strip() == "Tipo de pantalla":
                        monitor_characteristics.append(Caracteristica("Tipo de pantalla",
                                                                    specifications_values[i].get_attribute("innerHTML").strip()))

                    elif specifications_names[i].get_attribute("innerHTML").strip() == "Relación de aspecto":
                        monitor_characteristics.append(Caracteristica("Relación de aspecto",
                                                                    specifications_values[i].get_attribute("innerHTML").strip()))

                    elif specifications_names[i].get_attribute("innerHTML").strip() == "Contraste":
                        monitor_characteristics.append(Caracteristica("Contraste",
                                                                    specifications_values[i].get_attribute("innerHTML").strip()))

                    elif specifications_names[i].get_attribute("innerHTML").strip() == "Cantidad de colores de la pantalla":
                        monitor_characteristics.append(Caracteristica("Cantidad de colores de la pantalla",
                                                                    specifications_values[i].get_attribute("innerHTML").strip()))

                    elif specifications_names[i].get_attribute("innerHTML").strip() == "Brillo":
                        monitor_characteristics.append(Caracteristica("Brillo",
                                                                    specifications_values[i].get_attribute("innerHTML").strip()))

                    elif specifications_names[i].get_attribute("innerHTML").strip() == "Tiempo de respuesta GTG":
                        monitor_characteristics.append(Caracteristica("Tiempo de respuesta GTG",
                                                                    specifications_values[i].get_attribute("innerHTML").strip()))

                    elif specifications_names[i].get_attribute("innerHTML").strip() == "Tiempo de respuesta MPRT":
                        monitor_characteristics.append(Caracteristica("Tiempo de respuesta MPRT",
                                                                    specifications_values[i].get_attribute("innerHTML").strip()))

                    elif specifications_names[i].get_attribute("innerHTML").strip() == "Ángulo de visión horizontal":
                        monitor_characteristics.append(Caracteristica("Ángulo de visión horizontal",
                                                                    specifications_values[i].get_attribute("innerHTML").strip()))

                    elif specifications_names[i].get_attribute("innerHTML").strip() == "Ángulo de visión vertical":
                        monitor_characteristics.append(Caracteristica("Ángulo de visión vertical",
                                                                    specifications_values[i].get_attribute("innerHTML").strip()))

                    elif specifications_names[i].get_attribute("innerHTML").strip() == "Tecnologías de sincronización":
                        monitor_characteristics.append(Caracteristica("Tecnologías de sincronización",
                                                                    specifications_values[i].get_attribute("innerHTML").strip()))

                    elif specifications_names[i].get_attribute("innerHTML").strip() == "Tipo de panel":
                        monitor_characteristics.append(Caracteristica("Tipo de panel",
                                                                    specifications_values[i].get_attribute("innerHTML").strip()))

                    elif specifications_names[i].get_attribute("innerHTML").strip() == "Cables incluidos":
                        monitor_characteristics.append(Caracteristica("Cables incluidos",
                                                                    specifications_values[i].get_attribute("innerHTML").strip()))

                    elif specifications_names[i].get_attribute("innerHTML").strip() == "Peso con soporte":
                        monitor_characteristics.append(Caracteristica("Peso con soporte",
                                                                    specifications_values[i].get_attribute("innerHTML").strip()))

                    elif specifications_names[i].get_attribute("innerHTML").strip() == "Peso sin soporte":
                        monitor_characteristics.append(Caracteristica("Peso sin soporte",
                                                                    specifications_values[i].get_attribute("innerHTML").strip()))

                    elif specifications_names[i].get_attribute("innerHTML").strip() == "Altura con soporte":
                        monitor_characteristics.append(Caracteristica("Altura con soporte",
                                                                    specifications_values[i].get_attribute("innerHTML").strip()))

                    elif specifications_names[i].get_attribute("innerHTML").strip() == "Altura sin soporte":
                        monitor_characteristics.append(Caracteristica("Altura sin soporte",
                                                                    specifications_values[i].get_attribute("innerHTML").strip()))

                except IndexError:
                    print("IndexError captured...")

            producto.caracteristicas = monitor_characteristics

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

            monitor_comments = []
            for comment in comments_text:
                monitor_comments.append(Comentario("", comment.text.strip()))

            producto.comentarios = monitor_comments

            result = insert_product(producto.to_dict())

            if result.upserted_id is not None or result.modified_count > 0:
                monitor_scraper_counter += 1

        except Exception as e:
            print("an exception occurred in " + monitor_url, e)

    print("monitor scraping complete. Total: " + str(monitor_scraper_counter))

    return monitor_scraper_counter
