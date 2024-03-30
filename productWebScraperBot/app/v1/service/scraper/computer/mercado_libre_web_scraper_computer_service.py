import time

from selenium.webdriver.common.by import By

from app.v1.config.mongodb_config import insert_product
from app.v1.model.characteristic_model import Caracteristica
from app.v1.model.comment_model import Comentario
from app.v1.model.product_model import Producto


def mercado_web_scraper_bot(driver):
    mercado_libre_computer_url = "https://listado.mercadolibre.com.co/_Container_landing-computacion-portatilestudio#deal_print_id=ada3ec40-ee11-11ee-9247-2d03708ae4cf&c_id=special-withoutlabel&c_element_order=2&c_campaign=EP3PORTATILESTUDIOTRABAJO&c_uid=ada3ec40-ee11-11ee-9247-2d03708ae4cf"
    computer_scraper_counter = 0

    print("scraping computers from mercado libre...")

    driver.get(mercado_libre_computer_url)
    time.sleep(1)

    links_computers = driver.find_elements(By.CSS_SELECTOR,
                                            "a.ui-search-item__group__element.ui-search-link__title-card.ui-search-link")

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

                    elif specifications_names[i].get_attribute("innerHTML").strip() == "Tarjeta gráfica":
                        computer_characteristics.append(Caracteristica("Tarjeta gráfica",
                                                                    specifications_values[i].get_attribute("innerHTML").strip()))

                    elif specifications_names[i].get_attribute("innerHTML").strip() == "Marca del procesador":
                        computer_characteristics.append(Caracteristica("Marca del procesador",
                                                                    specifications_values[i].get_attribute("innerHTML").strip()))

                    elif specifications_names[i].get_attribute("innerHTML").strip() == "Línea del procesador":
                        computer_characteristics.append(Caracteristica("Línea del procesador",
                                                                    specifications_values[i].get_attribute("innerHTML").strip()))

                    elif specifications_names[i].get_attribute("innerHTML").strip() == "Cantidad de núcleos":
                        computer_characteristics.append(Caracteristica("Cantidad de núcleos",
                                                                    specifications_values[i].get_attribute("innerHTML").strip()))

                    elif specifications_names[i].get_attribute("innerHTML").strip() == "Velocidad máxima del procesador":
                        computer_characteristics.append(Caracteristica("Velocidad máxima del procesador",
                                                                    specifications_values[i].get_attribute("innerHTML").strip()))

                    elif specifications_names[i].get_attribute("innerHTML").strip() == "Velocidad mínima del procesador":
                        computer_characteristics.append(Caracteristica("Velocidad mínima del procesador",
                                                                    specifications_values[i].get_attribute("innerHTML").strip()))

                    elif specifications_names[i].get_attribute("innerHTML").strip() == "Memoria RAM":
                        computer_characteristics.append(Caracteristica("Memoria RAM",
                                                                    specifications_values[i].get_attribute("innerHTML").strip()))

                    elif specifications_names[i].get_attribute("innerHTML").strip() == "Tipo de memoria RAM":
                        computer_characteristics.append(Caracteristica("Tipo de memoria RAM",
                                                                    specifications_values[i].get_attribute("innerHTML").strip()))

                    elif specifications_names[i].get_attribute("innerHTML").strip() == "Capacidad de disco SSD":
                        computer_characteristics.append(Caracteristica("Capacidad de disco SSD",
                                                                    specifications_values[i].get_attribute("innerHTML").strip()))

                    elif specifications_names[i].get_attribute("innerHTML").strip() == "Frecuencia de actualización de la pantalla":
                        computer_characteristics.append(Caracteristica("Frecuencia de actualización de la pantalla",
                                                                    specifications_values[i].get_attribute("innerHTML").strip()))

                    elif specifications_names[i].get_attribute("innerHTML").strip() == "Tamaño de la pantalla":
                        computer_characteristics.append(Caracteristica("Tamaño de la pantalla",
                                                                    specifications_values[i].get_attribute("innerHTML").strip()))

                    elif specifications_names[i].get_attribute("innerHTML").strip() == "Tipo de pantalla":
                        computer_characteristics.append(Caracteristica("Tipo de pantalla",
                                                                    specifications_values[i].get_attribute("innerHTML").strip()))

                    elif specifications_names[i].get_attribute("innerHTML").strip() == "Nombre del sistema operativo":
                        computer_characteristics.append(Caracteristica("Nombre del sistema operativo",
                                                                    specifications_values[i].get_attribute("innerHTML").strip()))

                    elif specifications_names[i].get_attribute("innerHTML").strip() == "Versión del sistema operativo":
                        computer_characteristics.append(Caracteristica("Versión del sistema operativo",
                                                                    specifications_values[i].get_attribute("innerHTML").strip()))

                    elif specifications_names[i].get_attribute("innerHTML").strip() == "Peso":
                        computer_characteristics.append(Caracteristica("Peso",
                                                                    specifications_values[i].get_attribute("innerHTML").strip()))

                    elif specifications_names[i].get_attribute("innerHTML").strip() == "Duración máxima de la batería":
                        computer_characteristics.append(Caracteristica("Duración máxima de la batería",
                                                                    specifications_values[i].get_attribute("innerHTML").strip()))

                except IndexError:
                    print("IndexError captured...")

            producto.caracteristicas = computer_characteristics

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
