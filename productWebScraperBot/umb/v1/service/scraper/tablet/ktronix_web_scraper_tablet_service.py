import time
from datetime import datetime

from selenium.webdriver.common.by import By

from umb.v1.config.mongodb_config import insert_product
from umb.v1.model.characteristic_model import Caracteristica
from umb.v1.model.comment_model import Comentario
from umb.v1.model.product_model import Producto


def ktronix_web_scraper_bot(driver):
    ktronix_base_url = "https://www.ktronix.com"
    ktronix_tablet_url = ktronix_base_url + "/computadores-tablet/tabletas-ipads/c/BI_107_KTRON"
    tablet_scraper_counter = 0

    print("scraping tablets from ktronix...")
    driver.get(ktronix_tablet_url)

    time.sleep(1)

    tablets = driver.find_elements(By.CSS_SELECTOR,
                                     "li.product__item")

    tablets_info = []

    for tablet in tablets:
        name = tablet.find_element(By.CSS_SELECTOR,
                                     "h3.product__item__top__title")

        tablet_url = ktronix_base_url + name.get_attribute("data-url")

        brand = tablet.find_element(By.CSS_SELECTOR,
                                      "div.product__item__information__brand").text

        tablet_info = {
            "tablet_url": tablet_url,
            "brand": brand
        }

        tablets_info.append(tablet_info)

    for tablet_info in tablets_info:

        try:
            driver.get(tablet_info["tablet_url"])

            producto = Producto()

            producto_nombre = (driver.find_element(By.CSS_SELECTOR,
                                                   "div.new-container__header__title").text
                               .replace("\"", ""))

            producto.nombre = producto_nombre

            producto.url = tablet_info["tablet_url"]

            producto.marca = tablet_info["brand"]

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

            tablet_characteristics = []
            for i in range(len(characteristics_features)):

                if characteristics_features[i].text.__contains__("Linea Modelo Referencia"):
                    tablet_characteristics.append(Caracteristica("Linea Modelo Referencia",
                                                                   characteristics_results[i].text.strip()))

                elif characteristics_features[i].text.__contains__("Memoria Interna"):
                    tablet_characteristics.append(Caracteristica("Memoria Interna",
                                                                   characteristics_results[i].text.strip()))

                elif characteristics_features[i].text.__contains__("Memoria RAM"):
                    tablet_characteristics.append(Caracteristica("Memoria RAM",
                                                                   characteristics_results[i].text.strip()))


                elif characteristics_features[i].text.__contains__("Marca del Procesador"):
                    tablet_characteristics.append(Caracteristica("Marca del Procesador",
                                                                   characteristics_results[i].text.strip()))

                elif characteristics_features[i].text.__contains__("Sistema Operativo"):
                    tablet_characteristics.append(Caracteristica("Sistema Operativo",
                                                                   characteristics_results[i].text.strip()))


                elif characteristics_features[i].text.__contains__("Version Sistema Operativo"):
                    tablet_characteristics.append(Caracteristica("Version Sistema Operativo",
                                                                   characteristics_results[i].text.strip()))

                elif characteristics_features[i].text.strip() == "Velocidad del Procesador":
                    tablet_characteristics.append(Caracteristica("Velocidad del Procesador",
                                                                   characteristics_results[i].text.strip()))

                elif characteristics_features[i].text.__contains__("Nucleos del Procesador"):
                    tablet_characteristics.append(Caracteristica("Nucleos del Procesador",
                                                                   characteristics_results[i].text.strip()))


                elif characteristics_features[i].text.__contains__("Tamaño Pantalla"):
                    tablet_characteristics.append(Caracteristica("Tamaño Pantalla",
                                                                   characteristics_results[i].text.strip()))

                elif characteristics_features[i].text.__contains__("Resolucion Pantalla"):
                    tablet_characteristics.append(Caracteristica("Resolucion Pantalla",
                                                                   characteristics_results[i].text.strip()))


                elif characteristics_features[i].text.__contains__("Tipos de Puertos Entradas y Salidas"):
                    tablet_characteristics.append(Caracteristica("Tipos de Puertos Entradas y Salidas",
                                                                   characteristics_results[i].text.strip()))

                elif characteristics_features[i].text.__contains__("Conexion a Datos"):
                    tablet_characteristics.append(Caracteristica("Conexion a Datos",
                                                                   characteristics_results[i].text.strip()))

                elif characteristics_features[i].text.__contains__("Opciones de Conectividad"):
                    tablet_characteristics.append(Caracteristica("Opciones de Conectividad",
                                                                   characteristics_results[i].text.strip()))


                elif characteristics_features[i].text.__contains__("Resolucion Camara Frontal"):
                    tablet_characteristics.append(Caracteristica("Resolucion Camara Frontal",
                                                                   characteristics_results[i].text.strip()))

                elif characteristics_features[i].text.__contains__("Resolucion Camara Posterior"):
                    tablet_characteristics.append(Caracteristica("Resolucion Camara Posterior",
                                                                   characteristics_results[i].text.strip()))


                elif characteristics_features[i].text.__contains__("Duracion de la Bateria"):
                    tablet_characteristics.append(Caracteristica("Duracion de la Bateria",
                                                                   characteristics_results[i].text.strip()))

            producto.caracteristicas = tablet_characteristics

            username_comments = driver.find_elements(By.CSS_SELECTOR,
                                                     "span.yotpo-user-name")
            comment_titles = driver.find_elements(By.CSS_SELECTOR, "div.content-title")
            comment_texts = driver.find_elements(By.CSS_SELECTOR, "div.content-review")

            tablet_comments = []
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
                tablet_comments.append(comentario)

            producto.comentarios = tablet_comments
            producto.categoria = 'tablet'
            producto.plataforma = "ktronix"
            producto.id = producto_nombre + "-ktronix"
            producto.created_or_updated_at = datetime.now()

            result = insert_product(producto.to_dict())

            if result.upserted_id is not None or result.modified_count > 0:
                tablet_scraper_counter += 1

        except Exception as e:
            print("an exception occurred in " + tablet_info["tablet_url"], e)

    print("tablet scraping complete. Total: " + str(tablet_scraper_counter))

    return tablet_scraper_counter
