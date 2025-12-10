# Respira

## Configuración de Gradle

Este repositorio omite el archivo binario `gradle/wrapper/gradle-wrapper.jar` porque algunos flujos de revisión no aceptan binarios. Para compilar en Android Studio o desde la terminal:

1. Asegurate de tener Gradle instalado localmente (Android Studio ya lo incluye).
2. Generá nuevamente el wrapper con la versión configurada del proyecto:

   ```bash
   gradle wrapper --gradle-version 8.4 --distribution-type=bin
   ```

   Esto descargará el `gradle-wrapper.jar` faltante.
3. Luego podés usar el wrapper normalmente:

   ```bash
   ./gradlew assembleDebug
   ```

Si preferís no instalar Gradle globalmente, abrí el proyecto en Android Studio y ejecutá *Gradle Wrapper* desde la ventana de Gradle; Android Studio descargará el jar automáticamente.
