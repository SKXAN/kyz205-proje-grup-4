@echo off
rem Maven olmadan derle ve calistir. JDK 17+ kurulu olmali.
chcp 65001 >nul
setlocal EnableDelayedExpansion
cd /d "%~dp0"

set SRC=src\main\java
set OUT=out

rem javac arguman dosyasinda ters bolu kacis karakteri sayilir; yollari egik cizgiye ceviriyoruz.
if not exist "%OUT%" mkdir "%OUT%"
(for /r "%SRC%" %%f in (*.java) do @(
    set "dosya=%%f"
    echo "!dosya:\=/!"
)) > "%OUT%\kaynaklar.txt"

javac -encoding UTF-8 -d "%OUT%" @"%OUT%\kaynaklar.txt"
if errorlevel 1 (
    echo Derleme hatasi.
    exit /b 1
)

java -Dfile.encoding=UTF-8 -Dstdout.encoding=UTF-8 -cp "%OUT%" com.kyz205.nottakip.Main
endlocal
