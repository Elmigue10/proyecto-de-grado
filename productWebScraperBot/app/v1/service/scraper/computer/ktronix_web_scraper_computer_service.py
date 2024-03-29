import time
from datetime import datetime

from selenium.webdriver.common.by import By

from app.v1.config.mongodb_config import insert_product
from app.v1.model.characteristic_model import Caracteristica
from app.v1.model.comment_model import Comentario
from app.v1.model.product_model import Producto


def ktronix_web_scraper_bot(driver):
    ktronix_base_url = "https://www.ktronix.com"
    ktronix_computer_url = ktronix_base_url + "/computadores-tablet/computadores-portatiles/c/BI_104_KTRON"
    computer_scraper_counter = 0

    print("scraping computers from ktronix...")
    driver.get(ktronix_computer_url)
    
    time.sleep(1)
    
    computers = driver.find_elements(By.CSS_SELECTOR,
                                  "li.product__item")

    computers_info = []

    for computer in computers:
        name = computer.find_element(By.CSS_SELECTOR,
                                  "h3.product__item__top__title")

        computer_url = ktronix_base_url + name.get_attribute("data-url")

        brand = computer.find_element(By.CSS_SELECTOR,
                                   "div.product__item__information__brand").text

        computer_info = {
            "computer_url": computer_url,
            "brand": brand
        }

        computers_info.append(computer_info)

    for computer_info in computers_info:

        try:
            driver.get(computer_info["computer_url"])

            producto = Producto()

            producto_nombre = (driver.find_element(By.CSS_SELECTOR,
                                                   "div.new-container__header__title").text
                               .replace("\"", ""))

            producto.nombre = producto_nombre

            producto.url = computer_info["computer_url"]

            producto.marca = computer_info["brand"]

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

            computer_characteristics = []
            for i in range(len(characteristics_features)):

                if characteristics_features[i].text.__contains__("Linea Modelo Referencia"):
                    computer_characteristics.append(Caracteristica("Linea Modelo Referencia",
                                                                characteristics_results[i].text.strip()))

                elif characteristics_features[i].text.__contains__("Memoria RAM"):
                    computer_characteristics.append(Caracteristica("Memoria RAM",
                                                                characteristics_results[i].text.strip()))

                elif characteristics_features[i].text.__contains__("Capacidad de Disco"):
                    computer_characteristics.append(Caracteristica("Capacidad de Disco",
                                                                characteristics_results[i].text.strip()))


                elif characteristics_features[i].text.__contains__("Tipos de Discos que Incluye"):
                    computer_characteristics.append(Caracteristica("Tipos de Discos que Incluye",
                                                                characteristics_results[i].text.strip()))

                elif characteristics_features[i].text.__contains__("Sistema Operativo"):
                    computer_characteristics.append(Caracteristica("Sistema Operativo",
                                                                characteristics_results[i].text.strip()))


                elif characteristics_features[i].text.__contains__("Version Sistema Operativo"):
                    computer_characteristics.append(Caracteristica("Version Sistema Operativo",
                                                                characteristics_results[i].text.strip()))

                elif characteristics_features[i].text.strip() == "Procesador":
                    computer_characteristics.append(Caracteristica("Procesador",
                                                                characteristics_results[i].text.strip()))

                elif characteristics_features[i].text.__contains__("Velocidad del Procesador"):
                    computer_characteristics.append(Caracteristica("Velocidad del Procesador",
                                                                characteristics_results[i].text.strip()))


                elif characteristics_features[i].text.__contains__("Nucleos del Procesador"):
                    computer_characteristics.append(Caracteristica("Nucleos del Procesador",
                                                                characteristics_results[i].text.strip()))

                elif characteristics_features[i].text.__contains__("Marca del Procesador"):
                    computer_characteristics.append(Caracteristica("Marca del Procesador",
                                                                characteristics_results[i].text.strip()))


                elif characteristics_features[i].text.__contains__("Modelo del Procesador"):
                    computer_characteristics.append(Caracteristica("Modelo del Procesador",
                                                                characteristics_results[i].text.strip()))

                elif characteristics_features[i].text.__contains__("Marca Tarjeta de Video/Grafica"):
                    computer_characteristics.append(Caracteristica("Marca Tarjeta de Video/Grafica",
                                                                characteristics_results[i].text.strip()))

                elif characteristics_features[i].text.__contains__("Tarjeta Grafica"):
                    computer_characteristics.append(Caracteristica("Tarjeta Grafica",
                                                                characteristics_results[i].text.strip()))


                elif characteristics_features[i].text.__contains__("Modelo Tarjeta de Video/Grafica"):
                    computer_characteristics.append(Caracteristica("Modelo Tarjeta de Video/Grafica",
                                                                characteristics_results[i].text.strip()))

                elif characteristics_features[i].text.__contains__("Tamaño Pantalla"):
                    computer_characteristics.append(Caracteristica("Tamaño Pantalla",
                                                                characteristics_results[i].text.strip()))


                elif characteristics_features[i].text.__contains__("Tipos de Puertos Entradas y Salidas"):
                    computer_characteristics.append(Caracteristica("Tipos de Puertos Entradas y Salidas",
                                                                characteristics_results[i].text.strip()))

                elif characteristics_features[i].text.__contains__("Opciones de Conectividad"):
                    computer_characteristics.append(Caracteristica("Opciones de Conectividad",
                                                                characteristics_results[i].text.strip()))

                elif characteristics_features[i].text.__contains__("Peso"):
                    computer_characteristics.append(Caracteristica("Peso",
                                                                characteristics_results[i].text.strip()))

                elif characteristics_features[i].text.__contains__("Duracion de la Bateria"):
                    computer_characteristics.append(Caracteristica("Duracion de la Bateria",
                                                                characteristics_results[i].text.strip()))

                elif characteristics_features[i].text.__contains__("Resolucion Pantalla"):
                    computer_characteristics.append(Caracteristica("Resolucion Pantalla",
                                                                characteristics_results[i].text.strip()))

            producto.caracteristicas = computer_characteristics

            username_comments = driver.find_elements(By.CSS_SELECTOR,
                                                     "span.yotpo-user-name")
            comment_titles = driver.find_elements(By.CSS_SELECTOR, "div.content-title")
            comment_texts = driver.find_elements(By.CSS_SELECTOR, "div.content-review")

            computer_comments = []
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
                computer_comments.append(comentario)

            producto.comentarios = computer_comments
            producto.categoria = 'computer'
            producto.plataforma = "ktronix"
            producto.id = producto_nombre + "-ktronix"
            producto.created_or_updated_at = datetime.now()

            result = insert_product(producto.to_dict())

            if result.upserted_id is not None or result.modified_count > 0:
                computer_scraper_counter += 1

        except Exception as e:
            print("an exception occurred in " + computer_info["computer_url"], e)

    print("computer scraping complete. Total: " + str(computer_scraper_counter))

    return computer_scraper_counter
