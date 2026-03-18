# Prototipo Cloud Native - Clinica Veterinaria

Ruta del proyecto:

C:\Users\kathe\Downloads\cloud_ii\veterinaria-faas-notificaciones

## 1) Extensiones VS Code requeridas

- Azure Functions (ms-azuretools.vscode-azurefunctions)
- Extension Pack for Java (vscjava.vscode-java-pack)

## 2) Creacion de proyecto Azure Functions (GUI VS Code)

Sigue exactamente estos valores al ejecutar:

Azure Functions: Create New Project...

- Language: Java
- Build tool: Maven
- Group ID: com.veterinaria
- Artifact ID: notificaciones-faas
- Version: 1.0-SNAPSHOT
- Package name: com.veterinaria.notificaciones
- App name (Function name): EnviarNotificacionCita
- Authorization level: Anonymous

## 3) Comandos para generar bases Spring Boot

Opcion A: con Spring Initializr Web (curl)

```bash
curl https://start.spring.io/starter.zip \
  -d dependencies=web,data-jpa,h2,validation \
  -d type=maven-project \
  -d language=java \
  -d bootVersion=3.3.5 \
  -d baseDir=microservicio-historial-clinico \
  -d groupId=com.veterinaria \
  -d artifactId=microservicio-historial-clinico \
  -d name=microservicio-historial-clinico \
  -d packageName=com.veterinaria.historial \
  -d javaVersion=17 \
  -o microservicio-historial-clinico.zip

curl https://start.spring.io/starter.zip \
  -d dependencies=web,data-jpa,h2,validation \
  -d type=maven-project \
  -d language=java \
  -d bootVersion=3.3.5 \
  -d baseDir=microservicio-citas-agenda \
  -d groupId=com.veterinaria \
  -d artifactId=microservicio-citas-agenda \
  -d name=microservicio-citas-agenda \
  -d packageName=com.veterinaria.citas \
  -d javaVersion=17 \
  -o microservicio-citas-agenda.zip

curl https://start.spring.io/starter.zip \
  -d dependencies=web,data-jpa,h2 \
  -d type=maven-project \
  -d language=java \
  -d bootVersion=3.3.5 \
  -d baseDir=microservicio-inventario \
  -d groupId=com.veterinaria \
  -d artifactId=microservicio-inventario \
  -d name=microservicio-inventario \
  -d packageName=com.veterinaria.inventario \
  -d javaVersion=17 \
  -o microservicio-inventario.zip

curl https://start.spring.io/starter.zip \
  -d dependencies=web,data-jpa,h2 \
  -d type=maven-project \
  -d language=java \
  -d bootVersion=3.3.5 \
  -d baseDir=microservicio-facturacion \
  -d groupId=com.veterinaria \
  -d artifactId=microservicio-facturacion \
  -d name=microservicio-facturacion \
  -d packageName=com.veterinaria.facturacion \
  -d javaVersion=17 \
  -o microservicio-facturacion.zip

curl https://start.spring.io/starter.zip \
  -d dependencies=web,validation,actuator \
  -d type=maven-project \
  -d language=java \
  -d bootVersion=3.3.5 \
  -d baseDir=veterinaria-bff \
  -d groupId=com.veterinaria \
  -d artifactId=veterinaria-bff \
  -d name=veterinaria-bff \
  -d packageName=com.veterinaria.bff \
  -d javaVersion=17 \
  -o veterinaria-bff.zip
```

Opcion B en PowerShell (equivalente):

```powershell
Invoke-WebRequest "https://start.spring.io/starter.zip?type=maven-project&language=java&bootVersion=3.3.5&baseDir=veterinaria-bff&groupId=com.veterinaria&artifactId=veterinaria-bff&name=veterinaria-bff&packageName=com.veterinaria.bff&javaVersion=17&dependencies=web,validation,actuator" -OutFile veterinaria-bff.zip
```

## 4) Como ejecutar el prototipo en local

Abre 6 terminales en paralelo:

```bash
cd veterinaria-bff && mvn spring-boot:run
cd microservicio-historial-clinico && mvn spring-boot:run
cd microservicio-citas-agenda && mvn spring-boot:run
cd microservicio-inventario && mvn spring-boot:run
cd microservicio-facturacion && mvn spring-boot:run
cd notificaciones-faas && mvn azure-functions:run
```

Puertos:

- BFF: 8080
- Historial: 8081
- Citas: 8082
- Inventario: 8083
- Facturacion: 8084
- Azure Function local: 7071

## 5) Flujo de prueba rapido con curl

1. Crear cita via BFF

```bash
curl -X POST http://localhost:8080/api/bff/citas \
  -H "Content-Type: application/json" \
  -H "X-Auth-Token: demo-token" \
  -d '{"nombreCliente":"Ana Perez","nombreMascota":"Firulais","fechaHora":"2026-03-20T10:00:00","motivo":"Control"}'
```

2. Confirmar cita (dispara notificacion FaaS)

```bash
curl -X PUT http://localhost:8080/api/bff/citas/1/confirmar \
  -H "X-Auth-Token: demo-token"
```

3. Verificar logs de la Function en consola (simulacion envio correo/SMS)

## 6) Llamada HTTP desde microservicio-citas hacia Azure Function

La llamada se realiza en CitaService.confirmar():

- URL configurable: faas.notificaciones.url (application.properties)
- Metodo: POST
- Payload: citaId, cliente, mascota, fechaHora, estado
- Cliente HTTP: RestTemplate.postForEntity(...)

## 7) Frontend demo

Abre frontend/index.html en un navegador para simular acciones de agenda sobre el BFF.
