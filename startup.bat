@echo off

setlocal enabledelayedexpansion

set "file=JWT.key"
set "length=256"

:: Si el archivo ya existe, leemos su contenido

if not exist "%file%" (


:: Caracteres permitidos: letras, números y símbolos
set "charset=ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%%^&*()-_=+[]{};:,<.>/?"

:: Generar cadena aleatoria
set "randomStr="

for /l %%i in (1,1,%length%) do (
    set /a index=!random! %% 93
    for %%j in (!index!) do set "randomStr=!randomStr!!charset:~%%j,1!"
)

:: Escribir al archivo
echo !randomStr! > "%file%"
echo Archivo creado con cadena aleatoria de 256 caracteres.
)

:: Leer contenido del archivo en variable
set /p MODULE_SESSIONMANAGER__JWT=<"%file%"
echo Contenido leído:
echo !MODULE_SESSIONMANAGER__JWT!






REM Establecer los puertos
set MODULE_IDSPROVIDER__PORT=3333
set MODULE_SESSIONMANAGER__PORT=8085
set MODULE_REQUESTID__PORT=8085
set MODULE_MESSAGING__PORT=8090
set MODULE_APPSERVER__PORT=8080

REM Reutilizar los puertos usando CALL para expandir las variables
call set MODULE_IDSPROVIDER=http://idsprovider:%MODULE_IDSPROVIDER__PORT%
call set MODULE_SESSIONMANAGER=http://sessionmanager:%MODULE_SESSIONMANAGER__PORT%
call set MODULE_REQUESTID=http://sessionmanager:%MODULE_REQUESTID__PORT%
call set MODULE_MESSAGING=http://messaging:%MODULE_MESSAGING__PORT%

REM Mostrar resultados
echo MODULE_IDSPROVIDER=%MODULE_IDSPROVIDER%
echo MODULE_SESSIONMANAGER=%MODULE_SESSIONMANAGER%
echo MODULE_REQUESTID=%MODULE_REQUESTID%
echo MODULE_MESSAGING=%MODULE_MESSAGING%


REM launch docker-compose
docker compose up --build