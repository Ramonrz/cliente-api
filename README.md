
# cliente-api

API Rest, gerenciamento de clientes.

[![Build Status](https://travis-ci.org/joemccann/dillinger.svg?branch=master)](https://travis-ci.org/joemccann/dillinger)

- Java 8
- Spring Boot 2.4.2
    - spring-boot-starter-web
    - spring-boot-starter-data-jpa
    - spring-boot-starter-validation
    - spring-boot-starter-actuator
    - spring-boot-starter-test
    - springfox-boot-starter
    - modelmapper
    - lombok

# Execução do projeto 

### Passo 1
```shell script
$ git clone https://github.com/Ramonrz/cliente-api.git
```
### Passo 2 
```shell script
$ cd cliente-api
```
### Passo 3
```shell script
$ mvn clean package
```
### Passo 4
```shell script
$ cd ..
```
### Passo 5

```shell script
$ docker-compose up --build
```
## Swagger
http://localhost:8080/swagger-ui/#/clientes

## Health
http://localhost:8080/actuator/info
http://localhost:8080/actuator/health
