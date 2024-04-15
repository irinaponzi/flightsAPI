
# Flights API

CRUD de vuelos realizado en el marco del Bootcamp Java de Codeki.


## Sobre el proyecto

Desarrollado en Java (OpenJDK 17) y Spring Boot v.3.2.3

## Base de datos

La API utiliza MySQL como sistema de gestión de bases de datos. Se puede acceder a su configuración en `application.properties`.

Por defecto, para ejecutar el proyecto es necesario crear un esquema vacío con el nombre `flightsDB`.


## Json para Postman

```json
{
    "origin": "EZE",
    "destiny": "COR",
    "departureTime": "2024-02-01T08:00:00",
    "arrivingTime": "2024-02-01T10:00:00",
    "price": 137,
    "frequency": "Semanal"
}
```

## Documentación
[Ver la documentación de la API](/src/main/resources/swagger.yaml)

