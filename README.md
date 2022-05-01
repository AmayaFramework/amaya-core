# amaya-core [![maven-central](https://img.shields.io/maven-central/v/io.github.amayaframework/core-api?color=blue)](https://repo1.maven.org/maven2/io/github/amayaframework/core-api/)

The basis of the amaya framework, which provides basic functionality.
It includes basic implementations of routes, filters, controllers, http transactions and everything related to them, 
method packaging, as well as assistive utilities.

## Getting Started

To install it, you will need:
* java 8+
* [classindex](https://github.com/atteo/classindex)
* some implementation of slf4j
* Maven/Gradle

## Installing

### Gradle dependency

```Groovy
dependencies {
   implementation group: 'org.atteo.classindex', name: 'classindex', version: '3.4'
   annotationProcessor group: 'org.atteo.classindex', name: 'classindex', version: '3.4'
   implementation group: 'io.github.amayaframework', name: 'amaya-core', version: 'LATEST'
}
```

### Maven dependency
```
<dependency>
    <groupId>org.atteo.classindex</groupId>
    <artifactId>classindex</artifactId>
    <version>3.4</version>
</dependency>

<dependency>
    <groupId>io.github.amayaframework</groupId>
    <artifactId>amaya-core</artifactId>
    <version>LATEST</version>
</dependency>
```

## Usage example

## Built With

* [Gradle](https://gradle.org) - Dependency management
* [classindex](https://github.com/atteo/classindex) - Annotation scanning
* [jeflect](https://github.com/RomanQed/jeflect) - Method wrapping
* [slf4j](https://www.slf4j.org) - Logging facade
* [javax.servet](https://docs.oracle.com/javaee/7/api/javax/servlet/Servlet.html) - Servlets
* [http-utils](https://github.com/AmayaFramework/http-utils) - Some http stuff
* [java-utils](https://github.com/RomanQed/java-utils) - Pipelines and other stuff
* [amaya-filters](https://github.com/AmayaFramework/amaya-filters) - Implementation of string and content filters

## Authors
* **RomanQed** - *Main work* - [RomanQed](https://github.com/RomanQed)
* **max0000402** - *Technical advices and ideas for features* - [max0000402](https://github.com/max0000402)

See also the list of [contributors](https://github.com/AmayaFramework/amaya-core-api/contributors) who participated 
in this project.

## License

This project is licensed under the Apache License Version 2.0 - see the [LICENSE](LICENSE) file for details

## Acknowledgments

Thanks to everyone who was interested in this library, gave advice and suggested ideas.