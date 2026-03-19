param(
    [switch]$DryRun
)

$ErrorActionPreference = "Stop"

$root = "C:\Users\kathe\Downloads\cloud_ii\veterinaria-faas-notificaciones"
$javaHome = "C:\Users\kathe\.jdk\jdk-21.0.8"
$mavenBin = "C:\Users\kathe\.maven\apache-maven-3.9.14\bin"
$funcBin = "C:\Program Files\Microsoft\Azure Functions Core Tools"

if (-not (Test-Path $root)) {
    throw "No existe la carpeta raiz: $root"
}
if (-not (Test-Path $javaHome)) {
    throw "No existe JAVA_HOME esperado: $javaHome"
}
if (-not (Test-Path $mavenBin)) {
    throw "No existe Maven esperado: $mavenBin"
}

$services = @(
    @{ Name = "veterinaria-bff"; Path = Join-Path $root "veterinaria-bff"; Command = "mvn spring-boot:run" },
    @{ Name = "microservicio-historial-clinico"; Path = Join-Path $root "microservicio-historial-clinico"; Command = "mvn spring-boot:run" },
    @{ Name = "microservicio-citas-agenda"; Path = Join-Path $root "microservicio-citas-agenda"; Command = "mvn spring-boot:run" },
    @{ Name = "microservicio-inventario"; Path = Join-Path $root "microservicio-inventario"; Command = "mvn spring-boot:run" },
    @{ Name = "microservicio-facturacion"; Path = Join-Path $root "microservicio-facturacion"; Command = "mvn spring-boot:run" },
    @{ Name = "notificaciones-faas"; Path = Join-Path $root "notificaciones-faas"; Command = "mvn package; mvn azure-functions:run" }
)

foreach ($svc in $services) {
    if (-not (Test-Path $svc.Path)) {
        throw "No existe carpeta del servicio $($svc.Name): $($svc.Path)"
    }
}

$envPrefix = @(
    "`$env:JAVA_HOME = '$javaHome'",
    "`$env:PATH = '$funcBin;$javaHome\bin;$mavenBin;' + `$env:PATH"
) -join "; "

Write-Host "Levantando servicios desde: $root" -ForegroundColor Cyan
Write-Host "JAVA_HOME: $javaHome" -ForegroundColor Cyan

foreach ($svc in $services) {
    $script = "$envPrefix; Set-Location '$($svc.Path)'; $($svc.Command)"

    if ($DryRun) {
        Write-Host "[DRY-RUN] $($svc.Name): $script" -ForegroundColor Yellow
        continue
    }

    Start-Process -FilePath "powershell.exe" `
        -ArgumentList "-NoProfile", "-ExecutionPolicy", "Bypass", "-Command", $script `
        -WindowStyle Normal

    Write-Host "Iniciado: $($svc.Name)" -ForegroundColor Green
}

if ($DryRun) {
    Write-Host "Dry run finalizado. No se lanzaron procesos." -ForegroundColor Yellow
} else {
    Write-Host "Todos los procesos fueron lanzados. Espera 20-40 segundos para que arranquen." -ForegroundColor Green
    Write-Host "Prueba rapida BFF: http://localhost:8080/api/bff/health" -ForegroundColor Green
}
