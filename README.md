Este repositorio contiene el software desarrollado como proyecto de grado para obtar por el titulo de ingeniería de software de la universidad Manuela Beltrán.

En este proyecto se encuentra lo relacionado a la capa de servidor:

information-and-product-management:
Componente encargado del sistema transaccional para gestionar lo relacionado con los clientes, solicitudes pqrs y productos.

productWebScraperBot:
Componente encargado de realizar la gestión de la búsqueda de productos en la web, es decir, se encarga de realizar un proceso de web scraping en diferentes plataformas e-commerce
para obtener información de los productos tencologicos que están allí publicados.

Y lo relacionado a la aplicación web:
front-end: Aplicación web construida para permitir a los clientes y al administrador realizar todos los procesos de gestión dentro de la aplicación.

Adicionalmente, se encuentran otras carpetas donde se encuentran recursos de utilidad, como por ejemplo:
La carga de productos resultado del raspado web para mongodb.
La colección de postman, donde se encuentran los endpoints que exponen los servicios para realizar todas las funcionalidades de la aplicación, esto en cuanto a la capa de servidor.
El script sql con la definición de las tablas necesarias para la base de datos relacional de PostgreSQL y el sistema transaccional.
Un docker-compose para montar todo el ambiente en un entorno local o en un servidor en la nube.
Algunas notas importantes para tener en cuenta con respecto a los componentes de la aplicación y como contenerizarlos.
