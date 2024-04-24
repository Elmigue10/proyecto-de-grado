# Proyecto de Grado - Ingeniería de Software

Este repositorio contiene el software desarrollado como proyecto de grado para obtar por el titulo de ingeniería de software de la universidad Manuela Beltrán.

## En este proyecto se encuentra lo relacionado a la capa de servidor y a la aplicación web:
## Capa de Servidor

### information-and-product-management

Componente encargado del sistema transaccional para gestionar lo relacionado con los clientes, solicitudes pqrs y productos.

### productWebScraperBot

Componente encargado de realizar la gestión de la búsqueda de productos en la web, es decir, se encarga de realizar un proceso de web scraping en diferentes plataformas e-commerce
para obtener información de los productos tencologicos que están allí publicados.

## Aplicación Web

### Front-End

La aplicación web se ha construido para permitir a los clientes y al administrador llevar a cabo todos los procesos de gestión dentro de la aplicación.

## Recursos Adicionales

Además de los componentes principales, este repositorio contiene otras carpetas con recursos útiles, como:

- **Carga de Productos:** Resultado del raspado web para MongoDB.
- **Colección de Postman:** Contiene los endpoints que exponen los servicios para todas las funcionalidades de la aplicación, en relación con la capa de servidor.
- **Script SQL:** Definición de las tablas necesarias para la base de datos relacional de PostgreSQL y el sistema transaccional.
- **Docker-compose:** Permite montar todo el ambiente en un entorno local o en un servidor en la nube.
- **Notas Importantes:** Información relevante sobre los componentes de la aplicación y cómo contenerizarlos correctamente.
