# amaya-core [![maven-central](https://img.shields.io/maven-central/v/io.github.amayaframework/amaya-core/2.0.0?color=blue)](https://repo1.maven.org/maven2/io/github/amayaframework/amaya-core/2.0.0)

An amaya framework module that implements a web application and its infrastructure.

## Getting Started

To install it, you will need:

* Java 11+
* Maven/Gradle
* Any amaya-server implementation (for example, [amaya-jetty](https://github.com/AmayaFramework/amaya-jetty))

## Installing

### Gradle dependency

```Groovy
dependencies {
   implementation group: 'io.github.amayaframework', name: 'amaya-core', version: '2.0.0'
}
```

### Maven dependency

```
<dependency>
    <groupId>io.github.amayaframework</groupId>
    <artifactId>amaya-core</artifactId>
    <version>2.0.0</version>
</dependency>
```

## Usage example

### Hello, world

<p>Gradle dependency section:</p>

```Gradle
dependencies {
    implementation group: 'io.github.amayaframework', name: 'amaya-core', version: '2.0.0'
    implementation group: 'io.github.amayaframework', name: 'amaya-jetty', version: '1.0.0'
}
```

<p>Java code:</p>

```Java
import io.github.amayaframework.core.WebBuilders;
import io.github.amayaframework.jetty.JettyServerFactory;

public class Main {
    public static void main(String[] args) throws Throwable {
        var builder = WebBuilders.create();
        var app = builder
                .setServerFactory(new JettyServerFactory())
                .build();
        app.bind(8080);
        app.run(ctx -> System.out.println("Hello, world"));
    }
}
```

## Built With

* [Gradle](https://gradle.org) - Dependency management
* [jfunc](https://github.com/RomanQed/jfunc) - Functional interfaces
* [jakarta.servlet](https://projects.eclipse.org/projects/ee4j.servlet) - Servlet API

## Authors

* [RomanQed](https://github.com/RomanQed) - *Main work*
* [max0000402](https://github.com/max0000402) - *Technical advices and ideas for features*

See also the list of [contributors](https://github.com/AmayaFramework/amaya-core/contributors) who participated
in this project.

## License

This project is licensed under the Apache License Version 2.0 - see the [LICENSE](LICENSE) file for details

## Acknowledgments

Thanks to everyone who was interested in this framework, gave advices and suggested ideas.
