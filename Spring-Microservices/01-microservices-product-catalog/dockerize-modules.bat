@REM The way jib-plugin works with children pom.xml is unnecessarily complex and coupled. So I just created this simple .bat which perfectly does the job.

@echo off
setlocal

set modules=api-gateway discovery-server inventory-service notification-service order-service product-service

for %%s in (%modules%) do (
    cd %%s
    echo Building %%s module...
    call mvn jib:dockerBuild
    cd ..
)

endlocal
echo Build completed successfully.