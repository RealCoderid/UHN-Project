------Env used:
Java version: 1.8.0_311
Apache Maven 3.8.2 
Web Framework - Spring boot
Database - H2 In-Memory database (embedded in the fat jar)


------Scripts
	1) start.sh - to start the app, logs will get appended to a file in same dir
	2) stop.sh  - stop the app
	3) mvn clean install  - To rebuild the project. If the project is rebuilt, Replace the new jar to the root dir.
	4) man test - run Junit tests


------Swagger URL:
http://localhost:8080/swagger-ui.html


------Curl commands
1) List patient  -  2 patients are added while app startup for the ease of testing

    curl -X GET "http://localhost:8080/patient/" -H "accept: application/json"


2)Add a patient -  add 4 patients request

    curl -X POST "http://localhost:8080/patient/" -H "accept: application/json" -H "Content-Type: application/json" -d "[ { \"firstName\": \"victor\", \"lastName\": \"axelsen\", \"dateOfBirth\": \"1994-02-14\" }, { \"firstName\": \"ashna\", \"lastName\": \"bindra\", \"dateOfBirth\": \"1987-05-17\" }, { \"firstName\": \"ashna\", \"lastName\": \"sindhu\", \"dateOfBirth\": \"1997-12-24\" }, { \"firstName\": \"Kento\", \"lastName\": \"baby\", \"dateOfBirth\": \"1982-08-28\" }]"


3)Get Patient by ID - 

    curl -X GET "http://localhost:8080/patient/1" -H "accept: application/json"

4)Get Patient by name

    curl -X GET "http://localhost:8080/patient/name?firstName=kento" -H "accept: application/json"