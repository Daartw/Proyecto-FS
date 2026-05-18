# Molly Store — Tienda TCG Multilenguaje y Multimoneda

## Integrantes
- Martín Bozzo
- Daniel Araya
## Descripción
Sistema de gestión para tienda de cartas coleccionables (TCG) basado en 
arquitectura de microservicios con Spring Boot. Permite gestionar catálogo 
de cartas, inventario, ventas, jugadores con fidelización por puntos, 
pagos, logística y sincronización entre servicios.

## Microservicios

| Servicio | Puerto | Descripción |
|---|---|---|
| catalogo-service | 8081 | Cartas y expansiones |
| inventario-service | 8082 | Stock de productos |
| buylist-service | 8083 | Listas de compra de clientes |
| ventas-service | 8084 | Ventas y tipo de cambio USD/CLP |
| sincronizacion-service | 8085 | Sincronización de inventario entre servicios |
| jugadores-service | 8086 | Jugadores y fidelización por puntos |
| pagos-service | 8087 | Transacciones y métodos de pago |
| reabastecimiento-service | 8088 | Órdenes de reabastecimiento |
| logistica-service | 8089 | Envíos y seguimiento |
| alertas-service | 8090 | Alertas y suscripciones de cartas |

## Comunicación entre servicios
- ventas-service → sincronizacion-service (WebClient)
- sincronizacion-service → inventario-service (WebClient)

## Requisitos previos
- Java 21
- Maven
- MySQL (XAMPP o Laragon)
- IntelliJ IDEA

## Pasos para ejecutar
1. Iniciar MySQL en XAMPP
2. Abrir cada servicio en IntelliJ
3. Ejecutar en orden: catalogo → inventario → sincronizacion → ventas → jugadores
4. Las bases de datos se crean automáticamente
5. Probar endpoints con Postman en los puertos correspondientes

## Tecnologías
Spring Boot 4.0.6 · Java 21 · MySQL · JPA/Hibernate · 
WebClient · Spring Security · Lombok · Maven
