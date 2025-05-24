# Intitech E-Commerce

Intitech E-Commerce es un backend desarrollado con una arquitectura de microservicios utilizando **Spring Boot**, **Maven** y bases de datos relacionales y no relacionales. Este proyecto está diseñado para gestionar un sistema de comercio electrónico con múltiples servicios independientes.

## Tabla de Contenidos

- [Descripción General](#descripción-general)
- [Tecnologías Utilizadas](#tecnologías-utilizadas)
- [Estructura del Proyecto](#estructura-del-proyecto)
- [Servicios](#servicios)
- [Requisitos Previos](#requisitos-previos)
- [Instrucciones de Ejecución](#instrucciones-de-ejecución)
- [Docker Compose](#docker-compose)
- [Contribuciones](#contribuciones)

## Descripción General

Este proyecto implementa un sistema de comercio electrónico con microservicios que interactúan entre sí a través de una arquitectura basada en **Spring Cloud Netflix Eureka** para el descubrimiento de servicios y un **API Gateway** para la gestión de solicitudes.

## Tecnologías Utilizadas

- **Java 17**
- **Spring Boot 3.1.0**
- **Spring Cloud 2022.0.4**
- **Maven**
- **MySQL 8**
- **PostgreSQL 15**
- **MongoDB**
- **Docker y Docker Compose**

## Estructura del Proyecto

El proyecto está organizado en múltiples módulos, cada uno representando un microservicio independiente:

- `msvc-products`: Gestión de productos.
- `msvc-inventory`: Gestión de inventarios.
- `msvc-order`: Gestión de pedidos.
- `msvc-users`: Gestión de usuarios.
- `msvc-ratings`: Gestión de calificaciones.
- `msvc-blog`: Gestión de blogs.
- `msvc-coupon`: Gestión de cupones.
- `msvc-cart`: Gestión del carrito de compras.
- `msvc-enquire`: Gestión de consultas.
- `payment-service`: Gestión de pagos.
- `upload-service`: Gestión de cargas de archivos.
- `auth-service`: Servicio de autenticación.
- `gateway-service`: API Gateway.
- `discovery-eureka`: Servidor de descubrimiento de servicios.

## Servicios

### Bases de Datos

- **MySQL**: Utilizado por los servicios `msvc-cart`, `msvc-inventory` y `msvc-order`.
- **PostgreSQL**: Utilizado por el servicio `msvc-users`.
- **MongoDB**: Utilizado por los servicios `msvc-products`, `msvc-ratings`, `msvc-blog`, `msvc-coupon`, y otros.

### Microservicios

Cada microservicio tiene su propio `Dockerfile` y configuración en el archivo `docker-compose.yml`.

### API Gateway

El servicio `gateway-service` actúa como punto de entrada único para todas las solicitudes.

### Servidor Eureka

El servicio `discovery-eureka` permite el descubrimiento dinámico de microservicios.

## Requisitos Previos

- **Docker** y **Docker Compose** instalados.
- **Java 17** y **Maven** instalados.
- Conexión a internet para descargar dependencias.

## Instrucciones de Ejecución

1. Clona este repositorio:
   ```bash
   git clone <URL_DEL_REPOSITORIO>
   cd intitech-ecommerce
