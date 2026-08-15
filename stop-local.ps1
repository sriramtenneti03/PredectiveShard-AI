$ErrorActionPreference = 'Stop'

$targets = @(
    'uvicorn',
    'java',
    'node'
)

foreach ($processName in $targets) {
    Get-CimInstance Win32_Process -Filter "Name LIKE '%$processName%'" | ForEach-Object {
        Stop-Process -Id $_.ProcessId -Force -ErrorAction SilentlyContinue
    }
}

Write-Host 'Local app processes have been stopped.'
