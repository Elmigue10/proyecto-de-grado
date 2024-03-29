import time
from datetime import datetime

from selenium.webdriver.common.by import By

from app.v1.config.mongodb_config import insert_product
from app.v1.model.characteristic_model import Caracteristica
from app.v1.model.comment_model import Comentario
from app.v1.model.product_model import Producto


def falabella_web_scraper_bot(driver):
    falabella_base_url = "https://www.falabella.com.co/falabella-co"
    falabella_computer_url = falabella_base_url + "/category/cat1361001/Computadores-Portatiles"
    computer_scraper_counter = 0

    print("scraping computers from falabella...")

    driver.get(falabella_computer_url)
    time.sleep(1)

    computers = driver.find_elements(By.CSS_SELECTOR, "a.pod-4_GRID.pod-link")

    computers_urls = []

    for computer in computers:
        computer_url = computer.get_attribute("href")
        computers_urls.append(computer_url)

    for computer_url in computers_urls:

        try:
            driver.get(computer_url)

            producto = Producto()

            marca = driver.find_element(By.CSS_SELECTOR, "a#pdp-product-brand-link").text.strip()
            nombre = driver.find_element(By.CSS_SELECTOR, "h1.product-name").text
            precio = (driver.find_element(By.CSS_SELECTOR, "span.copy17.primary.senary.jsx-2835692965.bold.line-height-29")
                      .text.replace("$", "")
                      .replace(".", "").strip())

            producto.id = nombre + "-falabella"
            producto.nombre = nombre
            producto.url = computer_url
            producto.marca = marca
            producto.precio = precio
            producto.categoria = "smartcomputer"
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

                computer_characteristics = []

                for i in range(len(properties_names)):

                    if properties_names[i].text.strip() == "Capacidad de almacenamiento":
                        (computer_characteristics
                         .append(Caracteristica("Capacidad de almacenamiento", properties_values[i].text.strip())))

                    elif properties_names[i].text.strip() == "Sistema operativo":
                        (computer_characteristics
                         .append(Caracteristica("Sistema operativo", properties_values[i].text.strip())))

                    elif properties_names[i].text.strip() == "Procesador":
                        (computer_characteristics
                         .append(Caracteristica("Procesador", properties_values[i].text.strip())))

                    elif properties_names[i].text.strip() == "Modelo":
                        (computer_characteristics
                         .append(Caracteristica("Modelo", properties_values[i].text.strip())))

                    elif properties_names[i].text.strip() == "Capacidad de almacenamiento":
                        (computer_characteristics
                         .append(Caracteristica("Capacidad de almacenamiento", properties_values[i].text.strip())))

                    elif properties_names[i].text.__contains__("Memoria RAM"):
                        (computer_characteristics
                         .append(Caracteristica("Memoria RAM", properties_values[i].text.strip())))

                    elif properties_names[i].text.__contains__("Unidad de estado sólido SSD"):
                        (computer_characteristics
                         .append(Caracteristica("Unidad de estado sólido SSD", properties_values[i].text.strip())))

                    elif properties_names[i].text.__contains__("Resolución de la pantalla"):
                        (computer_characteristics
                         .append(Caracteristica("Resolución de la pantalla", properties_values[i].text.strip())))

                    elif properties_names[i].text.__contains__("Tamaño de la pantalla"):
                        (computer_characteristics
                         .append(Caracteristica("Tamaño de la pantalla", properties_values[i].text.strip())))

                    elif properties_names[i].text.__contains__("Disco duro HDD"):
                        (computer_characteristics
                         .append(Caracteristica("Disco duro HDD", properties_values[i].text.strip())))

                    elif properties_names[i].text.__contains__("Núcleos del procesador"):
                        (computer_characteristics
                         .append(Caracteristica("Núcleos del procesador", properties_values[i].text.strip())))

                    elif properties_names[i].text.__contains__("Velocidad de procesamiento (GHz)"):
                        (computer_characteristics
                         .append(Caracteristica("Velocidad de procesamiento (GHz)", properties_values[i].text.strip())))

                    elif properties_names[i].text.__contains__("Modelo del procesador"):
                        (computer_characteristics
                         .append(Caracteristica("Modelo del procesador", properties_values[i].text.strip())))

                    elif properties_names[i].text.__contains__("Generación del procesador"):
                        (computer_characteristics
                         .append(Caracteristica("Generación del procesador", properties_values[i].text.strip())))

                    elif properties_names[i].text.__contains__("Modelo del procesador"):
                        (computer_characteristics
                         .append(Caracteristica("Modelo del procesador", properties_values[i].text.strip())))

                    elif properties_names[i].text.__contains__("Tarjeta gráfica"):
                        (computer_characteristics
                         .append(Caracteristica("Tarjeta gráfica", properties_values[i].text.strip())))

                    elif properties_names[i].text.__contains__("Capacidad de la tarjeta de video"):
                        (computer_characteristics
                         .append(Caracteristica("Capacidad de la tarjeta de video", properties_values[i].text.strip())))

                    elif properties_names[i].text.__contains__("Duración aproximada de la batería"):
                        (computer_characteristics
                         .append(Caracteristica("Duración aproximada de la batería", properties_values[i].text.strip())))

                    elif properties_names[i].text.__contains__("Peso del producto"):
                        (computer_characteristics
                         .append(Caracteristica("Peso del producto", properties_values[i].text.strip())))

                    elif properties_names[i].text.__contains__("Conectividad"):
                        (computer_characteristics
                         .append(Caracteristica("Conectividad", properties_values[i].text.strip())))

                producto.caracteristicas = computer_characteristics

            comments_button = driver.find_elements(By.CSS_SELECTOR, "button.bv-content-btn.bv-content-btn-pages"
                                                                   ".bv-content-btn-pages-load-more.bv-focusable")

            if comments_button:
                comments_button[0].click()

                username_comments_container = driver.find_elements(By.CSS_SELECTOR, "div.bv-author")
                comment_texts = driver.find_elements(By.CSS_SELECTOR, "div.bv-content-summary-body-text")

                computer_comments = []
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

                    computer_comments.append(comentario)

                producto.comentarios = computer_comments

            result = insert_product(producto.to_dict())

            if result.upserted_id is not None or result.modified_count > 0:
                computer_scraper_counter += 1

        except Exception as e:
            print("an exception occurred in " + computer_url, e)

    print("computer scraping complete. Total: " + str(computer_scraper_counter))

    return computer_scraper_counter
