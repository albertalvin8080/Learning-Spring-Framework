@REM The way jib-plugin works with children pom.xml is unnecessarily complex and coupled. So I just created this simple .bat which perfectly does the job.

cd .\api-gateway
call mvn jib:dockerBuild

cd ..\discovery-server
call mvn jib:dockerBuild

cd ..\product-service
call mvn jib:dockerBuild

cd ..\inventory-service
call mvn jib:dockerBuild

cd ..\order-service
call mvn jib:dockerBuild

cd ..\notification-service
call mvn jib:dockerBuild

cd ..