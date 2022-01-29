# amaya-core-api [![maven-central](https://img.shields.io/maven-central/v/io.github.amayaframework/core-api?color=blue)](https://repo1.maven.org/maven2/io/github/amayaframework/core-api/)

The core of the Amaya framework is the one on which various implementations can be built.
It includes basic implementations of routes, filters, controllers, http transactions and everything related to them, 
method packaging, as well as assistive utilities.

## Getting Started

To install it, you will need:
* any build of the JDK no older than version 8
* [classindex](https://github.com/atteo/classindex)
* some implementation of slf4j
* Maven/Gradle

## Installing

### Gradle dependency

```Groovy
dependencies {
    implementation group: 'org.atteo.classindex', name: 'classindex', version: '3.4'
    annotationProcessor group: 'org.atteo.classindex', name: 'classindex', version: '3.4'
    implementation group: 'javax.servlet', name: 'javax.servlet-api', version: '4.0.1'
    implementation group: 'com.github.romanqed', name: 'jutils', version: '1.2.7'
    implementation group: 'io.github.amayaframework', name: 'filters', version: '1.0.7'
    implementation group: 'io.github.amayaframework', name: 'core-api', version: 'LATEST'
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
    <groupId>javax.servlet</groupId>
    <artifactId>javax.servlet-api</artifactId>
    <version>4.0.1</version>
</dependency>

<dependency>
    <groupId>com.github.romanqed</groupId>
    <artifactId>jutils</artifactId>
    <version>1.2.7</version>
</dependency>

<dependency>
    <groupId>io.github.amayaframework</groupId>
    <artifactId>filters</artifactId>
    <version>1.0.7</version>
</dependency>

<dependency>
    <groupId>io.github.amayaframework</groupId>
    <artifactId>core-api</artifactId>
    <version>LATEST</version>
</dependency>
```

## Usage example

## Pipeline concept

To process incoming http requests, the Amaya framework uses a simple and flexible concept of actions performed
sequentially on some data, called Pipeline.

This is a simplified diagram of what a typical pipeline data lifecycle looks like.

![Pipelines_1](https://github.com/amayaframework/amaya-core/raw/main/images/pipelines_1.png)

The core of the framework provides a basic set of actions for the pipeline, which allows
processing incoming requests in accordance with the declared functionality.

There are 6 actions in total, but for the first 3 there are 2 different variations: for the sun server and for servlets.
All their names are described in the Stage enum.
In this order they are executed:
<p>Input actions:</p>

* SunFindRouteAction (receives: SunRequestData, returns SunRequestData) /
  ServletFindRouteAction (receives: ServletRequestData, returns ServletRequestData)
* SunParseRequestAction (receives: SunRequestData, returns SunRequestData) /
  ServletParseRequestAction (receives: ServletRequestData, returns ServletRequestData)
* SunParseRequestCookiesAction (receives: SunRequestData, returns SunRequestData) /
  ParseRequestCookiesAction (receives: ServletRequestData, returns ServletRequestData)
* InvokeControllerAction (receives: returns RequestData, returns HttpResponse)

<p>Output actions:</p>

* CheckResponseAction (receives: PipelineResult, returns HttpResponse)
* ParseResponseCookiesAction (receives: HttpResponse, returns HttpResponse)

Also included in the standard delivery are 4 additional debugging actions that will be automatically
added when the appropriate configuration is enabled in the config.

<p>Input actions:</p>

* RouteDebugAction (receives: RequestData, returns RequestData)
* RequestDebugAction (receives: RequestData, returns RequestData)
* ResponseDebugAction (receives: HttpResponse, returns HttpResponse)

<p>Output actions:</p>

* InputResultDebugAction (receives: PipelineResult, returns PipelineResult)

Note: in order to avoid the need to create two different variations of pipeline actions when creating plugins,
SunRequestData and ServletRequestData containing data about the server implementation were inherited from
the universal RequestData.

Thanks to this separation, almost any necessary functionality can be added by simply inserting
the necessary actions between existing ones.

For the configuration of pipelines, and in particular, the interface containing them,
it is necessary to create an inherited class (or just a lambda) from the Configurator interface.

```Java
class MyConfigurator implements Configurator {

    @Override
    public void accept(IOHandler handler) {
        Pipeline input = handler.getInput();
        Pipeline output = handler.getOutput();
        // Do something
    }
}
```

The framework uses exactly the same mechanism for default pipeline configuration.

Then you just add your configurator to the builder.

```Java
public class Main {
    public static void main(String[] args) throws IOException {
        AmayaServer server = new AmayaBuilder().
                bind(8080).
                addConfigurator(new MyConfigurator()).
                build();
        server.start();
    }
}
```

Also, you can add a collection of configurators at once, which will overwrite the one set earlier,
or add configurators one by one - they will be executed in the order of addition.

## Filter concept

The idea of the filter is very simple - it is some handler that converts the received data set according
to internal rules and then returns them. In case of any discrepancy, an exception is thrown.
If there is no exception, filtering is considered successful.

In general, filters in the framework are used to convert path parameters and inject
the required values into method arguments.

### String filters
This kind of filters is applied to the path parameters and converts them from the raw string type to the required one.

For example, built-in integer filter:
```Java
@NamedFilter("int")
public class IntegerFilter implements StringFilter {
    @Override
    public Object transform(String source) {
        return Integer.parseInt(source);
    }
}
```

### Content filters
This kind of filters is applied to the data prepared for injection into the argument, trying to
extract data defined by some string from them.
For example, path filter, which will get the right one with a specific name from the entire map of path variables.

For example, built-in path filter:
```Java
@NamedFilter("path")
public class PathFilter implements ContentFilter {
    @Override
    @SuppressWarnings("unchecked")
    public Object transform(Object source, String name) {
        try {
            return ((Map<String, Object>) source).get(name);
        } catch (Exception e) {
            return null;
        }
    }
}
```

Note: If you want to create your own filters,
you need to connect the necessary [dependency](https://github.com/AmayaFramework/amaya-filters).

## Creating plugins
At the moment, taking into account all the above, we get an easy-to-modify and modular system.
And the plugin in general will be a set of pipeline filters and actions combined into a pipeline configurator.

Also, all the necessary information for this is contained in javadocs.

### "Filter" plugins
This type of plugins simply implies a set of filter classes packaged in a library.
All you need to create such a plugin is:

1) Fill in your build file correctly
<p>For gradle, it will be like:</p>

```Groovy
dependencies {
    implementation group: 'org.atteo.classindex', name: 'classindex', version: '3.4'
    annotationProcessor group: 'org.atteo.classindex', name: 'classindex', version: '3.4'
    implementation group: 'io.github.amayaframework', name: 'filters', version: 'LATEST'
}
```

2) Create the filters you want
<p>For example, this</p>

```Java
@NamedFilter("hello-filter")
public class IntegerFilter implements StringFilter {
    @Override
    public Object transform(String source) {
        if (source.startsWith("Hello")) {
            return "Hi there!";
        }
        return source;
    }
}
```
3) Build and publish your library to any convenient repository
4) Connect your library to an Amaya Framework-based project
5) Enjoy your beautiful filters!

