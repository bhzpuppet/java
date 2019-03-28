@echo off
::::::::::::::::::::::::
:: Author: Prince An
::::::::::::::::::::::::
set cw=%classpath%
cd ..
set dir=%cd%
set classpath=%cd%;%dir%\lib\hamcrest-all-1.3.jar;%dir%\lib\junit-4.12.jar;
cd .\test

echo.
echo Compiling...
echo.
javac -d . ..\src\GPSException.java || exit 3
javac -d . ..\src\Point.java || exit 3
javac -d . ..\src\Track.java || exit 3

javac FullTests.java || exit 2

java FullTests

pause