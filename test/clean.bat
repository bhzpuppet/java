@echo off
::::::::::::::::::::::::
:: Author: Prince An
::::::::::::::::::::::::
set dir=%cd%
echo %dir%
del /f /a /q "%cd%\*.class" 