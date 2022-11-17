# ToDo Web service

A ToDo web service produces restful api for to do console app
[ToDo client application](https://github.com/MohamedEldefrawy/ToDo-Client)

## Table of contents

- [Overview](#overview)
    - [Built with](#built-with)

## Overview

1) Clone the project
   ``` https://github.com/MohamedEldefrawy/ToDo-Client.git```
2) in the project directory run the following
    ```
   mvn clean install
   ```
3) Run sql scripts located in ```src/resources```
4) Change DB username and PW located in Configuration class
   ```src/main/java/com.todo/config/DatabaseConfiguration```
5) Postman collection provided in
   ```src/main/resources```

### Built with

* [Java 17](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html)
* [Jakarta](https://jakarta.ee/)
* [Jackson](https://github.com/FasterXML/jackson)
* [MysqlConnector](https://dev.mysql.com/downloads/connector/j/)
