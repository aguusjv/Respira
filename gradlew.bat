@ECHO OFF
SET DIR=%~dp0
SET JAR=%DIR%gradle\wrapper\gradle-wrapper.jar
IF NOT EXIST "%JAR%" (
  ECHO Gradle wrapper jar missing. Please add gradle/wrapper/gradle-wrapper.jar.
  EXIT /B 1
)
java -jar "%JAR%" %*
