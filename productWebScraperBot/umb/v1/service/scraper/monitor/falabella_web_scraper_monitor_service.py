import time
from datetime import datetime

from selenium.webdriver.common.by import By

from umb.v1.config.mongodb_config import insert_product
from umb.v1.model.characteristic_model import Caracteristica
from umb.v1.model.comment_model import Comentario
from umb.v1.model.product_model import Producto


def falabella_web_scraper_bot(driver):
    falabella_base_url = "https://www.falabella.com.co/falabella-co"
    falabella_monitor_url = falabella_base_url + "/category/cat2571041/Monitores-para-pc"
    monitor_scraper_counter = 0

    print("scraping monitors from falabella...")

    driver.get(falabella_monitor_url)
    time.sleep(1)

    monitors = driver.find_elements(By.CSS_SELECTOR, "a.pod-4_GRID.pod-link")

    monitors_urls = []

    for monitor in monitors:
        monitor_url = monitor.get_attribute("href")
        monitors_urls.append(monitor_url)

    for monitor_url in monitors_urls:

        try:
            driver.get(monitor_url)

            producto = Producto()

            marca = driver.find_element(By.CSS_SELECTOR, "a#pdp-product-brand-link").text.strip()
            nombre = driver.find_element(By.CSS_SELECTOR, "h1.product-name").text
            precio = (
                driver.find_element(By.CSS_SELECTOR, "span.copy17.primary.senary.jsx-2835692965.bold.line-height-29")
                .text.replace("$", "")
                .replace(".", "").strip())

            producto.id = nombre + "-falabella"
            producto.nombre = nombre
            producto.url = monitor_url
            producto.marca = marca
            producto.precio = precio
            producto.categoria = "monitor"
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

                monitor_characteristics = []

                for i in range(len(properties_names)):

                    if properties_names[i].text.strip() == "Resolución de pantalla":
                        (monitor_characteristics
                         .append(Caracteristica("Resolución de pantalla", properties_values[i].text.strip())))

                    elif properties_names[i].text.strip() == "Dimensiones":
                        (monitor_characteristics
                         .append(Caracteristica("Dimensiones", properties_values[i].text.strip())))

                    elif properties_names[i].text.strip() == "Características de la pantalla":
                        (monitor_characteristics
                         .append(Caracteristica("Características de la pantalla", properties_values[i].text.strip())))

                    elif properties_names[i].text.strip() == "Compatible con":
                        (monitor_characteristics
                         .append(Caracteristica("Compatible con", properties_values[i].text.strip())))

                    elif properties_names[i].text.strip() == "Contraste":
                        (monitor_characteristics
                         .append(Caracteristica("Contraste", properties_values[i].text.strip())))

                    elif properties_names[i].text == "Modelo":
                        (monitor_characteristics
                         .append(Caracteristica("Modelo", properties_values[i].text.strip())))

                    elif properties_names[i].text.__contains__("Tiempo de respuesta"):
                        (monitor_characteristics
                         .append(Caracteristica("Tiempo de respuesta", properties_values[i].text.strip())))

                    elif properties_names[i].text.__contains__("Cantidad de entradas"):
                        (monitor_characteristics
                         .append(Caracteristica("Cantidad de entradas", properties_values[i].text.strip())))

                    elif properties_names[i].text == "Garantía":
                        (monitor_characteristics
                         .append(Caracteristica("Garantía", properties_values[i].text.strip())))

                    elif properties_names[i].text.__contains__("Incluye"):
                        (monitor_characteristics
                         .append(Caracteristica("Incluye", properties_values[i].text.strip())))

                    elif properties_names[i].text.__contains__("Tamaño de la pantalla"):
                        (monitor_characteristics
                         .append(Caracteristica("Tamaño de la pantalla", properties_values[i].text.strip())))

                    elif properties_names[i].text.__contains__("Velocidad de procesamiento (GHz)"):
                        (monitor_characteristics
                         .append(Caracteristica("Velocidad de procesamiento (GHz)", properties_values[i].text.strip())))

                    elif properties_names[i].text.__contains__("Velocidad de imagen"):
                        (monitor_characteristics
                         .append(Caracteristica("Velocidad de imagen", properties_values[i].text.strip())))

                    elif properties_names[i].text.__contains__("Características de la pantalla"):
                        (monitor_characteristics
                         .append(Caracteristica("Características de la pantalla", properties_values[i].text.strip())))

                producto.caracteristicas = monitor_characteristics

            comments_button = driver.find_elements(By.CSS_SELECTOR, "button.bv-content-btn.bv-content-btn-pages"
                                                                    ".bv-content-btn-pages-load-more.bv-focusable")

            if comments_button:
                comments_button[0].click()

                username_comments_container = driver.find_elements(By.CSS_SELECTOR, "div.bv-author")
                comment_texts = driver.find_elements(By.CSS_SELECTOR, "div.bv-content-summary-body-text")

                monitor_comments = []
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

                    monitor_comments.append(comentario)

                producto.comentarios = monitor_comments

            result = insert_product(producto.to_dict())

            if result.upserted_id is not None or result.modified_count > 0:
                monitor_scraper_counter += 1

        except Exception as e:
            print("an exception occurred in " + monitor_url, e)

    print("monitor scraping complete. Total: " + str(monitor_scraper_counter))

    return monitor_scraper_counter
