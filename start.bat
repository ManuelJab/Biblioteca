@echo off
setlocal

echo Configurando JAVA_HOME en C:\Program Files\Java\jdk-25
set "JAVA_HOME=C:\Program Files\Java\jdk-25"
set "PATH=%JAVA_HOME%\bin;%PATH%"

echo Verificando version de Java...
java -version

echo.
echo Iniciando Sistema de Biblioteca...
mvnw.cmd spring-boot:run

endlocal
pause
