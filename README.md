# API de Gestión de Empleados

### Reference Documentation

Esta es una API RESTful construida con Spring Boot para gestionar la información de los empleados. Permite crear, leer, actualizar y eliminar registros de empleados.

## Funcionalidades
- **Listado de Empleados:** Recupera un listado de todos los empleados registrados.
- **Eliminación de Empleado:** Elimina un empleado específico utilizando su ID de registro.
- **Actualización de Empleado:** Permite actualizar cualquier dato de un empleado existente.
- **Inserción de Empleados:** Permite insertar uno o varios empleados en una sola petición.

```markdown
**Endpoints de la API:**

Lista los endpoints principales de la API y sus métodos HTTP.

## Endpoints de la API
- `GET /api/employees`: Recupera el listado de todos los empleados.
- `GET /api/employees/{id}`: Recupera un empleado por su ID.
- `POST /api/employees`: Inserta uno o varios empleados (requiere un array JSON en el cuerpo).
- `PUT /api/employees/{id}`: Actualiza la información de un empleado (requiere un objeto JSON en el cuerpo).
- `DELETE /api/employees/{id}`: Elimina un empleado por su ID.

Consulta la documentación de Swagger para obtener detalles sobre los parámetros de request, los cuerpos de request/response y los códigos de estado HTTP.


## Tecnologías Utilizadas
- **Java:** 21
- **Spring Boot:** 3.4.5
- **Spring JPA:** Para la persistencia de datos.
- **MySQL:** Como base de datos.
- **JSON:** Para el formato de request y response.
- **Swagger (SpringDoc):** Para la documentación de la API.
- **JUnit y Mockito:** Para las pruebas unitarias.
- **Lombok:** Para reducir el código boilerplate.
- **PROPERTIES:** Para la configuración de la aplicación.
- **Logback:** Para el manejo de logs.