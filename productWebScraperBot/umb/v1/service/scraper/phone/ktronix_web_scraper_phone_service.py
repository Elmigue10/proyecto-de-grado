import time
from datetime import datetime

from selenium.webdriver.common.by import By

from umb.v1.config.mongodb_config import insert_product
from umb.v1.model.characteristic_model import Caracteristica
from umb.v1.model.comment_model import Comentario
from umb.v1.model.product_model import Producto


def ktronix_web_scraper_bot(driver):
    ktronix_base_url = "https://www.ktronix.com"
    ktronix_phone_url = ktronix_base_url + "/celulares/smartphones/c/BI_101_KTRON"
    phone_scraper_counter = 0

    print("scraping phones from ktronix...")
    driver.get(ktronix_phone_url)
    time.sleep(1)
    phones = driver.find_elements(By.CSS_SELECTOR,
                                  "li.product__item")

    phones_info = []

    for phone in phones:
        name = phone.find_element(By.CSS_SELECTOR,
                                  "h3.product__item__top__title")

        phone_url = ktronix_base_url + name.get_attribute("data-url")

        brand = phone.find_element(By.CSS_SELECTOR,
                                   "div.product__item__information__brand").text

        phone_info = {
            "phone_url": phone_url,
            "brand": brand
        }

        phones_info.append(phone_info)

    for phone_info in phones_info:

        try:
            driver.get(phone_info["phone_url"])

            producto = Producto()

            producto_nombre = (driver.find_element(By.CSS_SELECTOR,
                                                  "div.new-container__header__title").text
                               .replace("\"", ""))

            producto.nombre = producto_nombre

            producto.url = phone_info["phone_url"]

            producto.marca = phone_info["brand"]

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

            has_family = False
            phone_characteristics = []
            for i in range(len(characteristics_features)):

                if characteristics_features[i].text.__contains__("Familia") and not has_family:
                    family_value = characteristics_results[i].text.strip()
                    phone_characteristics.append(Caracteristica("Familia", family_value))
                    producto_id += family_value + "-"
                    has_family = True

                elif characteristics_features[i].text.__contains__("Memoria Interna"):
                    memoria_interna_value = characteristics_results[i].text.strip()
                    phone_characteristics.append(Caracteristica("Memoria Interna", memoria_interna_value))
                    producto_id += memoria_interna_value + "-"

                elif characteristics_features[i].text.__contains__("Memoria RAM"):
                    memoria_ram_value = characteristics_results[i].text.strip()
                    phone_characteristics.append(Caracteristica("Memoria RAM", memoria_ram_value))
                    producto_id += memoria_ram_value

                elif characteristics_features[i].text.strip == "Sistema Operativo":
                    phone_characteristics.append(Caracteristica("Sistema Operativo",
                                                                characteristics_results[i].text.strip()))

                elif characteristics_features[i].text.__contains__("Version Sistema Operativo"):
                    phone_characteristics.append(Caracteristica("Version Sistema Operativo",
                                                                characteristics_results[i].text.strip()))

                elif characteristics_features[i].text.__contains__("Marca del Procesador"):
                    phone_characteristics.append(Caracteristica("Marca del Procesador",
                                                                characteristics_results[i].text.strip()))

                elif characteristics_features[i].text.__contains__("Tamaño Pantalla"):
                    phone_characteristics.append(Caracteristica("Tamaño Pantalla",
                                                                characteristics_results[i].text))

                elif characteristics_features[i].text.__contains__("Tipo de Pantalla"):
                    phone_characteristics.append(Caracteristica("Tipo de Pantalla",
                                                                characteristics_results[i].text.strip()))

                elif characteristics_features[i].text.__contains__("Resolucion Pantalla"):
                    phone_characteristics.append(Caracteristica("Resolucion Pantalla",
                                                                characteristics_results[i].text.strip()))

                elif characteristics_features[i].text.__contains__("Resolucion Camara Frontal 1"):
                    phone_characteristics.append(Caracteristica("Resolucion Camara Frontal 1",
                                                                characteristics_results[i].text))

                elif characteristics_features[i].text.__contains__("Resolucion Camara Posterior 1"):
                    phone_characteristics.append(Caracteristica("Resolucion Camara Posterior 1",
                                                                characteristics_results[i].text))

                elif characteristics_features[i].text.__contains__("Red de Transmision de Datos"):
                    phone_characteristics.append(Caracteristica("Red de Transmision de Datos",
                                                                characteristics_results[i].text.strip()
                                                                .replace(" \n", ",")))

                elif characteristics_features[i].text.__contains__("Capacidad de la Bateria"):
                    phone_characteristics.append(Caracteristica("Capacidad de la Bateria",
                                                                characteristics_results[i].text))

            producto.caracteristicas = phone_characteristics

            username_comments = driver.find_elements(By.CSS_SELECTOR,
                                                     "span.yotpo-user-name")
            comment_titles = driver.find_elements(By.CSS_SELECTOR, "div.content-title")
            comment_texts = driver.find_elements(By.CSS_SELECTOR, "div.content-review")

            phone_comments = []
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
                phone_comments.append(comentario)

            producto.comentarios = phone_comments
            producto.categoria = 'smartphone'
            producto.plataforma = "ktronix"
            producto.id = producto_id + "-ktronix"
            producto.created_or_updated_at = datetime.now()

            result = insert_product(producto.to_dict())

            if result.upserted_id is not None or result.modified_count > 0:
                phone_scraper_counter += 1

        except Exception as e:
            print("an exception occurred in " + phone_info["phone_url"], e)

    print("phone scraping complete. Total: " + str(phone_scraper_counter))

    return phone_scraper_counter
