import time
from datetime import datetime

from selenium.webdriver.common.by import By

from umb.v1.config.mongodb_config import insert_product
from umb.v1.model.characteristic_model import Caracteristica
from umb.v1.model.comment_model import Comentario
from umb.v1.model.product_model import Producto


def exito_web_scraper_bot(driver):
    exito_base_url = "https://www.exito.com/"
    exito_monitor_url = exito_base_url + "/tecnologia/computadores-y-accesorios/monitores"

    monitor_scraper_counter = 0

    print("scraping monitors from exito...")

    driver.get(exito_monitor_url)
    time.sleep(1)

    container_monitors = driver.find_elements(By.CSS_SELECTOR,
                                            "article.product-card-no-alimentos_fsProductCardNoAlimentos__1N1Y5")

    monitors_urls = []
    for container in container_monitors:
        a_element = container.find_element(By.TAG_NAME, "a")
        monitors_urls.append(a_element.get_attribute("href"))

    for monitor_url in monitors_urls:

        try:
            driver.get(monitor_url)

            producto = Producto()

            producto.url = monitor_url

            button_image = driver.find_element(By.CSS_SELECTOR, "button.ImgZoom_ContainerImage__KzA13")

            image = button_image.find_element(By.TAG_NAME, "img")

            producto.imagen_url = image.get_attribute("src")

            nombre = (driver.find_element(By.CSS_SELECTOR, "h1.product-title_product-title__heading__eJJqz")
                      .text.strip())

            producto.nombre = nombre

            producto.categoria = "monitor"

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

            monitor_characteristics = []
            for i in range(len(specifications_titles)):

                if specifications_titles[i].text.__contains__("Referencia"):
                    monitor_characteristics.append(Caracteristica("Referencia",
                                                                specifications_texts[i].text.strip()))

                if specifications_titles[i].text.strip() == "Tamaño de Pantalla":
                    monitor_characteristics.append(Caracteristica("Tamaño de Pantalla",
                                                                specifications_texts[i].text.strip()))

                if specifications_titles[i].text.__contains__("Tamaño de pantalla cms"):
                    monitor_characteristics.append(Caracteristica("Tamaño de pantalla cms",
                                                                specifications_texts[i].text.strip()))

                if specifications_titles[i].text.__contains__("Número De Puertos HDMI"):
                    monitor_characteristics.append(Caracteristica("Número De Puertos HDMI",
                                                                specifications_texts[i].text.strip()))

                if specifications_titles[i].text.__contains__("Resolución"):
                    monitor_characteristics.append(Caracteristica("Resolución",
                                                                specifications_texts[i].text.strip()))

                if specifications_titles[i].text.__contains__("Tipo De pantalla"):
                    monitor_characteristics.append(Caracteristica("Tipo De pantalla",
                                                                specifications_texts[i].text.strip()))

                if specifications_titles[i].text.__contains__("Número De Puertos USB"):
                    monitor_characteristics.append(Caracteristica("Número De Puertos USB",
                                                                specifications_texts[i].text.strip()))

            producto.caracteristicas = monitor_characteristics

            comments_button = driver.find_elements(By.CSS_SELECTOR, "div.drawer_openDrawer__2UATA")

            if comments_button:
                for comment_button in comments_button:
                    if comment_button.get_attribute("innerHTML").__contains__("Ver todas las opiniones"):
                        comment_button.click()
                        time.sleep(1)

                usernames_comments = driver.find_elements(By.CSS_SELECTOR, "div[data-fs-review-review='true']")
                titles_comments = driver.find_elements(By.CSS_SELECTOR, "div[data-fs-review-title='true']")
                contents_comments = driver.find_elements(By.CSS_SELECTOR, "div[data-fs-review-message='true']")

                monitor_comments = []
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
                    monitor_comments.append(comentario)

                producto.comentarios = monitor_comments
            result = insert_product(producto.to_dict())

            if result.upserted_id is not None or result.modified_count > 0:
                monitor_scraper_counter += 1

        except Exception as e:
            print("an exception occurred in " + monitor_url, e)

    print("monitor scraping complete. Total: " + str(monitor_scraper_counter))

    return monitor_scraper_counter
