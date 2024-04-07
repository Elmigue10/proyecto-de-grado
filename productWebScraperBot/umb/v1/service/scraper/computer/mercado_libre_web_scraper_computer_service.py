import time
from datetime import datetime

from selenium.webdriver.common.by import By

from umb.v1.config.mongodb_config import insert_product
from umb.v1.model.characteristic_model import Caracteristica
from umb.v1.model.comment_model import Comentario
from umb.v1.model.product_model import Producto


def mercado_web_scraper_bot(driver):
    mercado_libre_computer_url = "https://www.mercadolibre.com.co/mas-vendidos/MCO1652?new_bestseller_landing=false#origin=pdp"
    computer_scraper_counter = 0

    print("scraping computers from mercado libre...")

    driver.get(mercado_libre_computer_url)
    time.sleep(1)

    links_computers = driver.find_elements(By.CSS_SELECTOR,
                                            "a.ui-recommendations-card__link")

    computers_urls = []
    for link_computer in links_computers:
        computers_urls.append(link_computer.get_attribute("href"))

    for computer_url in computers_urls:

        try:
            driver.get(computer_url)

            producto = Producto()

            nombre = driver.find_element(By.CSS_SELECTOR, "h1.ui-pdp-title").text.strip()

            producto.nombre = nombre

            producto.id = nombre + "-mercado-libre"

            producto.url = computer_url

            imagen_url = (driver.find_element(By.CSS_SELECTOR, "img.ui-pdp-image.ui-pdp-gallery__figure__image")
                          .get_attribute("src"))

            producto.imagen_url = imagen_url

            precio = (driver.find_element(By.CSS_SELECTOR, "span.andes-money-amount__fraction").text
                      .strip().replace(".", ""))

            producto.precio = precio

            producto.categoria = "computer"

            producto.plataforma = "mercado-libre"
            producto.created_or_updated_at = datetime.now()

            specifications_names = driver.find_elements(By.CSS_SELECTOR, "div.andes-table__header__container")
            specifications_values = driver.find_elements(By.CSS_SELECTOR, "span.andes-table__column--value")

            computer_characteristics = []
            for i in range(len(specifications_names)):

                try:
                    if specifications_names[i].get_attribute("innerHTML").strip() == "Marca":
                        producto.marca = specifications_values[i].get_attribute("innerHTML").strip()

                    elif specifications_names[i].get_attribute("innerHTML").strip() == "Línea":
                        computer_characteristics.append(Caracteristica("Línea",
                                                                    specifications_values[i].get_attribute("innerHTML").strip()))

                    elif specifications_names[i].get_attribute("innerHTML").strip() == "Modelo":
                        computer_characteristics.append(Caracteristica("Modelo",
                                                                    specifications_values[i].get_attribute("innerHTML").strip()))

                    elif specifications_names[i].get_attribute("innerHTML").strip() == "Nombre del sistema operativo":
                        computer_characteristics.append(Caracteristica("Nombre del sistema operativo",
                                                                    specifications_values[i].get_attribute("innerHTML").strip()))

                    elif specifications_names[i].get_attribute("innerHTML").strip() == "Versión original del sistema operativo":
                        computer_characteristics.append(Caracteristica("Versión original del sistema operativo",
                                                                    specifications_values[i].get_attribute("innerHTML").strip()))

                    elif specifications_names[i].get_attribute("innerHTML").strip() == "Memoria interna":
                        computer_characteristics.append(Caracteristica("Memoria interna",
                                                                    specifications_values[i].get_attribute("innerHTML").strip()))

                    elif specifications_names[i].get_attribute("innerHTML").strip() == "Memoria RAM":
                        computer_characteristics.append(Caracteristica("Memoria RAM",
                                                                    specifications_values[i].get_attribute("innerHTML").strip()))

                    elif specifications_names[i].get_attribute("innerHTML").strip() == "Red móvil":
                        computer_characteristics.append(Caracteristica("Red móvil",
                                                                    specifications_values[i].get_attribute("innerHTML").strip()))

                    elif specifications_names[i].get_attribute("innerHTML").strip() == "Tipo de conector de carga":
                        computer_characteristics.append(Caracteristica("Tipo de conector de carga",
                                                                    specifications_values[i].get_attribute("innerHTML").strip()))

                    elif specifications_names[i].get_attribute("innerHTML").strip() == "Modelo del procesador":
                        computer_characteristics.append(Caracteristica("Modelo del procesador",
                                                                    specifications_values[i].get_attribute("innerHTML").strip()))

                    elif specifications_names[i].get_attribute("innerHTML").strip() == "Velocidad del procesador":
                        computer_characteristics.append(Caracteristica("Velocidad del procesador",
                                                                    specifications_values[i].get_attribute("innerHTML").strip()))

                    elif specifications_names[i].get_attribute("innerHTML").strip() == "Cantidad de núcleos del procesador":
                        computer_characteristics.append(Caracteristica("Cantidad de núcleos del procesador",
                                                                    specifications_values[i].get_attribute("innerHTML").strip()))

                    elif specifications_names[i].get_attribute("innerHTML").strip() == "Resolución de la cámara trasera principal":
                        computer_characteristics.append(Caracteristica("Resolución de la cámara trasera principal",
                                                                    specifications_values[i].get_attribute("innerHTML").strip()))

                    elif specifications_names[i].get_attribute("innerHTML").strip() == "Resolución de la cámara frontal principal":
                        computer_characteristics.append(Caracteristica("Resolución de la cámara frontal principal",
                                                                    specifications_values[i].get_attribute("innerHTML").strip()))

                    elif specifications_names[i].get_attribute("innerHTML").strip() == "Tamaño de la pantalla":
                        computer_characteristics.append(Caracteristica("Tamaño de la pantalla",
                                                                    specifications_values[i].get_attribute("innerHTML").strip()))

                    elif specifications_names[i].get_attribute("innerHTML").strip() == "Capacidad de la batería":
                        computer_characteristics.append(Caracteristica("Capacidad de la batería",
                                                                    specifications_values[i].get_attribute("innerHTML").strip()))

                    elif specifications_names[i].get_attribute("innerHTML").strip() == "Altura x Ancho x Profundidad":
                        computer_characteristics.append(Caracteristica("Altura x Ancho x Profundidad",
                                                                    specifications_values[i].get_attribute("innerHTML").strip()))
                except IndexError:
                    print("IndexError captured...")

            producto.caracteristicas = computer_characteristics

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

            computer_comments = []
            for comment in comments_text:
                computer_comments.append(Comentario("", comment.text.strip()))

            producto.comentarios = computer_comments

            result = insert_product(producto.to_dict())

            if result.upserted_id is not None or result.modified_count > 0:
                computer_scraper_counter += 1

        except Exception as e:
            print("an exception occurred in " + computer_url, e)

    print("computer scraping complete. Total: " + str(computer_scraper_counter))

    return computer_scraper_counter
