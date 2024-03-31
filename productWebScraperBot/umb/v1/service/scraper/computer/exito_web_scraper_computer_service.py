import time
from datetime import datetime

from selenium.webdriver.common.by import By

from umb.v1.config.mongodb_config import insert_product
from umb.v1.model.characteristic_model import Caracteristica
from umb.v1.model.comment_model import Comentario
from umb.v1.model.product_model import Producto


def exito_web_scraper_bot(driver):
    exito_base_url = "https://www.exito.com/"
    exito_computer_url = exito_base_url + "/tecnologia/computadores-y-accesorios"

    computer_scraper_counter = 0

    print("scraping computers from exito...")

    driver.get(exito_computer_url)
    time.sleep(1)

    container_computers = driver.find_elements(By.CSS_SELECTOR,
                                            "article.product-card-no-alimentos_fsProductCardNoAlimentos__1N1Y5")

    computers_urls = []
    for container in container_computers:
        a_element = container.find_element(By.TAG_NAME, "a")
        computers_urls.append(a_element.get_attribute("href"))

    for computer_url in computers_urls:

        try:
            driver.get(computer_url)

            producto = Producto()

            producto.url = computer_url

            button_image = driver.find_element(By.CSS_SELECTOR, "button.ImgZoom_ContainerImage__KzA13")

            image = button_image.find_element(By.TAG_NAME, "img")

            producto.imagen_url = image.get_attribute("src")

            nombre = (driver.find_element(By.CSS_SELECTOR, "h1.product-title_product-title__heading__eJJqz")
                      .text.strip())

            producto.nombre = nombre

            if nombre.lower().__contains__("computador"):
                producto.categoria = "computer"
            elif nombre.lower().__contains__("tablet"):
                producto.categoria = "tablet"
            elif nombre.lower().__contains__("monitor"):
                producto.categoria = "monitor"
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

            computer_characteristics = []
            for i in range(len(specifications_titles)):

                if specifications_titles[i].text.__contains__("Referencia"):
                    computer_characteristics.append(Caracteristica("Referencia",
                                                                specifications_texts[i].text.strip()))

                if specifications_titles[i].text.strip() == "Sistema Operativo":
                    computer_characteristics.append(Caracteristica("Sistema Operativo",
                                                                specifications_texts[i].text.strip()))

                if specifications_titles[i].text.__contains__("Batería"):
                    computer_characteristics.append(Caracteristica("Batería",
                                                                specifications_texts[i].text.strip()))

                if specifications_titles[i].text.__contains__("Ancho"):
                    computer_characteristics.append(Caracteristica("Ancho",
                                                                specifications_texts[i].text.strip()))

                if specifications_titles[i].text.__contains__("Generación del procesador"):
                    computer_characteristics.append(Caracteristica("Generación del procesador",
                                                                specifications_texts[i].text.strip()))

                if specifications_titles[i].text.__contains__("Tamaño de Pantalla"):
                    computer_characteristics.append(Caracteristica("Tamaño de Pantalla",
                                                                specifications_texts[i].text.strip()))

                if specifications_titles[i].text.__contains__("Conectividad"):
                    computer_characteristics.append(Caracteristica("Conectividad",
                                                                specifications_texts[i].text.strip()))

                if specifications_titles[i].text.strip() == "Rango de Almacenamiento":
                    computer_characteristics.append(Caracteristica("Rango de Almacenamiento",
                                                                specifications_texts[i].text.strip()))

                if specifications_titles[i].text.strip() == "Marca Procesador":
                    computer_characteristics.append(Caracteristica("Marca Procesador",
                                                                specifications_texts[i].text.strip()))

                if specifications_titles[i].text.strip() == "Tipo de disco duro":
                    computer_characteristics.append(Caracteristica("Tipo de disco duro",
                                                                specifications_texts[i].text.strip()))

                if specifications_titles[i].text.__contains__("Modelo de Procesador"):
                    computer_characteristics.append(Caracteristica("Modelo de Procesador",
                                                                specifications_texts[i].text.strip()))

                if specifications_titles[i].text.__contains__("Marca de Tarjeta Gráfica"):
                    computer_characteristics.append(Caracteristica("Marca de Tarjeta Gráfica",
                                                                specifications_texts[i].text.strip()))

                if specifications_titles[i].text.__contains__("Memoria del Sistema Ram"):
                    computer_characteristics.append(Caracteristica("Memoria del Sistema Ram",
                                                                specifications_texts[i].text.strip()))

                if specifications_titles[i].text.strip() == "Disco Duro":
                    computer_characteristics.append(Caracteristica("Disco Duro",
                                                                specifications_texts[i].text.strip()))

                if specifications_titles[i].text.strip() == "Procesador":
                    computer_characteristics.append(Caracteristica("Procesador",
                                                                specifications_texts[i].text.strip()))

                if specifications_titles[i].text.__contains__("Alto"):
                    computer_characteristics.append(Caracteristica("Alto",
                                                                specifications_texts[i].text.strip()))

                if specifications_titles[i].text.__contains__("Tasa de refresco"):
                    computer_characteristics.append(Caracteristica("Tasa de refresco",
                                                                specifications_texts[i].text.strip()))

                if specifications_titles[i].text.__contains__("Tipo De pantalla"):
                    computer_characteristics.append(Caracteristica("Tipo De pantalla",
                                                                specifications_texts[i].text.strip()))

                if specifications_titles[i].text.__contains__("Versión sistema operativo"):
                    computer_characteristics.append(Caracteristica("Versión sistema operativo",
                                                                specifications_texts[i].text.strip()))

                if specifications_titles[i].text.__contains__("Modelo"):
                    computer_characteristics.append(Caracteristica("Modelo",
                                                                specifications_texts[i].text.strip()))

                if specifications_titles[i].text.__contains__("Cantidad de núcleo"):
                    computer_characteristics.append(Caracteristica("Cantidad de núcleos",
                                                                specifications_texts[i].text.strip()))

                if specifications_titles[i].text.__contains__("Peso"):
                    computer_characteristics.append(Caracteristica("Peso",
                                                                specifications_texts[i].text.strip()))

            producto.caracteristicas = computer_characteristics

            comments_button = driver.find_elements(By.CSS_SELECTOR, "div.drawer_openDrawer__2UATA")

            if comments_button:
                for comment_button in comments_button:
                    if comment_button.get_attribute("innerHTML").__contains__("Ver todas las opiniones"):
                        comment_button.click()
                        time.sleep(1)

                usernames_comments = driver.find_elements(By.CSS_SELECTOR, "div[data-fs-review-review='true']")
                titles_comments = driver.find_elements(By.CSS_SELECTOR, "div[data-fs-review-title='true']")
                contents_comments = driver.find_elements(By.CSS_SELECTOR, "div[data-fs-review-message='true']")

                computer_comments = []
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
                    computer_comments.append(comentario)

                producto.comentarios = computer_comments
            result = insert_product(producto.to_dict())

            if result.upserted_id is not None or result.modified_count > 0:
                computer_scraper_counter += 1

        except Exception as e:
            print("an exception occurred in " + computer_url, e)

    print("computer scraping complete. Total: " + str(computer_scraper_counter))

    return computer_scraper_counter
