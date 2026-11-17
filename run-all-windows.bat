@echo off
title Tourist Microservices Launcher
echo Starting Auth Service on port 8081...
start "Auth Service - 8081" cmd /k "cd /d %~dp0auth-service && mvn spring-boot:run"
timeout /t 8 /nobreak >nul
echo Starting Tourist Service on port 8082...
start "Tourist Service - 8082" cmd /k "cd /d %~dp0tourist-service && mvn spring-boot:run"
timeout /t 8 /nobreak >nul
echo Starting API Gateway on port 8080...
start "API Gateway - 8080" cmd /k "cd /d %~dp0api-gateway && mvn spring-boot:run"
echo.
echo Started three service consoles. Wait for each to report Started.
echo REST Client requests: rest-client	ourist-api.http
pause
