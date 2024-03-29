import time
from datetime import datetime

from selenium.webdriver.common.by import By

from app.v1.config.mongodb_config import insert_product
from app.v1.model.characteristic_model import Caracteristica
from app.v1.model.comment_model import Comentario
from app.v1.model.product_model import Producto


def falabella_web_scraper_bot(driver):
    falabella_base_url = "https://www.falabella.com.co/falabella-co"
    falabella_phone_url = falabella_base_url + "/category/cat1660941/Celulares-y-Telefonos"
    phone_scraper_counter = 0

    print("scraping phones from falabella...")

    driver.get(falabella_phone_url)
    time.sleep(1)

    phones = driver.find_elements(By.CSS_SELECTOR, "a.pod-4_GRID.pod-link")

    phones_urls = []

    for phone in phones:
        phone_url = phone.get_attribute("href")
        phones_urls.append(phone_url)

    for phone_url in phones_urls:

        try:
            driver.get(phone_url)

            producto = Producto()

            marca = driver.find_element(By.CSS_SELECTOR, "a#pdp-product-brand-link").text.strip()
            nombre = driver.find_element(By.CSS_SELECTOR, "h1.product-name").text
            precio = (driver.find_element(By.CSS_SELECTOR, "span.copy17.primary.senary.jsx-2835692965.bold.line-height-29")
                      .text.replace("$", "")
                      .replace(".", "").strip())

            producto.id = nombre + "-falabella"
            producto.nombre = nombre
            producto.url = phone_url
            producto.marca = marca
            producto.precio = precio
            producto.categoria = "smartphone"
            producto.plataforma = "falabella"
            producto.imagen_url = driver.find_element(By.CSS_SELECTOR, "img.jsx-2487856160").get_attribute("src")
            producto.created_or_updated_at = datetime.now()

            properties_button = driver.find_elements(By.CSS_SELECTOR, "button#swatch-collapsed-id")

            if properties_button:
                properties_button[0].click()

            properties_names = driver.find_elements(By.CSS_SELECTOR,
                                                    "td.property-name")

            properties_values = driver.find_elements(By.CSS_SELECTOR,
                                                     "td.property-value")

            phone_characteristics = []

            for i in range(len(properties_names)):
                if properties_names[i].text.__contains__("Memoria RAM"):
                    (phone_characteristics
                     .append(Caracteristica("Memoria RAM", properties_values[i].text.strip())))

                elif properties_names[i].text == "Sistema operativo":
                    (phone_characteristics
                     .append(Caracteristica("Sistema operativo", properties_values[i].text.strip())))

                elif properties_names[i].text.__contains__("Núcleos del procesador"):
                    (phone_characteristics
                     .append(Caracteristica("Núcleos del procesador", properties_values[i].text.strip())))

                elif properties_names[i].text.__contains__("Tamaño de la pantalla"):
                    (phone_characteristics
                     .append(Caracteristica("Tamaño de la pantalla", properties_values[i].text.strip())))

                elif properties_names[i].text.__contains__("Cámara posterior"):
                    (phone_characteristics
                     .append(Caracteristica("Cámara posterior", properties_values[i].text.strip())))

                elif properties_names[i].text.__contains__("Modelo"):
                    (phone_characteristics
                     .append(Caracteristica("Modelo", properties_values[i].text.strip())))

                elif properties_names[i].text.__contains__("Cámara frontal"):
                    (phone_characteristics
                     .append(Caracteristica("Cámara frontal", properties_values[i].text.strip())))

                elif properties_names[i].text.__contains__("Conectividad"):
                    (phone_characteristics
                     .append(Caracteristica("Conectividad", properties_values[i].text.strip())))

                elif properties_names[i].text.__contains__("Capacidad de la batería"):
                    (phone_characteristics
                     .append(Caracteristica("Capacidad de la batería (mAh)", properties_values[i].text.strip())))

                elif properties_names[i].text.__contains__("Sistema operativo específico"):
                    (phone_characteristics
                     .append(Caracteristica("Sistema operativo específico", properties_values[i].text.strip())))

                elif properties_names[i].text.__contains__("Capacidad de almacenamiento"):
                    (phone_characteristics
                     .append(Caracteristica("Capacidad de almacenamiento", properties_values[i].text.strip())))

                elif properties_names[i].text.__contains__("Marca y modelo del procesador"):
                    (phone_characteristics
                     .append(Caracteristica("Marca y modelo del procesador", properties_values[i].text.strip())))

            producto.caracteristicas = phone_characteristics

            comments_button = driver.find_elements(By.CSS_SELECTOR, "button.bv-content-btn.bv-content-btn-pages"
                                                                   ".bv-content-btn-pages-load-more.bv-focusable")

            if comments_button:
                comments_button[0].click()

            username_comments_container = driver.find_elements(By.CSS_SELECTOR, "div.bv-author")
            comment_texts = driver.find_elements(By.CSS_SELECTOR, "div.bv-content-summary-body-text")

            phone_comments = []
            for i in range(len(username_comments_container)):
                comentario = Comentario()

                try:
                    comentario.username = username_comments_container[i].text.strip()
                except IndexError:
                    print("IndexError captured...")

                try:
                    comentario.content = comment_texts[i].text.strip()
                except IndexError:
                    print("IndexError captured...")

                phone_comments.append(comentario)
                producto.comentarios = phone_comments

            result = insert_product(producto.to_dict())

            if result.upserted_id is not None or result.modified_count > 0:
                phone_scraper_counter += 1

        except Exception as e:
            print("an exception occurred in " + phone_url, e)

    print("phone scraping complete. Total: " + str(phone_scraper_counter))

    return phone_scraper_counter
