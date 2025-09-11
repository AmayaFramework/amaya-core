<p align="center">
  <img src="../img/logo.png" alt="logo" style="width: 200px; height: auto;">
</p>

# amaya-core - ядро современного, легковесного и быстрого web-фреймворка

|      Модуль       |                                                                                                                                Версия                                                                                                                                 |
|:-----------------:|:---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------:|
|   amaya-options   |         [![amaya-options](https://img.shields.io/maven-central/v/io.github.amayaframework/amaya-options?strategy=releaseProperty&style=for-the-badge&label=amaya-options&color=blue)](https://repo1.maven.org/maven2/io/github/amayaframework/amaya-options/)         |
| amaya-environment | [![amaya-environment](https://img.shields.io/maven-central/v/io.github.amayaframework/amaya-environment?strategy=releaseProperty&style=for-the-badge&label=amaya-environment&color=blue)](https://repo1.maven.org/maven2/io/github/amayaframework/amaya-environment/) |
|   amaya-service   |         [![amaya-service](https://img.shields.io/maven-central/v/io.github.amayaframework/amaya-service?strategy=releaseProperty&style=for-the-badge&label=amaya-service&color=blue)](https://repo1.maven.org/maven2/io/github/amayaframework/amaya-service/)         |
| amaya-application | [![amaya-application](https://img.shields.io/maven-central/v/io.github.amayaframework/amaya-application?strategy=releaseProperty&style=for-the-badge&label=amaya-application&color=blue)](https://repo1.maven.org/maven2/io/github/amayaframework/amaya-application/) |
|    amaya-http     |               [![amaya-http](https://img.shields.io/maven-central/v/io.github.amayaframework/amaya-http?strategy=releaseProperty&style=for-the-badge&label=amaya-http&color=blue)](https://repo1.maven.org/maven2/io/github/amayaframework/amaya-http/)               |
|  amaya-tokenize   |       [![amaya-tokenize](https://img.shields.io/maven-central/v/io.github.amayaframework/amaya-tokenize?strategy=releaseProperty&style=for-the-badge&label=amaya-tokenize&color=blue)](https://repo1.maven.org/maven2/io/github/amayaframework/amaya-tokenize/)       |
|   amaya-context   |         [![amaya-context](https://img.shields.io/maven-central/v/io.github.amayaframework/amaya-context?strategy=releaseProperty&style=for-the-badge&label=amaya-context&color=blue)](https://repo1.maven.org/maven2/io/github/amayaframework/amaya-context/)         |
|   amaya-server    |           [![amaya-server](https://img.shields.io/maven-central/v/io.github.amayaframework/amaya-server?strategy=releaseProperty&style=for-the-badge&label=amaya-server&color=blue)](https://repo1.maven.org/maven2/io/github/amayaframework/amaya-server/)           |
|     amaya-web     |                 [![amaya-web](https://img.shields.io/maven-central/v/io.github.amayaframework/amaya-web?strategy=releaseProperty&style=for-the-badge&label=amaya-web&color=blue)](https://repo1.maven.org/maven2/io/github/amayaframework/amaya-web/)                 |
|    amaya-core     |               [![amaya-core](https://img.shields.io/maven-central/v/io.github.amayaframework/amaya-core?strategy=releaseProperty&style=for-the-badge&label=amaya-core&color=blue)](https://repo1.maven.org/maven2/io/github/amayaframework/amaya-core/)               |

- [English](../../README.md)
- Русский
- [简体中文](readme_zh_cn.md)
- [正體中文](readme_zh_tw.md)
- [日本語](readme_jp.md)
- [Deutsch](readme_de.md)
- [Français](readme_fr.md)

Amaya Core — основа Amaya Framework. Оно представляет собой модульное ядро, состоящее из отдельных логических подмодулей,
объединяемых в единую зависимость центральным модулем amaya-core. Ядро включает в себя следующий функционал:

* amaya-options — типизированные словари конфигурации вида "строковый ключ->значение";
* amaya-environment — виртуальная точка монтирования в файловой системе (на основе java.nio.file API);
* amaya-service — управляемые сервисы с полностью консистентным жизненным циклом на основе машины состояний;
* amaya-application — абстрактное приложение-сервис; объединяет все компоненты фреймворка и является единой точкой входа, 
после запуска отслеживает системные сигналы (через jvm shutdown hooks) и обеспечивает ожидаемую реакцию.
* amaya-http — набор сущностей, описывающих некоторые понятия из HTTP RFC (http version, http status code, http method, 
mime type)
* amaya-context — универсальный контекст запроса для веб-сервера (в т.ч. и http сервера); основан на [servlet api 6.0](https://jakarta.ee/specifications/servlet/6.0);
* amaya-server — абстрактный сервер-сервис, использующий amaya-context; объявляет базовое API конфигурации;
* amaya-web — абстрактное веб-приложение, расширяющее amaya-application, включает в себя amaya-server;
* amaya-di — опциональная интеграция с [amaya-di](https://github.com/AmayaFramework/amaya-di), включенная в amaya-core; 
при обнаружении зависимости подключается автоматически.

Реализация amaya-server также поставляется отдельно. Могут использоваться как готовые реализации 
([amaya-jetty](https://github.com/AmayaFramework/amaya-jetty), 
[amaya-tomcat](https://github.com/AmayaFramework/amaya-tomcat), 
[amaya-undertow](https://github.com/AmayaFramework/amaya-undertow)),
так и пользовательские.

# Введение

Для установки вам потребуется:

* JVM 11+
* Maven/Gradle

Любая версия JVM ниже 11 не поддерживается.

Возможно использование любых JVM-языков, основанных на JDK 11 (или выше).

## Установка

Для создания минимально возможного приложения необходимо установить два модуля: общий модуль ядра (amaya-core) и любую
реализацию amaya-server на ваш выбор. Для работы DI дополнительно требуются amaya-di и реализация stub-фабрики
(amaya-di-asm или amaya-di-reflect, более подробно см. 
[amaya-di README](https://github.com/AmayaFramework/amaya-di/blob/main/docs/readme/readme_ru.md#установка)).

### Gradle

```groovy
dependencies {
    implementation group: 'io.github.amayaframework', name: 'amaya-core', version: '3.6.1'
    // Любая реализация amaya-server
    implementation group: 'io.github.amayaframework', name: 'amaya-<server-name>', version: '<server-version>'
    // DI (опционально)
    implementation group: 'io.github.amayaframework', name: 'amaya-di', version: '3.1.0'
    // Реализация stub-фабрики (опционально)
    implementation group: 'io.github.amayaframework', name: 'amaya-di-<stub-impl-name>', version: '<stub-impl-version>'
}
```

### Maven

```xml
<dependencies>
    <dependency>
        <groupId>io.github.amayaframework</groupId>
        <artifactId>amaya-core</artifactId>
        <version>3.6.1</version>
    </dependency>
    <!--Любая реализация amaya-server-->
    <dependency>
        <groupId>io.github.amayaframework</groupId>
        <artifactId>amaya-[server-name]</artifactId>
        <version>[server-version]</version>
    </dependency>
    <!--DI (опционально)-->
    <dependency>
        <groupId>io.github.amayaframework</groupId>
        <artifactId>amaya-di</artifactId>
        <version>3.1.0</version>
    </dependency>
    <!--Реализация stub-фабрики (опционально)-->
    <dependency>
        <groupId>io.github.amayaframework</groupId>
        <artifactId>amaya-di-[stub-impl-name]</artifactId>
        <version>[stub-impl-version]</version>
    </dependency>
</dependencies>
```

## Hello, world!

Стандартным способом собрать приложение является получение инстанса `WebApplicationBuilder`, конфигурация компонентов
приложения и получение готового инстанса `WebApplication`. Далее производится настройка уже самого приложения и его
запуск (`app.run()`). После вызова `run` приложение производит запуск управляемых сервисов (сервер, менеджер сервисов)
и начинает отслеживать системные сигналы.

Для примера соберем приложение на Java 17 и amaya-jetty, без di. Установим зависимости:

```groovy
dependencies {
    implementation group: 'io.github.amayaframework', name: 'amaya-core', version: '3.6.0'
    implementation group: 'io.github.amayaframework', name: 'amaya-jetty', version: '3.3.1-12.0.26'
}
```

Если используется JPMS, добавим зависимости в модуль:

```java
open module amayaframework.examples {
    requires amayaframework.core;
    requires amayaframework.jetty;
    exports io.github.amayaframework.examples;
}
```

И соберем простейшее приложение:

```java
package io.github.amayaframework.examples;

import io.github.amayaframework.core.WebBuilders;
import io.github.amayaframework.jetty.JettyServerFactory;

public final class SimpleHelloWorld {
    public static void main(String[] args) throws Throwable {
        var app = WebBuilders.create()
                .withServerFactory(new JettyServerFactory())
                .build();
        app.configurer().add((ctx, next) -> {
            ctx.response().writer().println("Hello from amaya");
        });
        app.bind(8080);
        app.run();
    }
}
```

Теперь после запуска можно отправить запрос и увидеть ответ:

```
>curl -v http://localhost:8080
* Host localhost:8080 was resolved.
* IPv6: ::1
* IPv4: 127.0.0.1
*   Trying [::1]:8080...
* Connected to localhost (::1) port 8080
* using HTTP/1.x
> GET / HTTP/1.1
> Host: localhost:8080
> User-Agent: curl/8.13.0
> Accept: */*
>
< HTTP/1.1 200 OK
< Date: ???, ?? ??? 2025 XX:YY:ZZ GMT
< Content-Length: 18
<
Hello from amaya
```

Для демонстрации основного принципа конфигурации в фреймворке включим отправку заголовков `Server` и `X-Powered-By`:

```java
package io.github.amayaframework.examples;

import io.github.amayaframework.core.WebBuilders;
import io.github.amayaframework.jetty.JettyServerFactory;
import io.github.amayaframework.options.Options;
import io.github.amayaframework.server.ServerOptions;
import io.github.amayaframework.web.WebOptions;

public final class SimpleHelloWorld {
    public static void main(String[] args) throws Throwable {
        var opts = Options.createGrouped();
        var serverOpts = opts.ensureGroup(WebOptions.SERVER_GROUP);
        serverOpts.set(ServerOptions.SEND_SERVER, true);
        serverOpts.set(ServerOptions.SEND_POWERED_BY, true);
        var app = WebBuilders.create(opts)
                .withServerFactory(new JettyServerFactory())
                .build();
        app.configurer().add((ctx, next) -> {
            ctx.response().writer().println("Hello from amaya");
        });
        app.bind(8080);
        app.run();
    }
}
```

И отправим запрос:

```
>curl -v http://localhost:8080
* Host localhost:8080 was resolved.
* IPv6: ::1
* IPv4: 127.0.0.1
*   Trying [::1]:8080...
* Connected to localhost (::1) port 8080
* using HTTP/1.x
> GET / HTTP/1.1
> Host: localhost:8080
> User-Agent: curl/8.13.0
> Accept: */*
>
* Request completely sent off
< HTTP/1.1 200 OK
< Server: Jetty(12.0.26)
< X-Powered-By: Jetty(12.0.26)
< Date: ???, ?? ??? 2025 XX:YY:ZZ GMT
< Content-Length: 18
<
Hello from amaya
```

