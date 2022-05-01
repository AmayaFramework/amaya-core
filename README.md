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
### A quick overview
The core of the framework is its mandatory part, the main one, the functionality of which 
will somehow be used by the rest of the parts.

The core provides ample opportunities for customization of the framework functionality:
* implementation of user code in the process of interaction between the controller and the network code
* creating custom filters for path parameters
* creation of additional annotations for injecting values into the arguments of the route method
* creating custom packers for controller methods
* creating a custom router (a mechanism that finds the necessary method according to the received path)
* creating custom classes that implement standard HttpRequest and HttpResponse
  
<h3>Important</h3>
<p>The kernel does NOT contain implementations for any particular http-server/java-servlets 
to increase the versatility and customization of the framework.</p>

### Pipelines
To communicate the controller with the http server, the framework uses a mechanism called "pipeline".
In fact, it is a changeable chain of named functions that are executed sequentially/asynchronously.
Thus, you can create your own actions for their subsequent implementation.

<p>Custom action</p>

```Java
public class MyAction extends PipelineAction<RequestData, RequestData> {

    @Override
    public RequestData execute(RequestData data) {
        // Do something
        return data;
    }
}
```

<p>Inserting</p>

```Java
public class MyConfigurator implements Configurator {

    @Override
    public void configureController(Controller controller) {
        // Do something
    }

    @Override
    public void configureInput(NamedPipeline input) {
        input.put(new MyAction());
    }

    @Override
    public void configureOutput(NamedPipeline output) {
        // Do something
    }
}
```

### Filters for path parameters
The route may contain any parameters (for example, "/a/{b:int}")
To check whether the sent parameter matches the expected type (b must be of type int), filters (filter) are used,
which in this case
a) transform b from the string representation into int,
b) in case of inconsistency, report this to the framework by means of an exception

You can very easily create your own filter in this way:

```Java
@NamedFilter("int")
public class IntegerFilter implements Filter {
    @Override
    public Object transform(String source) {
        return Integer.parseInt(source);
    }
}
```

### Annotations for injecting route method arguments
The injection of values into method arguments in the framework is implemented by redirecting the return value of 
the HttpRequest getter method.
Thus, any inject annotation is, in fact, a repository of arguments that should be passed to the getter.
For example, in the case of @Path("param") (annotation to get the path parameter), 
the method will be called HttpRequest::getpathparameters with the argument "param", 
and the value it returns will be in the annotated argument.

By default, there are 5 such annotations (Header, Http Cookie, Path, Query, Body) and custom ones can be added.
To do this, you will need to create your own annotation (if it has parameters, explicitly specify their position) 
and link it to the getter in your HttpRequest implementation.

<p>Implementation</p>

<p>Custom request</p>

```Java
@SourceRequest(MyReq.class)
public class MyReq extends CommonHttpRequest {

    public MyReq(HttpRequest body) {
        super(body);
    }

    @Provider(MyAnnot.class)
    public String getSome(String a, int b) {
        return a + b;
    }
}
```

<p>Custom annotation</p>

```Java
@Retention(RetentionPolicy.RUNTIME)
public @interface MyAnnot {
    @Position
    String a();
    @Position(1)
    int b();
}
```

### Controller Method Packers
In general, the task of the packager is to turn a pair from an instance of your controller and a method (from java-reflect) into a ready-to-call
Action<HttpRequest, HttpResponse>.

<p>Example of dummy implementation using java reflection</p>

```Java
public static class MyPacker implements Packer {

    @Override
    public Action<HttpRequest, HttpResponse> pack(Object instance, Method method) {
        return request -> (HttpResponse) method.invoke(instance, request);
    }
}
```

### Routers
The router processes the path that came from the user and directs it to the appropriate route.
For example, "/index/5" will be routed to "/index/{i:int}".
Although the kernel provides the necessary minimum of routers by default, if you lack their functionality,
you can implement your own.

<p>Example of a plain router embedded in the core</p>

```Java
public class BaseRouter extends MethodRouter {
    @Override
    public MethodRoute follow(HttpMethod method, String route) {
        Map<String, MethodRoute> methodRoutes = routes.get(method);
        if (methodRoutes == null) {
            return null;
        }
        return methodRoutes.get(route);
    }
}
```

### Custom HttpRequest and HttpResponse
If you need to create custom entities that implement a http transaction, 
you just need to inherit from ready-made existing classes.

<p>Custom http request</p>

```Java
public class MyReq extends CommonHttpRequest {

    public MyReq(HttpRequest body) {
        super(body);
    }

    // Some code
}
```

<p>Custom http response</p>

```Java
public class MyResp extends CommonHttpResponse {
    // Some code
}
```

## Built With

* [Gradle](https://gradle.org) - Dependency management
* [classindex](https://github.com/atteo/classindex) - Annotation scanning
* [jeflect](https://github.com/RomanQed/jeflect) - Method wrapping
* [slf4j](https://www.slf4j.org) - Logging facade
* [javax.servet](https://docs.oracle.com/javaee/7/api/javax/servlet/Servlet.html) - Servlets
* [http-utils](https://github.com/AmayaFramework/http-utils) - Some http stuff
* [java-utils](https://github.com/RomanQed/java-utils) - Pipelines and other stuff

## Authors
* **RomanQed** - *Main work* - [RomanQed](https://github.com/RomanQed)
* **max0000402** - *Technical advices and ideas for features* - [max0000402](https://github.com/max0000402)

See also the list of [contributors](https://github.com/AmayaFramework/amaya-core-api/contributors) who participated 
in this project.

## License

This project is licensed under the Apache License Version 2.0 - see the [LICENSE](LICENSE) file for details

## Acknowledgments

Thanks to everyone who was interested in this library, gave advice and suggested ideas.