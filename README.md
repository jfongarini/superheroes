# Superheroes

<!-- TABLE OF CONTENTS -->
<details open="open">
  <summary>Table of Contents</summary>
  <ol>
    <li>
      <a href="#about-the-project">About The Project</a>
      <ul>
        <li><a href="#built-with">Built With</a></li>
      </ul>
    </li>
    <li>
      <a href="#getting-started">Getting Started</a>
      <ul>
        <li><a href="#prerequisites">Prerequisites</a></li>
        <li><a href="#installation">Installation</a></li>
        <li><a href="#run-using-docker">Run using Docker</a></li>        
      </ul>
    </li>
    <li>
      <a href="#roadmap">Roadmap</a>
      <ul>
        <li><a href="#default-credentials">Default credentials</a></li>      
      </ul>
    </li>
    <li><a href="#contact">Contact</a></li>
  </ol>
</details>



<!-- ABOUT THE PROJECT -->
## About The Project

This documentation provides an overview of the Superheroes application, which is developed using Spring Boot, Maven, and Java 21. The application utilizes an H2 database to store superhero data and is secured with basic authentication using Spring Security.
The Superheroes application is a system that allows the following operations:
Create, Read, Update, and Delete (CRUD) superhero information.
Search superheroes by superhero name.

### Built With

* Spring Boot
* Maven
* Java 21
* H2 Database
* Jacoco

<!-- GETTING STARTED -->
## Getting Started

This is an example of how you may give instructions on setting up your project locally.
To get a local copy up and running follow these simple example steps.

### Prerequisites

* Java Development Kit (JDK) 21: Install JDK 21 on your system. You can download JDK 21 from the official Oracle website or use an alternative distribution like OpenJDK.
* Maven: Maven is used for project management and dependency resolution. Make sure Maven is installed on your system. You can download Maven from the official Apache Maven website and follow the installation instructions.

### Installation

1. Clone the repository from GitHub using the following command:
    ```sh
   git clone https://github.com/jfongarini/superheroes.git
   ```
2. Navigate to the project directory:
   ```sh
   cd superheroes
   ```
3. Build the project using Maven:
   ```sh
   mvn clean install
   ```
4. Once the build is successful, run the application using Maven:
   ```sh
   mvn spring-boot:run
   ```

### Run using Docker

1. Do step 1 and 2 of Installation guide and run:
    ```sh
   docker build -t superheroes .
   ```
2. Execute:
   ```
   docker run -p 8080:8080 <<Your image id>>
   ```


<!-- ROADMAP -->
## Roadmap

Once application is running go to the following url to check the API specifications:

 [http://localhost:8080/api/swagger-ui/index.html](http://localhost:8080/api/swagger-ui/index.html)

### Default credentials:
The application is secured by basic authentication, the default credentials are:
* User:
    ```
   mindata
   ```
* Password:
    ```
   superheroes
   ```
<!-- COVERAGE REPORT -->
## Generating Coverage Report

To generate a coverage report of the tests, follow these steps:

1. Execute the following command at the root of the project:
```sh
   mvn clean test
```

2. After the tests are completed, execute the following command to generate the coverage report:
```sh
   mvn jacoco:report
```
3. The coverage report will be available in the directory target/site/jacoco/index.html.


<!-- CONTACT -->
## Contact

Juan Francisco Ongarini - jfongarini@gmail.com

