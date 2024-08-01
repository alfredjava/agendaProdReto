## Ejecutar la Aplicación

Para ejecutar la aplicación, sigue estos pasos:

1. Clona el repositorio:
    ```sh
    git clone git@github.com:alfredjava/agendaProdReto.git
    cd agendaProdReto
    ```

2. Construye el proyecto usando Gradle:
    ```sh
    ./gradlew build
    ```

3. Ejecuta la aplicación:
    ```sh
    ./gradlew bootRun
    ```

La aplicación estará disponible en `http://localhost:8080`.

## Realizar Pruebas de los Endpoints de Productos

Para realizar pruebas de los endpoints de productos, puedes usar la colección de Postman adjunta: [agendaPro.postman_collection.json](agendaPro.postman_collection.json).

### Listar Productos

- **Endpoint:** `GET /api/products`
- **Descripción:** Obtiene la lista de todos los productos.
- **Ejemplo de Solicitud:**
    ```sh
    curl -X GET http://localhost:8080/api/products -u user:password
    ```

### Guardar Producto

- **Endpoint:** `POST /api/products`
- **Descripción:** Crea un nuevo producto.
- **Ejemplo de Solicitud:**
    ```sh
    curl -X POST http://localhost:8080/api/products -u user:password -H "Content-Type: application/json" -d '{
        "name": "Product1",
        "category": "Category1",
        "price": 100.0
    }'
    ```

### Ver Estadísticas del Guardado

- **Endpoint:** `GET /api/statistics/product-count/{category}`
- **Descripción:** Obtiene el conteo de productos en una categoría específica.
- **Ejemplo de Solicitud:**
    ```sh
    curl -X GET http://localhost:8080/api/statistics/product-count/Category1 -u user:password
    ```

## Importar la Colección de Postman
[agendaPro.postman_collection.json](agendaPro.postman_collection.json)
1. Abre Postman.
2. Haz clic en "Import" en la esquina superior izquierda.
3. Selecciona el archivo `agendaPro.postman_collection.json`.
4. La colección se importará y podrás ver los endpoints listos para ser probados.

## Notas

- Asegúrate de que la aplicación esté en ejecución antes de realizar las pruebas.
- Usa las credenciales `user:password` para la autenticación básica en las solicitudes.