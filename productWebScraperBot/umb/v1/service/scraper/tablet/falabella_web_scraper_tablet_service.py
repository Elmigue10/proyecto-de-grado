import time
from datetime import datetime

from selenium.webdriver.common.by import By

from umb.v1.config.mongodb_config import insert_product
from umb.v1.model.characteristic_model import Caracteristica
from umb.v1.model.comment_model import Comentario
from umb.v1.model.product_model import Producto


def falabella_web_scraper_bot(driver):
    falabella_base_url = "https://www.falabella.com.co/falabella-co"
    falabella_tablet_url = falabella_base_url + "/category/cat50666/Tablets"
    tablet_scraper_counter = 0

    print("scraping tablets from falabella...")

    driver.get(falabella_tablet_url)
    time.sleep(1)

    tablets = driver.find_elements(By.CSS_SELECTOR, "a.pod-4_GRID.pod-link")

    tablets_urls = []

    for tablet in tablets:
        tablet_url = tablet.get_attribute("href")
        tablets_urls.append(tablet_url)

    for tablet_url in tablets_urls:

        try:
            driver.get(tablet_url)

            producto = Producto()

            marca = driver.find_element(By.CSS_SELECTOR, "a#pdp-product-brand-link").text.strip()
            nombre = driver.find_element(By.CSS_SELECTOR, "h1.product-name").text
            precio = (driver.find_element(By.CSS_SELECTOR, "span.copy17.primary.senary.jsx-2835692965.bold.line-height-29")
                      .text.replace("$", "")
                      .replace(".", "").strip())

            producto.id = nombre + "-falabella"
            producto.nombre = nombre
            producto.url = tablet_url
            producto.marca = marca
            producto.precio = precio
            producto.categoria = "tablet"
            producto.plataforma = "falabella"
            producto.imagen_url = driver.find_element(By.CSS_SELECTOR, "img.jsx-2487856160").get_attribute("src")
            producto.created_or_updated_at = datetime.now()

            properties_buttons = driver.find_elements(By.CSS_SELECTOR, "button#swatch-collapsed-id")

            if properties_buttons:
                properties_button = properties_buttons[0]

                driver.execute_script("arguments[0].click();", properties_button)

                properties_names = driver.find_elements(By.CSS_SELECTOR,
                                                        "td.property-name")

                properties_values = driver.find_elements(By.CSS_SELECTOR,
                                                         "td.property-value")

                tablet_characteristics = []

                for i in range(len(properties_names)):

                    if properties_names[i].text.strip() == "Procesador":
                        (tablet_characteristics
                         .append(Caracteristica("Procesador", properties_values[i].text.strip())))

                    elif properties_names[i].text.strip() == "Núcleos del procesador":
                        (tablet_characteristics
                         .append(Caracteristica("Núcleos del procesador", properties_values[i].text.strip())))

                    elif properties_names[i].text.strip() == "Tamaño de la pantalla":
                        (tablet_characteristics
                         .append(Caracteristica("Tamaño de la pantalla", properties_values[i].text.strip())))

                    elif properties_names[i].text.strip() == "Velocidad del procesador":
                        (tablet_characteristics
                         .append(Caracteristica("Velocidad del procesador", properties_values[i].text.strip())))

                    elif properties_names[i].text.strip() == "Memoria RAM":
                        (tablet_characteristics
                         .append(Caracteristica("Memoria RAM", properties_values[i].text.strip())))

                    elif properties_names[i].text.__contains__("Memoria expandible"):
                        (tablet_characteristics
                         .append(Caracteristica("Memoria expandible", properties_values[i].text.strip())))

                    elif properties_names[i].text.__contains__("Memoria interna"):
                        (tablet_characteristics
                         .append(Caracteristica("Memoria interna", properties_values[i].text.strip())))

                    elif properties_names[i].text.__contains__("Cámara frontal"):
                        (tablet_characteristics
                         .append(Caracteristica("Cámara frontal", properties_values[i].text.strip())))

                    elif properties_names[i].text.__contains__("Cámara posterior"):
                        (tablet_characteristics
                         .append(Caracteristica("Cámara posterior", properties_values[i].text.strip())))

                    elif properties_names[i].text.__contains__("Conectividad"):
                        (tablet_characteristics
                         .append(Caracteristica("Conectividad", properties_values[i].text.strip())))

                    elif properties_names[i].text.__contains__("Conexión Bluetooth"):
                        (tablet_characteristics
                         .append(Caracteristica("Conexión Bluetooth", properties_values[i].text.strip())))

                    elif properties_names[i].text.__contains__("Sistema operativo"):
                        (tablet_characteristics
                         .append(Caracteristica("Sistema operativo", properties_values[i].text.strip())))

                    elif properties_names[i].text.__contains__("Características de la pantalla"):
                        (tablet_characteristics
                         .append(Caracteristica("Características de la pantalla", properties_values[i].text.strip())))

                    elif properties_names[i].text.__contains__("Peso del producto"):
                        (tablet_characteristics
                         .append(Caracteristica("Peso del producto", properties_values[i].text.strip())))

                    elif properties_names[i].text == "Modelo":
                        (tablet_characteristics
                         .append(Caracteristica("Modelo", properties_values[i].text.strip())))

                    elif properties_names[i].text.__contains__("Nombre comercial"):
                        (tablet_characteristics
                         .append(Caracteristica("Nombre comercial", properties_values[i].text.strip())))

                    elif properties_names[i].text.__contains__("¿Qué incluye?"):
                        (tablet_characteristics
                         .append(Caracteristica("¿Qué incluye?", properties_values[i].text.strip())))

                    elif properties_names[i].text.__contains__("Tasa de refresco"):
                        (tablet_characteristics
                         .append(Caracteristica("Tasa de refresco", properties_values[i].text.strip())))

                    elif properties_names[i].text.__contains__("Capacidad de almacenamiento"):
                        (tablet_characteristics
                         .append(Caracteristica("Capacidad de almacenamiento", properties_values[i].text.strip())))

                    elif properties_names[i].text.__contains__("Procesador específico"):
                        (tablet_characteristics
                         .append(Caracteristica("Procesador específico", properties_values[i].text.strip())))

                producto.caracteristicas = tablet_characteristics

            comments_button = driver.find_elements(By.CSS_SELECTOR, "button.bv-content-btn.bv-content-btn-pages"
                                                                   ".bv-content-btn-pages-load-more.bv-focusable")

            if comments_button:
                comments_button[0].click()

                username_comments_container = driver.find_elements(By.CSS_SELECTOR, "div.bv-author")
                comment_texts = driver.find_elements(By.CSS_SELECTOR, "div.bv-content-summary-body-text")

                tablet_comments = []
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

                    tablet_comments.append(comentario)

                producto.comentarios = tablet_comments

            result = insert_product(producto.to_dict())

            if result.upserted_id is not None or result.modified_count > 0:
                tablet_scraper_counter += 1

        except Exception as e:
            print("an exception occurred in " + tablet_url, e)

    print("tablet scraping complete. Total: " + str(tablet_scraper_counter))

    return tablet_scraper_counter
