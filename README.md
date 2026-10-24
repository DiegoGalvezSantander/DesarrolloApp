# Mygos - Conecta con tu plan ideal

*Integrantes del equipo:*
- Diego Gálvez
- Javiera Cortés
- Michelle Serrano

*Asignatura:* DSY1105 - Desarrollo de Aplicaciones Móviles

##  Descripción del Proyecto
Mygos es una aplicación móvil nativa para Android desarrollada en Kotlin. Su objetivo es conectar a personas con intereses similares a través de eventos de deporte, cine y conciertos, permitiendo hacer "Match" con otros asistentes.

##  Arquitectura y Tecnologías
El proyecto sigue estrictamente el patrón de diseño *MVVM (Model-View-ViewModel)*:
- *Model:* Entidades de Room (Usuario, Match) y DAOs.
- *View:* Interfaces de usuario creadas con Jetpack Compose.
- *ViewModel:* AuthViewModel y MatchViewModel manejan el estado de la UI.
- *Repository:* UsuarioRepository y MatchRepository encapsulan el acceso a los datos locales.
- *Navegación:* NavHost y rememberNavController.

##  Funcionalidades Implementadas
- *Formularios validados:* Login y Registro con retroalimentación visual de errores.
- *Persistencia Local (Room):* Almacenamiento de usuarios, matches y sesión activa.
- *Recursos Nativos:*
  - *Cámara:* Para tomar y actualizar la foto de perfil.
  - *GPS:* Para obtener la ubicación del usuario en la pantalla principal.
- *Animaciones y Modales:* Animación de escala en el botón de "Hacer Match" y AlertDialog de confirmación.
- *Diseño UI/UX:* Paleta de colores personalizada (Material Design 3) y navegación fluida.

##  Cómo ejecutar el proyecto
1. Clonar este repositorio.
2. Abrir el proyecto en Android Studio.
3. Sincronizar Gradle.
4. Ejecutar en un emulador o dispositivo físico con Android 7.0 (API 24) o superior.
