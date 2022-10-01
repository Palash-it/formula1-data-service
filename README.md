# Read Me First
The following was discovered as part of building this project:

* The original package name 'com.recommit.assignment.formula1.formula1-data-service' is invalid and this project uses 'com.recommit.assignment.formula1.formula1dataservice' instead.

# Formula 1 Data Service
Project Base URL: localhost:8081/formula1-data-service/

## Setup local environment
Needs to install `Java 11`, `Gradle 7.4.1`, `H2 in memory database` and `Intellij`.
As this is an assignment project, so i created only one property file for default profile.

## Setup database
I used in memory database H2 to keep the project simple. After starting the application open bellow link
to connect H2 console:
Database UI: localhost:8081/formula1-data-service/h2-admin
JDBC URL: jdbc:h2:/var/formula1/db/formula1db
User Name: sa
Password: 


## How to build the project
1. ### Clone the project from the repository
    

2. ### Build the project
        - Go to project directory
        - Run command: `gradlew build`

3. ### Run the project
    - Using IDE:
        - Open project in Intellij
        - Right-click on the main spring boot application class (`Formula1DataServiceApplication`) and click on Run

    - Run from terminal ( as a spring boot project):
        - Go to the project directory
        - Run this command: `gradle bootRun`

4. ### Access the application

    - Access the deployed application: [http://localhost:8081/formula1-data-service/]

## Access API documentation

Swagger API documentation can be found here: [http://localhost:8081/formula1-data-service/swagger-ui/index.html]

To use jwt token in Swagger Authorization dont add "Bearer word manually". Swagger will add it. Just copy and paste the JWT token

### Login Credential
username: admin
password: admin