### "Pipeline" plugins
This type of plug-ins may contain a set of pipeline actions that add some functionality.
All you need to create such a plugin is:
1) Connect the necessary dependencies
<p>For gradle: </p>

```Groovy
dependencies {
    implementation group: 'com.github.romanqed', name: 'jutils', version: 'LATEST'
    implementation group: 'io.github.amayaframework', name: 'http-server', version: 'LATEST'
    implementation group: 'io.github.amayaframework', name: 'core', version: 'LATEST'
}
```
2) Create a stage enum that lists all your actions and in the future will help other
   plugin authors easily find the names of your actions

```Java
public enum MyStage {
    MY_STAGE_1,
    MY_STAGE_2
}
```
3) Create the necessary pipeline actions
<p>For example, so:</p>

```Java
public class MyStage1Action extends PipelineAction<RequestData, RequestData> {
    
    @Override
    public RequestData apply(RequestData requestData) {
        HttpRequest request = requestData.getRequest();
        request.setBody(new MyBeautifulDataFormat(request.getBody()));
        return requestData;
    }
}
```

```Java
public class MyStage2Action extends PipelineAction<HttpResponse, HttpResponse> {

    @Override
    public HttpResponse apply(HttpResponse response) {
        response.setBody(((MyBeautifulDataFormat) response.getBody()).makeString());
        return response;
    }
}
```

