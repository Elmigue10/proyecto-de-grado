import time
from datetime import datetime

from selenium.webdriver.common.by import By

from umb.v1.config.mongodb_config import insert_product
from umb.v1.model.characteristic_model import Caracteristica
from umb.v1.model.comment_model import Comentario
from umb.v1.model.product_model import Producto


def exito_web_scraper_bot(driver):
    exito_base_url = "https://www.exito.com/"
    exito_phone_url = exito_base_url + "/tecnologia/celulares"

    phone_scraper_counter = 0

    print("scraping phones from exito...")

    driver.get(exito_phone_url)
    time.sleep(1)

    container_phones = driver.find_elements(By.CSS_SELECTOR,
                                            "article.product-card-no-alimentos_fsProductCardNoAlimentos__1N1Y5")

    phones_urls = []
    for container in container_phones:
        a_element = container.find_element(By.TAG_NAME, "a")
        phones_urls.append(a_element.get_attribute("href"))

    for phone_url in phones_urls:

        try:
            driver.get(phone_url)

            producto = Producto()

            producto.url = phone_url

            button_image = driver.find_element(By.CSS_SELECTOR, "button.ImgZoom_ContainerImage__KzA13")

            image = button_image.find_element(By.TAG_NAME, "img")

            producto.imagen_url = image.get_attribute("src")

            nombre = (driver.find_element(By.CSS_SELECTOR, "h1.product-title_product-title__heading__eJJqz")
                      .text.strip())

            producto.nombre = nombre

            if nombre.lower().__contains__("celular") or nombre.lower().__contains__("iphone"):
                producto.categoria = "smartphone"
            else:
                producto.categoria = "other"

            producto.id = nombre + "-exito"

            price = (driver.find_element(By.CSS_SELECTOR, "p.ProductPrice_container__price__LS1Td").text.strip()
                     .replace("$", "").replace(".", "").strip())

            producto.precio = price

            marca = (driver.find_element(By.CSS_SELECTOR, "span.product-title_product-title__specification__B5pYV")
                     .text.strip().split("-PLU"))

            producto.marca = marca[0]
            producto.plataforma = "exito"
            producto.created_or_updated_at = datetime.now()

            specifications_button_container = (
                driver.find_elements(By.CSS_SELECTOR, "div.see-more-link_fs-see-more-link__WdJd8"))

            if specifications_button_container:
                specifications_button = specifications_button_container[0].find_elements(By.TAG_NAME, "button")
                if specifications_button:
                    specifications_button[0].click()

            specifications_titles = driver.find_elements(By.CSS_SELECTOR, "p[data-fs-title-specification='true']")
            specifications_texts = driver.find_elements(By.CSS_SELECTOR, "p[data-fs-text-specification='true']")

            phone_characteristics = []
            for i in range(len(specifications_titles)):

                if specifications_titles[i].text.__contains__("Referencia"):
                    phone_characteristics.append(Caracteristica("Referencia",
                                                                specifications_texts[i].text.strip()))

                if specifications_titles[i].text.__contains__("Capacidad de almacenamiento"):
                    phone_characteristics.append(Caracteristica("Capacidad de almacenamiento",
                                                                specifications_texts[i].text.strip()))

                if specifications_titles[i].text.strip() == "Sistema Operativo":
                    phone_characteristics.append(Caracteristica("Sistema Operativo",
                                                                specifications_texts[i].text.strip()))

                if specifications_titles[i].text.__contains__("Tamaño de Pantalla"):
                    phone_characteristics.append(Caracteristica("Tamaño de Pantalla",
                                                                specifications_texts[i].text.strip()))

                if specifications_titles[i].text.__contains__("Memoria del Sistema Ram"):
                    phone_characteristics.append(Caracteristica("Memoria del Sistema Ram",
                                                                specifications_texts[i].text.strip()))

                if specifications_titles[i].text.__contains__("Rango Bateria"):
                    phone_characteristics.append(Caracteristica("Rango Bateria",
                                                                specifications_texts[i].text.strip()))

                if specifications_titles[i].text.__contains__("Velocidad del procesador"):
                    phone_characteristics.append(Caracteristica("Velocidad del procesador",
                                                                specifications_texts[i].text.strip()))

                if specifications_titles[i].text.strip() == "Procesador":
                    phone_characteristics.append(Caracteristica("Procesador",
                                                                specifications_texts[i].text.strip()))

                if specifications_titles[i].text.__contains__("Dual Sim"):
                    phone_characteristics.append(Caracteristica("Dual Sim",
                                                                specifications_texts[i].text.strip()))

                if specifications_titles[i].text.__contains__("Red"):
                    phone_characteristics.append(Caracteristica("Red",
                                                                specifications_texts[i].text.strip()))

                if specifications_titles[i].text.__contains__("Cámara frontal"):
                    phone_characteristics.append(Caracteristica("Cámara frontal",
                                                                specifications_texts[i].text.strip()))

                if specifications_titles[i].text.__contains__("Cámara Principal"):
                    phone_characteristics.append(Caracteristica("Cámara Principal",
                                                                specifications_texts[i].text.strip()))

                if specifications_titles[i].text.__contains__("Tipo de Bateria"):
                    phone_characteristics.append(Caracteristica("Tipo de Bateria",
                                                                specifications_texts[i].text.strip()))

                if specifications_titles[i].text.strip() == "Versión sistema operativo":
                    phone_characteristics.append(Caracteristica("Versión sistema operativo",
                                                                specifications_texts[i].text.strip()))

                if specifications_titles[i].text.__contains__("Modelo"):
                    phone_characteristics.append(Caracteristica("Modelo",
                                                                specifications_texts[i].text.strip()))

                if specifications_titles[i].text.__contains__("Cantidad de núcleo"):
                    phone_characteristics.append(Caracteristica("Tipo de Bateria",
                                                                specifications_texts[i].text.strip()))

                if specifications_titles[i].text.__contains__("Ancho"):
                    phone_characteristics.append(Caracteristica("Ancho",
                                                                specifications_texts[i].text.strip()))

                if specifications_titles[i].text.__contains__("Alto"):
                    phone_characteristics.append(Caracteristica("Alto",
                                                                specifications_texts[i].text.strip()))

            producto.caracteristicas = phone_characteristics

            comments_button = driver.find_elements(By.CSS_SELECTOR, "div.drawer_openDrawer__2UATA")

            if comments_button:
                for comment_button in comments_button:
                    if comment_button.get_attribute("innerHTML").__contains__("Ver todas las opiniones"):
                        comment_button.click()
                        time.sleep(1)

                usernames_comments = driver.find_elements(By.CSS_SELECTOR, "div[data-fs-review-review='true']")
                titles_comments = driver.find_elements(By.CSS_SELECTOR, "div[data-fs-review-title='true']")
                contents_comments = driver.find_elements(By.CSS_SELECTOR, "div[data-fs-review-message='true']")

                phone_comments = []
                for i in range(len(usernames_comments)):
                    comentario = Comentario()

                    try:
                        comentario.username = usernames_comments[i].get_attribute("innerHTML").strip()
                    except IndexError:
                        print("IndexError captured...")

                    comment_text = ""

                    try:
                        comment_text += titles_comments[i].get_attribute("innerHTML").strip() + ". "
                    except IndexError:
                        print("IndexError captured...")

                    try:
                        comment_text += contents_comments[i].get_attribute("innerHTML").strip()
                    except IndexError:
                        print("IndexError captured...")

                    comentario.content = comment_text
                    phone_comments.append(comentario)

                producto.comentarios = phone_comments
            result = insert_product(producto.to_dict())

            if result.upserted_id is not None or result.modified_count > 0:
                phone_scraper_counter += 1

        except Exception as e:
            print("an exception occurred in " + phone_url, e)

    print("phone scraping complete. Total: " + str(phone_scraper_counter))

    return phone_scraper_counter
