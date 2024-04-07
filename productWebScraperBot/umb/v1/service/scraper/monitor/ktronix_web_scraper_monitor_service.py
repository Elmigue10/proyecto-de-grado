import time
from datetime import datetime

from selenium.webdriver.common.by import By

from umb.v1.config.mongodb_config import insert_product
from umb.v1.model.characteristic_model import Caracteristica
from umb.v1.model.comment_model import Comentario
from umb.v1.model.product_model import Producto


def ktronix_web_scraper_bot(driver):
    ktronix_base_url = "https://www.ktronix.com"
    ktronix_monitor_url = ktronix_base_url + "/computadores-tablet/monitores/c/BI_110_KTRON"
    monitor_scraper_counter = 0

    print("scraping monitors from ktronix...")
    driver.get(ktronix_monitor_url)

    time.sleep(1)

    monitors = driver.find_elements(By.CSS_SELECTOR,
                                    "li.product__item")

    monitors_info = []

    for monitor in monitors:
        name = monitor.find_element(By.CSS_SELECTOR,
                                    "h3.product__item__top__title")

        monitor_url = ktronix_base_url + name.get_attribute("data-url")

        brand = monitor.find_element(By.CSS_SELECTOR,
                                     "div.product__item__information__brand").text

        monitor_info = {
            "monitor_url": monitor_url,
            "brand": brand
        }

        monitors_info.append(monitor_info)

    for monitor_info in monitors_info:

        try:
            driver.get(monitor_info["monitor_url"])

            producto = Producto()

            producto_nombre = (driver.find_element(By.CSS_SELECTOR,
                                                   "div.new-container__header__title").text
                               .replace("\"", ""))

            producto.nombre = producto_nombre

            producto.url = monitor_info["monitor_url"]

            producto.marca = monitor_info["brand"]

            producto.precio = ((driver.find_element(By.ID, "js-original_price").text
                                .replace("$", ""))
                               .replace(".", "")
                               .replace("\nHoy", ""))

            producto.imagen_url = (driver.find_element(By.CSS_SELECTOR, "img.owl-lazy.js-zoom-desktop-new")
                                   .get_attribute("src"))

            characteristics_features = driver.find_elements(By.CSS_SELECTOR,
                                                            "div.new-container__table__classifications___type__item_feature")

            characteristics_results = driver.find_elements(By.CSS_SELECTOR,
                                                           "div.new-container__table__classifications___type__item_result")

            producto_id = producto_nombre + "-"

            monitor_characteristics = []
            for i in range(len(characteristics_features)):

                if characteristics_features[i].text.__contains__("Linea Modelo Referencia"):
                    monitor_characteristics.append(Caracteristica("Linea Modelo Referencia",
                                                                  characteristics_results[i].text.strip()))

                elif characteristics_features[i].text.__contains__("Tipo de Pantalla"):
                    monitor_characteristics.append(Caracteristica("Tipo de Pantalla",
                                                                  characteristics_results[i].text.strip()))

                elif characteristics_features[i].text.__contains__("Resolucion Pantalla"):
                    monitor_characteristics.append(Caracteristica("Resolucion Pantalla",
                                                                  characteristics_results[i].text.strip()))


                elif characteristics_features[i].text.__contains__("Diseño de la pantalla"):
                    monitor_characteristics.append(Caracteristica("Diseño de la pantalla",
                                                                  characteristics_results[i].text.strip()))

                elif characteristics_features[i].text.__contains__("Tamaño Pantalla"):
                    monitor_characteristics.append(Caracteristica("Tamaño Pantalla",
                                                                  characteristics_results[i].text.strip()))


                elif characteristics_features[i].text.__contains__("Contraste"):
                    monitor_characteristics.append(Caracteristica("Contraste",
                                                                  characteristics_results[i].text.strip()))

                elif characteristics_features[i].text.strip() == "Velocidad de Respuesta del Monitor":
                    monitor_characteristics.append(Caracteristica("Velocidad de Respuesta del Monitor",
                                                                  characteristics_results[i].text.strip()))

                elif characteristics_features[i].text.__contains__("Tipos de Puertos Entradas y Salidas"):
                    monitor_characteristics.append(Caracteristica("Tipos de Puertos Entradas y Salidas",
                                                                  characteristics_results[i].text.strip()))


                elif characteristics_features[i].text.__contains__("Otras Tecnologias de Conectividad"):
                    monitor_characteristics.append(Caracteristica("Otras Tecnologias de Conectividad",
                                                                  characteristics_results[i].text.strip()))

                elif characteristics_features[i].text.__contains__("Opciones de Conectividad"):
                    monitor_characteristics.append(Caracteristica("Opciones de Conectividad",
                                                                  characteristics_results[i].text.strip()))


                elif characteristics_features[i].text.__contains__("Uso"):
                    monitor_characteristics.append(Caracteristica("Uso",
                                                                  characteristics_results[i].text.strip()))

                elif characteristics_features[i].text.__contains__("Qué incluye el producto"):
                    monitor_characteristics.append(Caracteristica("Qué incluye el producto",
                                                                  characteristics_results[i].text.strip()))

                elif characteristics_features[i].text.__contains__("Garantía"):
                    monitor_characteristics.append(Caracteristica("Garantía",
                                                                  characteristics_results[i].text.strip()))

            producto.caracteristicas = monitor_characteristics

            username_comments = driver.find_elements(By.CSS_SELECTOR,
                                                     "span.yotpo-user-name")
            comment_titles = driver.find_elements(By.CSS_SELECTOR, "div.content-title")
            comment_texts = driver.find_elements(By.CSS_SELECTOR, "div.content-review")

            monitor_comments = []
            for i in range(len(username_comments)):
                comentario = Comentario()

                try:
                    comentario.username = username_comments[i].text.strip()
                except IndexError:
                    print("IndexError captured...")

                comment_text = ""

                try:
                    comment_text += comment_titles[i].text.strip() + ". "
                except IndexError:
                    print("IndexError captured...")

                try:
                    comment_text += comment_texts[i].text.strip()
                except IndexError:
                    print("IndexError captured...")

                comentario.content = comment_text
                monitor_comments.append(comentario)

            producto.comentarios = monitor_comments
            producto.categoria = 'monitor'
            producto.plataforma = "ktronix"
            producto.id = producto_nombre + "-ktronix"
            producto.created_or_updated_at = datetime.now()

            result = insert_product(producto.to_dict())

            if result.upserted_id is not None or result.modified_count > 0:
                monitor_scraper_counter += 1

        except Exception as e:
            print("an exception occurred in " + monitor_info["monitor_url"], e)

    print("monitor scraping complete. Total: " + str(monitor_scraper_counter))

    return monitor_scraper_counter