4) Create a pipeline configurator that collects the fruits of your labors into a single whole
<p>Example code:</p>

```Java
public class MyPipelineConfigurator implements Configurator {
    @Override
    public void accept(IOHandler handler) {
        Pipeline input = handler.input();
        Pipeline output = handler.output();
        input.insertAfter(
                Stage.PARSE_REQUEST.name(),
                MyStage.MY_STAGE_1.name(),
                new MyStage1Action()
        );
        output.insertAfter(
                Stage.CHECK_RESPONSE.name(),
                MyStage.MY_STAGE_2.name(),
                new MyStage2Action()
        );
    }
}
```

3) Build and publish your library to any convenient repository
4) Connect your library to an Amaya Framework-based project
5) Add your configurator to the framework server

```Java
public class Server {
    public static void main(String[] args) throws IOException {
        AmayaServer server = new AmayaBuilder().
                addConfigurator(new MyPipelineConfigurator()).
                build();
        server.start();
    }
}
```

6) Enjoy your beautiful data format!

In the same way, you can change not only the body and any content included in the request and response,
but also change their types directly, creating your own classes inherited from the base ones.

### "Common" plugins
This type of plugins simply implies the presence of mixed content inside, which was described above.

## Configuring the framework
In addition to plugins, you also have the ability to configure the framework and the sun server via singleton configs.

### Amaya config
The Amaya config allows you to configure a fairly small number of parameters, but they all strongly affect the behavior
of the framework.

#### Route packer
(In enum: ROUTE_PACKER)
Specifies the wrapper that will be used to wrap the found methods inside the controller.
It affects the speed of method invocation and allows you to organize the injection of values into arguments.
Can be overridden by a custom class. Basic interface for it named Packer.

#### Router
(In enum: ROUTER)
Specifies the router class to be used in the controllers. Affects the speed of route search and includes
support for processing route parameters. Can be overridden by a custom class. Basic interface for it named Router.

#### Charset
(In enum: CHARSET)
Specifies the encoding that will be used when processing the request and response.

#### Backlog
(In enum: BACKLOG)
Specifies the value of the backlog parameter to be passed to the sun server.

#### Debug
(In enum: DEBUG)
Specifies whether debugging mode will be enabled

## Results

As a result, we get a fully customizable framework, similar to an easily shared constructor.
And I hope that all the necessary functionality that is missing at the moment will be gradually
implemented by the community in the form of open source plugin libraries. In addition, do not be
surprised by the absence of some familiar things like cookie storage: the framework was developed
with an eye to the ideas of the REST philosophy and hopes to remain in this form. Of course,
the author might have forgotten to invent or implement some things, so he is waiting for your issues.

## Built With

* [Gradle](https://gradle.org) - Dependency management
* [slf4j](https://www.slf4j.org) - Logging facade

## Authors
* **RomanQed** - *Main work* - [RomanQed](https://github.com/RomanQed)
* **max0000402** - *Technical advices and ideas for features* - [max0000402](https://github.com/max0000402)

See also the list of [contributors](https://github.com/AmayaFramework/amaya-core-api/contributors) who participated 
in this project.

## License

This project is licensed under the Apache License Version 2.0 - see the [LICENSE](LICENSE) file for details

## Acknowledgments

Thanks to everyone who was interested in this library, gave advice and suggested ideas.