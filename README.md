# Content

**1. General overview**   
**2. How to start the project**    
**3. Architecture**   
**4. Microservices description**  

# 1. General overview 

In this repository, we have designed a vacation management platform with a microservice architecture. Each service is created as an individual module (a standalone Spring Boot application) with its own database (see the **docker-compose.yml** file for each module). Microservices communicate synchronously (Feign client) and asynchronously (using Kafka messages).   

Docker Desktop with the databases for each service:  

![image](https://github.com/user-attachments/assets/07c60bb2-245a-49f9-99f7-fc933cd0b126)

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

Runs on **https://localhsot:8085**. Spring Boot service which contains the DTO models user in the microservice communication. Each service uses adapters (Adapter pattern) when transfering the information between services. It has no database an it is imported as maven dependency in the other services.

## 4.2 Kafka service

Runs on **https://localhsot:8086**. Spring Boot service which contains the message models used in the asynchronous communication. It is imported as maven dependency in the other services.

## 4.3 User Management service   

Runs on **https://localhsot:8082**. Spring Boot service which contains the User and Role entities. Has PostgreSQL database (official docker image - see **docker-compose.yml** file in the root folder). The sample data are imported via **liquibase** (see **resources/db/changeLog/changeLog.xml** file) 

**Database design**

## Database Table Design

### `roles` Table  

| Column Name    | Data Type         | Constraints                          | Description             |
|----------------|-------------------|--------------------------------------|-------------------------|
| `id`           | `INT`             | `PRIMARY KEY`, `AUTO_INCREMENT`      | Unique role ID          |
| `name`         | `VARCHAR(255)`     | `NOT NULL`                           | Name of the role        |

### `users` Table

| Column Name    | Data Type         | Constraints                          | Description             |
|----------------|-------------------|--------------------------------------|-------------------------|
| `id`           | `INT`             | `PRIMARY KEY`, `AUTO_INCREMENT`      | Unique user ID          |
| `username`     | `VARCHAR(255)`     | `NOT NULL`                           | User's username         |
| `first_name`   | `VARCHAR(255)`     | `NULL`                               | User's first name       |
| `last_name`    | `VARCHAR(255)`     | `NULL`                               | User's last name        |
| `role_id`      | `INT`             | `FOREIGN KEY` (references `roles(id)`) | User's assigned role    |


**REST endpoints**

 - @GetMapping("/roles") - Retrieve all roles
 - @PostMapping("/role/{role}") - Create new Role
 - @PutMapping("/role/{oldRole}/{newRole}") - Update the role name
 - @DeleteMapping("/role/{role}") - Delete a role
 - @GetMapping("/role/{role}/users") - Retrieves all user with a specific role
 - @GetMapping("/roles/users-count") - Retrieves user count per role for all roles
 - @GetMapping("/search") - Searches users by first name and uses Spring Data JPA pagination for the results
 - @GetMapping("/users") - Retrieves all users and uses Spring Data JPA pagination for the results
 - @GetMapping("/user/{id}") - Retrieves user by ID. (used in microservice communication)
 - @GetMapping("/user/username/{username}") - Retrieves user by username (unique user field)
 - @PutMapping("/user") - Updates user
 - @PutMapping("/user/{username}/{role}") - Add role to a user
 - @DeleteMapping("/user/{username}") - Deletes user by username

## 4.4 Project Management service

Runs on **https://localhsot:8083**. Spring Boot service which contains the Project and Team entities. The service also has User entity which stores **only** the user ID. When user information is needed Feign client call is made to User management service to retrieve full user information. Has PostgreSQL database (official docker image - see **docker-compose.yml** file in the root folder). The sample data are imported via **liquibase** (see **resources/db/changeLog/changeLog.xml** file) 

**Database design**

## Database Table Design

### `projects` Table

| Column Name    | Data Type         | Constraints                          | Description             |
|----------------|-------------------|--------------------------------------|-------------------------|
| `id`           | `INT`             | `PRIMARY KEY`, `AUTO_INCREMENT`      | Unique project ID       |
| `name`         | `VARCHAR(255)`    | `NOT NULL`                           | Name of the project     |
| `description`  | `TEXT`            | `NULL`                               | Description of the project |

### `teams` Table

| Column Name    | Data Type         | Constraints                          | Description             |
|----------------|-------------------|--------------------------------------|-------------------------|
| `id`           | `INT`             | `PRIMARY KEY`, `AUTO_INCREMENT`      | Unique team ID          |
| `name`         | `VARCHAR(255)`     | `NOT NULL`                           | Name of the team        |
| `leader_id`    | `INT`             | `NULL`, `FOREIGN KEY` (references `users(id)`) | Leader of the team      |

### `team_projects` Table (Many-to-Many relationship between `teams` and `projects`)

| Column Name    | Data Type         | Constraints                          | Description             |
|----------------|-------------------|--------------------------------------|-------------------------|
| `team_id`      | `INT`             | `FOREIGN KEY` (references `teams(id)`) | Associated team ID      |
| `project_id`   | `INT`             | `FOREIGN KEY` (references `projects(id)`) | Associated project ID   |

### `team_members` Table (Many-to-Many relationship between `teams` and `users`)

| Column Name    | Data Type         | Constraints                          | Description             |
|----------------|-------------------|--------------------------------------|-------------------------|
| `team_id`      | `INT`             | `FOREIGN KEY` (references `teams(id)`) | Team member's team ID   |
| `user_id`      | `INT`             | `FOREIGN KEY` (references `users(id)`) | Team member's user ID   |

### `users` Table

| Column Name    | Data Type         | Constraints                          | Description             |
|----------------|-------------------|--------------------------------------|-------------------------|
| `id`           | `INT`             | `PRIMARY KEY`, `AUTO_INCREMENT`      | Unique user ID          |


**REST endpoints**

 - @GetMapping("/team/{name}") - Retrieves team by name
 - @GetMapping("/teams") - Retrieves all teams and uses Spring Data JPA pagination for the results
 - @PostMapping("/team") - Create a team
 - @PutMapping("/team/{name}/member/{id}/add") - Add user to a team
 - @PutMapping("/team/{name}/member/{id}/remove") - Removes user from a team
 - @PatchMapping("/team/{teamName}/add-project/{projectName}") - Add project to a team
 - @PatchMapping("/team/{teamName}/remove-project/{projectName}") - Removes project from a team
 - @DeleteMapping("/team/{name}") - Deletes a team by name
 - @GetMapping("/teams/search") - Seraches for a team by team name and project name. Uses pagination
 - @GetMapping("/projects") - Retrieves all projects and uses Spring Data JPA pagination for the results
 - @GetMapping("/project/{name}") - Retrieves project by name
 - @PatchMapping("/project/{name}") - Updates project
 - @DeleteMapping("/project/{name}") - Deletes project by name
 - @GetMapping("/project/{name}/teams") - Retrieves all project's teams and uses Spring Data JPA pagination for the results

## 4.5 Vacation Management service  

Runs on **https://localhsot:8084**. Contains the Vacations entities (documents). As a database it uses MongoDB (official docker image - see **docker-compose.yml** file in the root folder). The sample data are imported via docker volume and .js script on container start up (see **resources/mongo/init.js**)

**Database design**    

**Vacation Table**   

| Field         | Type            | Description                                   |
|---------------|-----------------|-----------------------------------------------|
| `_id`         | String          | Unique identifier for the vacation document (MongoDB ObjectId) |
| `applicant`   | String          | Name of the applicant requesting the vacation |
| `type`        | String          | Type of the vacation (e.g., Paid, Sick, etc.) |
| `startDate`   | LocalDate       | The start date of the vacation                |
| `endDate`     | LocalDate       | The end date of the vacation                  |
| `submittedOn` | LocalDate       | The date the vacation request was submitted   |
| `halfDay`     | Boolean         | Whether the vacation is a half-day (true/false)|
| `approved`    | Boolean         | Whether the vacation is approved (true/false) |
| `list`        | String          | Additional information or list related to the vacation (e.g., reasons) |


Overview of the sample data imported in the **storage** database, **vacations** collection. 

**Note** 

For a sick leave document I used a sample picture, because I do not want to upload actula sick leave document because of the personal data in it. However, the logic with real docuemnts is the same, since I store the bytes of the document in MongoDB and the endpoint in Gateway API accepts Multipart file. 

![image](https://github.com/user-attachments/assets/1ab5877a-82c0-4ab7-bc38-0437ab014531)

**REST endpoints**

 -  @GetMapping("/user/{name}/vacations") - Retrieve all user vacations
 -  @PatchMapping("/vacation/{id}") - Update vacation (startDate, endDate, halfDay)
 -  @DeleteMapping("/vacation/{id}") - Delete vacation
 -  @PatchMapping("/vacation/{id}/approve") - Approve vacation

**Kafka Messaging**

New vacation request is created via Kafka messaging from the API Gateway service to the Vacation Management service.

## 4.6 API Gateway service    

Runs on **https://localhsot:8080**. Serves as a single entry point for the REST calls.   

**REST endpoints**  

 - @GetMapping("/api/roles") - Retrieve all roles

   ![image](https://github.com/user-attachments/assets/e816487d-abaf-4b9c-9b54-7bc3e43f7396)

 - @PostMapping("/api/role/{role}") - Create new role

   ![image](https://github.com/user-attachments/assets/a2d79726-52ab-45ed-b095-ca0f07ac0709)

 - @PutMapping("/api/role/{oldName}/{newName}") - Rename role

   ![image](https://github.com/user-attachments/assets/eac361a1-d081-4a7d-836b-96429e7df090)

 - @DeleteMapping("/api/role/{role}") Deletes a role by name

   ![image](https://github.com/user-attachments/assets/992914b5-c6f8-41e6-8b77-e9ecee33637c)

 - @GetMapping("/api/role/{role}/users") - Retrieve users with a specific role

   ![image](https://github.com/user-attachments/assets/68bc2004-aa5f-418f-b689-f7857229ee66)

 - @GetMapping("/api/roles/users-count")
 
   ![image](https://github.com/user-attachments/assets/d2e566b7-2e33-41ba-92e8-d14b8de8819e)

 - @GetMapping("/users/search")

   ![image](https://github.com/user-attachments/assets/c951d3ec-3405-40d8-b4cc-490c41578e0a)

 - @GetMapping("/users")

   ![image](https://github.com/user-attachments/assets/7bae388b-1b2a-4499-9d3f-160c20c7e429)

 - @GetMapping("/user/{username}")

   ![image](https://github.com/user-attachments/assets/2921ac87-9d49-46a6-bdc9-6b6612da7c02)

 - @PutMapping("/user")

   ![image](https://github.com/user-attachments/assets/2e476a82-68d7-4104-a77c-71702f7a59a6)

   With wrong data returns 400 code

   ![image](https://github.com/user-attachments/assets/8ca38e3d-e7bb-4de1-a462-94619e39fa45)

 - @PatchMapping("/user/{username}/{role}")

   ![image](https://github.com/user-attachments/assets/388ad5ab-b169-43c5-b2f0-c8695b304a4c)

 - @DeleteMapping("/user/{username}")

   ![image](https://github.com/user-attachments/assets/2a4e2ed4-5f1b-4f5f-b262-c9688f81417e)

 - @GetMapping("/teams") (Project service retrieves user information from UserManagement)

   ![image](https://github.com/user-attachments/assets/bf274bf3-dd61-46c3-9cf1-fcca69129d61)

 - @GetMapping("/team/{name}") (Project service retrieves user information from UserManagement)

   ![image](https://github.com/user-attachments/assets/4665bbd4-78df-4efe-a805-1f2aee36c7f6)

 - @PostMapping("/team") (Project service retrieves user information from UserManagement)

 ----------------------- Insert image----------------------------

 - @PatchMapping("/team/{name}/member/{id}/add") (Project service retrieves user information from UserManagement)

   ![image](https://github.com/user-attachments/assets/f70236cf-24d3-4a6e-aa12-c4d83d4b1970)

 - @PatchMapping("/team/{name}/member/{id}/remove") (Project service retrieves user information from UserManagement)

   ![image](https://github.com/user-attachments/assets/42d62ae2-13ed-4239-82c3-13b18e74da71)

 - @PatchMapping("/team/{teamName}/add-project/{projectName}") (Project service retrieves user information from UserManagement)

   ![image](https://github.com/user-attachments/assets/2002bfa9-fd37-4f5d-a3ae-6bae891524f7)

- @PatchMapping("/team/{teamName}/remove-project/{projectName}") (Project service retrieves user information from UserManagement)

  ![image](https://github.com/user-attachments/assets/f5479d13-fa87-46ce-8ee8-01d6640fb68e)

- @DeleteMapping("/team/{name}")

  ![image](https://github.com/user-attachments/assets/906e6789-6b8a-4995-bc2d-ef5a6e315a0b)

- @GetMapping("/teams/search")

  ![image](https://github.com/user-attachments/assets/5aa0bdaf-4250-498a-bb7f-ce0d576fdc29)

- @GetMapping("/projects") - Retrieves all project. Uses JPA pagination

  ![image](https://github.com/user-attachments/assets/7d852e62-f742-4eb9-85b6-3570d899e81a)

- @GetMapping("/project/{name}") - Retrieve project by name

  ![image](https://github.com/user-attachments/assets/e17e0b49-5ad3-4a63-a9a4-65a030b8e36d)

- @PatchMapping("/project/{name}") Updates a project description

  ![image](https://github.com/user-attachments/assets/7fc433e0-425d-4516-940e-43073794bff9)

- @DeleteMapping("/project/{name}") Deletes a project

  ![image](https://github.com/user-attachments/assets/a0c23b8f-c45e-47e4-b7dd-a9988168c897)

- @GetMapping("/project/{name}/teams") Retrieves all team working on a project. Uses JPA pagination

  ![image](https://github.com/user-attachments/assets/d43b7bdf-2ff2-444a-8e7d-8b1dca3d2ae1)


















