# Spring Boot App

Project contains Spring Data Jpa, Spring Security

[![Java v17][shield-java]](https://openjdk.java.net/projects/jdk/17/)
[![Spring Framework v6][shield-spring]](https://jakarta.ee/specifications/platform/10/)
[![Spring Boot v3][shield-spring-boot]](https://jakarta.ee/specifications/platform/10/)

## Examples

Each example is provided as different branch. New features are made in incremental way, so each subsequent branch is
based on previous one. In some cases there can be parallel patch in branches incremental (marked by origin branch
column).

| branch             | origin branch      | description                       |
|--------------------|--------------------|-----------------------------------|
| spring-boot        | main               | Spring Boot & Spring Context      |
| feature/data-jpa   | feature/data-jpa   | Spring Data & Jakarta Persistence |
| ''/spring-security | ''/spring-security | Spring Security                   |

## Requirements

The list of tools required to build and run the project:

* Open JDK 17
* Gradle 8.7

## Building

In order to build project use:

```bash
docker compose up -d
```

## Configuration

Configuration can be updated in `application-dev.yml` or using environment variables.

## Running

In order to run using embedded Apache Tomcat server use:

```bash
java -jar build/libs/app-jpa-1.0.0-SNAPSHOT.jar
```

If your default `java` is not from JDK 17 or higher use:

```bash
<path_to_jdk_home>/bin/java -jar build/libs/app-jpa-1.0.0-SNAPSHOT.jar
```

## Stuff worth paying attention

New stuff added in other branches worth paying attention.


* `build.gradle` - project configuration with Spring dependencies,

Examples: 
* `SimpleRpgApplication` - Spring Boot application entry point, bean definition,
* `creature/entity/*` - entity classes for creatures,
* `character/entity/*` - entity classes for characters,
* `character/dto/*` - DTO classes for characters,
* `character/function/*` - mapping functions between entities and DTO,
* `user/entity/*` - entity classes for users,
* `user/function/*` - mapping functions between entities and DTO,
* `serialization/component/CloningUlility.java` - cloning utility for in-memory data source,
* `datastore/component/DataStore.java` - in-memory data source,
* `repository/CrudRepository.java` - base repository interface,
* `character/repository/api/*` - repositories declarations,
* `character/repository/memory/*` - simple in-memory repositories,
* `character/service/api/*` - services declarations,
* `character/service/impl/*` - services implementations,
* `character/controller/api/*` - controllers declarations,
* `character/controller/impl/*` - controllers implementations,
* `user/repository/api/*` - repositories declarations,
* `user/repository/memory/*` - simple in-memory repositories,
* `user/service/api/*` - services declarations,
* `user/service/impl/*` - services implementations,
* `user/controller/api/*` - controllers declarations,
* `user/controller/impl/*` - controllers implementations,
* `initialize/InitializeData.java` - test data initialization on startup,
* `cmd/ApplicationCommand.java` - command line interface for application,
* `ansi/function/ImageToAnsiArtFunction.java` - conversion of raster image to ANSI image.

## License

Project is licensed under the [MIT](LICENSE) license.


[shield-mit]: https://img.shields.io/badge/license-MIT-blue.svg
[shield-java]: https://img.shields.io/badge/Java-17-blue.svg
[shield-spring]: https://img.shields.io/badge/Spring%20Framework-6-blue.svg
[shield-spring-boot]: https://img.shields.io/badge/Spring%20Boot-3-blue.svg

