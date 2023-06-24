@echo off

cls
cd C:\intership\newsletter-intership-proxym\backend\newsletter-application
echo Building the application... 
mvn clean package -DskipTests && cd target && cls && echo Running the application... && java -jar newsletter-application-0.0.1-SNAPSHOT.jar
