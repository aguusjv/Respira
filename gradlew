#!/usr/bin/env sh

APP_HOME="$(cd "$(dirname "$0")" && pwd)"
JAVA_EXE="$(command -v java)"
if [ -z "$JAVA_EXE" ]; then
  echo "Java is required to run Gradle wrapper." >&2
  exit 1
fi

WRAPPER_JAR="$APP_HOME/gradle/wrapper/gradle-wrapper.jar"
WRAPPER_PROPERTIES="$APP_HOME/gradle/wrapper/gradle-wrapper.properties"
if [ ! -f "$WRAPPER_JAR" ] || [ ! -f "$WRAPPER_PROPERTIES" ]; then
  echo "Gradle wrapper files are missing. Please add gradle/wrapper/gradle-wrapper.jar." >&2
  exit 1
fi

exec "$JAVA_EXE" -jar "$WRAPPER_JAR" "$@"
