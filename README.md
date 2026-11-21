# Mygos - Encuentra tu Match en Eventos

Mygos es una aplicación móvil desarrollada en Android con Jetpack Compose que permite a los usuarios explorar eventos (deportes, cine, conciertos), registrarse, y hacer "Match" con otras personas interesadas en el mismo evento. La aplicación consume una API externa y base de datos en la nube a través de XANO.

## 👥 Integrantes
*   Michelle Serrano
*   Javiera Cortes
*   Diego Gálvez

## ⚙️ Funcionalidades
*   **Autenticación:** Registro e inicio de sesión de usuarios con persistencia de sesión local.
*   **Eventos:** Listado de eventos con imágenes, buscador integrado y sistema de caché (OkHttp) para evitar latencia.
*   **Match:** Posibilidad de unirse a un evento (Hacer Match) y ver los participantes en tiempo real.
*   **Perfil de Usuario:** Actualización de nombre, correo, contraseña y cambio de foto de perfil.
*   **Recursos Nativos:** Uso de la cámara del dispositivo para la foto de perfil (FileProvider) y notificaciones locales al hacer Match.
*   **Interfaz:** Ícono de aplicación personalizado y paleta de colores acorde a la marca.

## 🔗 Endpoints Utilizados (XANO API)
La aplicación se conecta a XANO (Base de datos y Backend) mediante Retrofit. Los endpoints utilizados son:

**Usuarios:**
*   `GET /usuario` (Filtrado por correo y contraseña para Login)
*   `POST /usuario` (Registro de nuevos usuarios)
*   `PUT /usuario/{id}` (Actualización de datos y foto de perfil)

**Eventos:**
*   `GET /evento` (Listado general de eventos)

**Matches (Reseñas):**
*   `GET /resena` (Filtrado por correo para "Mis Matches" y por evento para "Participantes")
*   `POST /resena` (Crear un nuevo match)
*   `DELETE /resena/{id}` (Eliminar un match)

## 🧪 Pruebas Unitarias
El proyecto cuenta con pruebas unitarias implementadas con **JUnit** y **MockWebServer** para validar la lógica de los repositorios y ViewModels, superando el 80% de cobertura de la lógica de negocio.
Para ejecutar las pruebas, abre una terminal en la raíz del proyecto y ejecuta:
```bash
./gradlew test
