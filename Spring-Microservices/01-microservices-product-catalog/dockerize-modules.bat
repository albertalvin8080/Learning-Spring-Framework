@REM The way jib-plugin works with children pom.xml is unnecessarily complex and coupled. So I just created this simple .bat which perfectly does the job.

cd .\api-gateway
mvn jib:dockerBuild

cd ..\discovery-server
mvn jib:dockerBuild

cd ..\inventory-service
mvn jib:dockerBuild

cd ..\order-service
mvn jib:dockerBuild

cd ..\notification-service
mvn jib:dockerBuild

cd ..