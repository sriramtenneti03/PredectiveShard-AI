$ErrorActionPreference = 'Stop'

$root = Split-Path -Parent $MyInvocation.MyCommand.Path
Set-Location $root

if (-not (Test-Path .env)) {
    Copy-Item .env.example .env
}

$commands = @{
    java = 'java'
    node = 'node'
    npm = 'npm'
    python = 'python'
}

foreach ($entry in $commands.GetEnumerator()) {
    if (-not (Get-Command $entry.Key -ErrorAction SilentlyContinue)) {
        Write-Error "Required command not found: $($entry.Value)"
        exit 1
    }
}

$mlDir = Join-Path $root 'ml-service'
$serverDir = Join-Path $root 'server'
$clientDir = Join-Path $root 'client'

if (-not (Test-Path (Join-Path $mlDir '.venv'))) {
    Write-Host 'Creating Python virtual environment for the ML service...'
    & python -m venv (Join-Path $mlDir '.venv')
}

$pythonExe = Join-Path $mlDir '.venv\Scripts\python.exe'
& $pythonExe -m pip install --upgrade pip
& $pythonExe -m pip install -r (Join-Path $mlDir 'requirements.txt')

Write-Host 'Starting ML service...'
Start-Process powershell -ArgumentList "-NoExit", "-Command", "Set-Location '$mlDir'; & '$pythonExe' -m uvicorn app.main:app --host 0.0.0.0 --port 8000" -WorkingDirectory $mlDir

Write-Host 'Starting Spring Boot server...'
Start-Process powershell -ArgumentList "-NoExit", "-Command", "Set-Location '$serverDir'; mvn spring-boot:run" -WorkingDirectory $serverDir

Write-Host 'Installing frontend dependencies...'
Set-Location $clientDir
npm install

Write-Host 'Starting React frontend...'
Start-Process powershell -ArgumentList "-NoExit", "-Command", "Set-Location '$clientDir'; npm run dev -- --host 0.0.0.0" -WorkingDirectory $clientDir

Write-Host 'The app is starting. Open http://localhost:5173'
Write-Host 'Ensure PostgreSQL, Redis, and Kafka are already running locally.'
