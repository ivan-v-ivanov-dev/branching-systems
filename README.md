# Content

**1. General overview**   
**2. How to start the project**    
**3. Architecture** 
**4. Microservices description**  

# 1. General overview 

In this repository, we have designed a vacation management platform with a microservice architecture. Each service is created as an individual module (a standalone Spring Boot application) with its own database (see the **docker-compose.yml** file for each module). Microservices communicate synchronously (Feign client) and asynchronously (using Kafka messages).   

Docker Desktop with the databases for each service:  

![image](https://github.com/user-attachments/assets/9495889d-4758-4a62-8728-3fd16955c7da)

# 2. How to start the project 

- Git clone the repository and on first place start the **docker-compose.yml** file in each service - it contains the **PostreSQL** database (as official docker image) for each service. In all services the sample data will be imported automatically, since I am using **Liquibase** (as official docker image) which depends on the database and imports the data via docker volume (see **resources/db/changeLog/changeLog.xml** for each service).    
 - As a second step, start each service individually since each service is a standalone Spring Boot application.     
 - The **Vacation Manager** Gateway API application runs on **http://localhost:8080/**

**Note**   
Keep an eye on the liquibase container, because sometimes needs to be restarted after the database has starter. The successful data import will be confirmed via log message in the liquibase container.

![image](https://github.com/user-attachments/assets/3ed854d0-f07f-47f6-ad56-e1de9495838f)

# 3. Architecture  

The application has a microservice architecture using API Gateway service (API Gateway pattern) as an entry point for the RESTful calls from the front-end and 4 servies - **Model service** (containing the DTO objects used between the services synchonous communication), **User Management service** (containing the User and Role entities), **Project Management service** (containing the Project and Team entities) and a **Vacation Management service** (containing the Vacation entities). Each service has its' own relational database instantiated locally via **docker-compose.yml** file.

............. insert architecture diagram ................

# 4. Microservices description

## 4.1 Model service  

Runs on **https://localhsot:8085**. Spring Boot service which contains the DTO models user in the microservice communication. Each uservice uses adapters (Adapter pattern) when transfering the information between services. It has no database.  

## 4.2 User Management service   

Runs on **https://localhsot:8082**. Spring Boot service which contains the User and Role entities. Has PostgreSQL database (official docker image - see **docker-compose.yml** file in the root folder). The sample data are imported via **liquibase** (see **resources/db/changeLog/changeLog.xml** file) 

**Database design**

...........................

**REST endpoints**

 - @GetMapping("/roles") - Retrieve all roles
 - @PostMapping("/role/{role}") - Create new Role
 - @PutMapping("/role/{oldRole}/{newRole}") - Update the role name
 - @DeleteMapping("/role/{role}") - Delete a role
 - @GetMapping("/role/{role}/users") - Retrieves all user with a specific role
 - @GetMapping("/roles/users-count") - Retrieves user count per role for all roles
 - @GetMapping("/search") - Searches users by first name and uses Spring Data JPA pagination for the results
 - @GetMapping("/user/{id}") - Retrieves user by ID. (used in microservice communication)
 - @GetMapping("/user/username/{username}") - Retrieves user by username (unique user field)
 - @PutMapping("/user") - Updates user
 - @PutMapping("/user/{username}/{role}") - Add role to a user
 - @DeleteMapping("/user/{username}") - Deletes user by username

## 4.2 Project Management service

Runs on **https://localhsot:8083**. Spring Boot service which contains the Project and Team entities. The service also has User entity which stores **only** the user ID. When user information is needed Feign client call is made to User management service to retrieve full user information. Has PostgreSQL database (official docker image - see **docker-compose.yml** file in the root folder). The sample data are imported via **liquibase** (see **resources/db/changeLog/changeLog.xml** file) 

**Database design**

...........................

**REST endpoints**

 - @GetMapping("/team/{name}") - Retrieves team by name
 - @GetMapping("/teams") - Retrieves all teams and uses Spring Data JPA pagination for the results
 - @PostMapping("/team") - Create a team
 - @PutMapping("/team/{name}/member/{id}/add") - Add user to a team
 - @PutMapping("/team/{name}/member/{id}/remove") - Removes user from a team
 - @PutMapping("/team/{teamName}/add-project/{projectName}") - Add project to a team
 - @PutMapping("/team/{teamName}/remove-project/{projectName}") - Removes project from a team
 - @DeleteMapping("/team/{name}") - Deletes a team by name
 - @GetMapping("/teams/search") - Seraches for a team by team name and project name. Uses pagination
 - @GetMapping("/projects") - Retrieves all projects and uses Spring Data JPA pagination for the results
 - @GetMapping("/project/{name}") - Retrieves project by name
 - @PutMapping("/project/{name}") - Updates project
 - @DeleteMapping("/project/{name}") - Deletes project by name
 - @GetMapping("/project/{name}/teams") - Retrieves all project's teams and uses Spring Data JPA pagination for the results


