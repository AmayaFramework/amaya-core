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

* amaya-options — типизированные словари конфигурации вида "строковый типизированный ключ->значение";
* amaya-environment — виртуальная точка монтирования в файловой системе (на основе java.nio.file API);
* amaya-service — управляемые сервисы с полностью консистентным жизненным циклом на основе машины состояний;
* amaya-application — абстрактное приложение-сервис; объединяет все компоненты фреймворка и является единой точкой входа, 
после запуска отслеживает системные сигналы (через jvm shutdown hooks) и обеспечивает ожидаемую реакцию;
* amaya-http — набор сущностей, описывающих некоторые понятия из HTTP RFC (http version, http status code, http method, 
mime type);
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
    implementation group: 'io.github.amayaframework', name: 'amaya-core', version: '3.7.0'
    // Любая реализация amaya-server
    implementation group: 'io.github.amayaframework', name: 'amaya-<server-name>', version: '<server-version>'
    // DI (опционально)
    implementation group: 'io.github.amayaframework', name: 'amaya-di', version: '3.2.0'
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
        <version>3.7.0</version>
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
        <version>3.2.0</version>
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
    implementation group: 'io.github.amayaframework', name: 'amaya-core', version: '3.7.0'
    implementation group: 'io.github.amayaframework', name: 'amaya-jetty', version: '3.3.3-12.1.1'
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
        app.bind(8080);
        app.run(ctx -> {
            ctx.response().writer().println("Hello from amaya");
        });
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

Для демонстрации конфигурации через именованные параметры включим отправку заголовков `Server` и `X-Powered-By`:

```java
package io.github.amayaframework.examples;

import io.github.amayaframework.core.WebBuilders;
import io.github.amayaframework.jetty.JettyServerFactory;
import io.github.amayaframework.options.Options;
import io.github.amayaframework.server.ServerOptions;
import io.github.amayaframework.web.WebOptions;

public final class SimpleHelloWorld {
    public static void main(String[] args) throws Throwable {
        var app = WebBuilders.create()
                .configureOptions(opts -> {
                    var serverOpts = opts.ensureGroup(WebOptions.SERVER_GROUP);
                    serverOpts.set(ServerOptions.SEND_SERVER, true);
                    serverOpts.set(ServerOptions.SEND_POWERED_BY, true);
                })
                .withServerFactory(new JettyServerFactory())
                .build();
        app.bind(8080);
        app.run(ctx -> {
            ctx.response().writer().println("Hello from amaya");
        });
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

## Hello, services and multibinding!

Рассмотрим более комплексный пример. Пусть наше приложение:

1. слушает порт 8081 с протоколом HTTP/1.1;
2. слушает порт 8082 с протоколом HTTP/2 (h2c);
3. на запрос GET /echo?msg отвечает значением параметра `msg`;
4. на запрос GET /random отвечает псевдослучайным числом;
5. на запрос GET /count возвращает текущий счетчик запросов /count;
6. на любой другой запрос отвечает 404.

Для работы с http 2 подключим доп. модуль jetty в build.gradle: 
```groovy
implementation group: 'org.eclipse.jetty.http2', name: 'jetty-http2-server', version: '12.1.1'
```

Соберем приложение и укажем в конфиге HTTP/2 как целевую версию протокола сервера. Настроим бинды на нужные порты,
причем для 8081 укажем HTTP/1.1:

```
var app = WebBuilders.create()
                .withServerFactory(new JettyServerFactory())
                .build();
        app.serverConfig().httpVersion(HttpVersion.HTTP_2_0);
        app.bind(8081, HttpVersion.HTTP_1_1);
        app.bind(8082);
```

Теперь для п. 5 реализуем сервис-счетчик:

```java
static final class CounterService extends AbstractService {
    private AtomicInteger counter;
    
    public int count() {
        return counter.getAndIncrement();
    }   

    @Override
    protected void doStart(CancelToken token, ServiceCallback callback) {
        if (counter == null) {
            counter = new AtomicInteger();
        }
    }

    @Override
    protected void doStop(CancelToken token) {
        if (counter != null) {
            counter.set(0);
        }
    }

    @Override
    protected void doDispose() {
        counter = null;
    }
}
```

Создадим его и зарегистрируем в менеджере приложения:

```
var counter = new CounterService();
app.manager().add(counter);
```

Реализуем с помощью middleware-пайплайна примитивный роутинг:

```
static Predicate<HttpContext> get(String path) {
    return ctx -> {
        var req = ctx.request();
        return req.method() == HttpMethod.GET && path.equals(req.path());
    };
}
...
app.configurer()
        .mapWhen(get("/echo"), (HttpContext ctx) -> {
            // TODO echo
        })
        .mapWhen(get("/random"), (HttpContext ctx) -> {
            // TODO random
        })
        .mapWhen(get("/count"), (HttpContext ctx) -> {
            // TODO count
        })
        .add((ctx, next) -> {
            ctx.response().sendError(HttpCode.NOT_FOUND);
        });
```

И подготовим каждый эндпоинт согласно нашему ТЗ:

```
.mapWhen(get("/echo"), (HttpContext ctx) -> {
    ctx.response().writer().println(ctx.request().<String>queryParam("msg"));
})
.mapWhen(get("/random"), (HttpContext ctx) -> {
    ctx.response().writer().println(ThreadLocalRandom.current().nextInt());
})
.mapWhen(get("/count"), (HttpContext ctx) -> {
    ctx.response().writer().println(counter.count());
})
```

В результате получаем такой код:

```java
package io.github.amayaframework.examples;

import com.github.romanqed.jct.CancelToken;
import io.github.amayaframework.context.HttpContext;
import io.github.amayaframework.core.WebBuilders;
import io.github.amayaframework.http.HttpCode;
import io.github.amayaframework.http.HttpMethod;
import io.github.amayaframework.http.HttpVersion;
import io.github.amayaframework.jetty.JettyServerFactory;
import io.github.amayaframework.service.AbstractService;
import io.github.amayaframework.service.ServiceCallback;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;

public final class ComplexHelloWorld {
    public static void main(String[] args) throws Throwable {
        var app = WebBuilders.create()
                .withServerFactory(new JettyServerFactory())
                .build();
        app.serverConfig().httpVersion(HttpVersion.HTTP_2_0);
        app.bind(8081, HttpVersion.HTTP_1_1);
        app.bind(8082);
        var counter = new CounterService();
        app.manager().add(counter);
        app.configurer()
                .mapWhen(get("/echo"), (HttpContext ctx) -> {
                    ctx.response().writer().println(ctx.request().<String>queryParam("msg"));
                })
                .mapWhen(get("/random"), (HttpContext ctx) -> {
                    ctx.response().writer().println(ThreadLocalRandom.current().nextInt());
                })
                .mapWhen(get("/count"), (HttpContext ctx) -> {
                    ctx.response().writer().println(counter.count());
                })
                .add((ctx, next) -> {
                    ctx.response().sendError(HttpCode.NOT_FOUND);
                });
        app.run();
    }

    static Predicate<HttpContext> get(String path) {
        return ctx -> {
            var req = ctx.request();
            return req.method() == HttpMethod.GET && path.equals(req.path());
        };
    }

    static final class CounterService extends AbstractService {
        private AtomicInteger counter;

        public int count() {
            return counter.getAndIncrement();
        }

        @Override
        protected void doStart(CancelToken token, ServiceCallback callback) {
            if (counter == null) {
                counter = new AtomicInteger();
            }
        }

        @Override
        protected void doStop(CancelToken token) {
            if (counter != null) {
                counter.set(0);
            }
        }

        @Override
        protected void doDispose() {
            counter = null;
        }
    }
}
```

Запустим его и протестируем порт 8081 (HTTP/1.1):

```
>curl localhost:8081/echo?msg=hello
hello

>curl localhost:8081/random
1287675387

>curl localhost:8081/count
0

>curl localhost:8081/count
1

>curl localhost:8081/somepath
<html>
<head>
<meta http-equiv="Content-Type" content="text/html;charset=ISO-8859-1"/>
<title>Error 404 Not Found</title>
</head>
<body>
<h2>HTTP ERROR 404 Not Found</h2>
<table>
<tr><th>URI:</th><td>http://localhost:8081/somepath</td></tr>
<tr><th>STATUS:</th><td>404</td></tr>
<tr><th>MESSAGE:</th><td>Not Found</td></tr>
</table>

</body>
</html>

>curl localhost:8081/
<html>
<head>
<meta http-equiv="Content-Type" content="text/html;charset=ISO-8859-1"/>
<title>Error 404 Not Found</title>
</head>
<body>
<h2>HTTP ERROR 404 Not Found</h2>
<table>
<tr><th>URI:</th><td>http://localhost:8081/</td></tr>
<tr><th>STATUS:</th><td>404</td></tr>
<tr><th>MESSAGE:</th><td>Not Found</td></tr>
</table>

</body>
</html>
```

Проверим порт 8082 (HTTP/2). Он может принимать как HTTP/1.1 запросы, так и HTTP/2 (upgrade или prior):

```
>curl localhost:8082/echo?msg=hello
hello

>curl --http2 --http2-prior-knowledge localhost:8082/echo?msg=hello
hello

>curl --http2 localhost:8082/echo?msg=hello
hello
```

Эмулируем "падение" сервиса-счетчика после достижения 5+ вызовов count:

```java
static final class CounterService extends AbstractService {
    private AtomicInteger counter;
    private ServiceCallback callback;

    public int count() {
        var ret = counter.getAndIncrement();
        if (ret >= 5 && callback != null) {
            callback.fail(new IllegalStateException("Count >= 5"));
        }
        return ret;
    }

    @Override
    protected void doStart(CancelToken token, ServiceCallback callback) {
        if (counter == null) {
            counter = new AtomicInteger();
        }
        this.callback = callback;
    }

    @Override
    protected void doStop(CancelToken token) {
        if (counter != null) {
            counter.set(0);
        }
        callback = null;
    }

    @Override
    protected void doDispose() {
        counter = null;
        callback = null;
    }
}
```

В результате получим это:

```
>curl localhost:8081/count
0

>curl localhost:8081/count
1

>curl localhost:8081/count
2

>curl localhost:8081/count
3

>curl localhost:8081/count
4

>curl localhost:8081/count
curl: (52) Empty reply from server

>curl localhost:8081/count
curl: (7) Failed to connect to localhost port 8081 after 1337 ms: Could not connect to server
```

## Hello, DI!

Повторим все то же самое, только теперь добавим DI. Для начала для наглядности разделим count-интерфейс и count-service,
а также сделаем CounterService public для того, чтобы встроенный генератор схем инжекта его увидел:

```java
interface Counter {
    int count();
}

public static final class CounterService extends AbstractService implements Counter {
    private AtomicInteger counter;

    public int count() {
        return counter.getAndIncrement();
    }

    @Override
    protected void doStart(CancelToken token, ServiceCallback callback) {
        if (counter == null) {
            counter = new AtomicInteger();
        }
    }

    @Override
    protected void doStop(CancelToken token) {
        if (counter != null) {
            counter.set(0);
        }
    }

    @Override
    protected void doDispose() {
        counter = null;
    }
}
```

Подключим зависимости для работы DI:

```groovy
implementation group: 'io.github.amayaframework', name: 'amaya-di', version: '3.2.0'
implementation group: 'io.github.amayaframework', name: 'amaya-di-reflect', version: '2.1.0'
```

```java
open module amayaframework.examples {
    ...
    requires amayaframework.di;
    ...
}
```

И зарегистрируем сервис через билдер:

```
public static void main(String[] args) throws Throwable {
    var app = WebBuilders.create()
            .configureServices(cfg -> cfg.register(Counter.class, CounterService.class))
            .withServerFactory(new JettyServerFactory())
            .build();
    app.bind(8081);
    var counter = app.provider().get(Counter.class);
    app.configurer()
            .mapWhen(get("/count"), (HttpContext ctx) -> {
                ctx.response().writer().println(counter.count());
            })
            .add((ctx, next) -> {
                ctx.response().sendError(HttpCode.NOT_FOUND);
            });
    app.run();
}
```

Теперь после запуска приложения можем убедиться в работе эндпоинта:

```
>curl localhost:8081/count
0

>curl localhost:8081/count
1

>curl localhost:8081/count
2
```

## Hello, async world!

Аналогично можно собрать асинхронный пайплайн, использующий CompletableFuture. Терминальное действие будет обработано
сервером и вызвано внутри моста к servlet async api. Покажем это на примере простого "hello, world" с обработкой
ошибок.

```java
package io.github.amayaframework.examples;

import com.github.romanqed.jfunc.Exceptions;
import com.github.romanqed.jsync.Futures;
import io.github.amayaframework.core.WebBuilders;
import io.github.amayaframework.jetty.JettyServerFactory;

import java.io.IOException;
import java.util.concurrent.ThreadLocalRandom;

public final class AsyncHelloWorld {
    public static void main(String[] args) throws Throwable {
        var app = WebBuilders.create()
                .withServerFactory(new JettyServerFactory())
                .build();
        app.bind(8080);
        app.configurer()
                .add((ctx, next) -> {
                    return next.runAsync(ctx).exceptionally(t -> {
                        if (t != null) {
                            try {
                                ctx.response().writer().println("Sorry, some problems");
                            } catch (IOException e) {
                                System.out.println("Rlly big problems");
                                Exceptions.throwAny(e);
                            }
                        }
                        return null;
                    });
                })
                .add((ctx, next) -> {
                    return Futures.run(() -> {
                        if (ThreadLocalRandom.current().nextBoolean()) {
                            throw new IllegalStateException("Unlucky");
                        }
                        ctx.response().writer().println("Hello, world!");
                    });
                });
        app.run();
    }
}
```

Проверим результат:

```
>curl localhost:8080
Hello, world!

>curl localhost:8080
Sorry, some problems

>curl localhost:8080
Sorry, some problems

>curl localhost:8080
Hello, world!
```

**ВАЖНОЕ ЗАМЕЧАНИЕ #1!** Таски внутри пайплайна могут быть чисто синхронными, чисто асинхронными, универсальными или 
"грязными". Пока сервер может однозначно определить модель исполнения, он всегда будет выбирать наиболее оптимальный
вариант для типа таска и вашего окружения (зависит от реализации). Однако как только серверу попадается 
"грязный" пайплайн, он откажется от попыток выбора и просто использует стандартную модель выполнения 
(sync для jvm с Project Loom, async для остальных). 

Наглядно продемонстрировать работу схемы выбора можно на следующем примере:

```java
package io.github.amayaframework.examples;

import com.github.romanqed.jconv.Task;
import com.github.romanqed.jsync.Futures;
import io.github.amayaframework.context.HttpContext;
import io.github.amayaframework.core.WebBuilders;
import io.github.amayaframework.jetty.JettyServerFactory;

import java.util.concurrent.CompletableFuture;

public final class UniHelloWorld {
    public static void main(String[] args) throws Throwable {
        var app = WebBuilders.create()
                .withServerFactory(new JettyServerFactory())
                .build();
        app.bind(8080);
        app.run(new UniHelloTask());
    }

    static final class UniHelloTask implements Task<HttpContext> {

        @Override
        public void run(HttpContext ctx) throws Throwable {
            ctx.response().writer().println("Hello from sync, JVM = " + Runtime.version());
        }

        @Override
        public CompletableFuture<Void> runAsync(HttpContext ctx) {
            return Futures.run(() -> ctx.response().writer().println("Hello from async, JVM = " + Runtime.version()));
        }

        @Override
        public boolean isSync() {
            return true;
        }

        @Override
        public boolean isAsync() {
            return true;
        }

        @Override
        public boolean isUni() {
            return true;
        }
    }
}
```

Теперь, если запустить этот код без каких-либо изменений, ответы будут следующими:

1. На jvm <= 19 (или без project loom): 
```
>curl localhost:8080
Hello from async, JVM = 17.0.16+8-LTS
```
2. На jvm >= 19:
```
>curl localhost:8080
Hello from sync, JVM = 21.0.7+6-LTS
```

При этом сервер автоматически переходит на использование виртуальных потоков при их доступности.

Для чистых sync/async тасков планирование не выполняется: они всегда будут выполняться как есть (из тех соображений,
что обернуть sync в CompletableFuture или join'ить async дороже, чем выполнять таск нативно). Планирование же для 
uni/mixed-тасков можно явно контролировать с помощью флага PREFER_ASYNC:

```
.configureOptions(opts -> {
    opts.ensureGroup(WebOptions.SERVER_GROUP).set(ServerOptions.PREFER_ASYNC, true);
})
```

**ВАЖНОЕ ЗАМЕЧАНИЕ #2!** При реализации модулей-расширений хорошим тоном является реализация своих тасков в uni-версии.
Модули, поставляемые экосистемой фреймворка, строго следуют этой практике.

# Строение ядра

TODO

## Политика управления зависимостями

TODO

## Все есть сервис

TODO

## Именованные параметры

TODO

## Виртуальное окружение

TODO

## Понятие приложения

TODO

## HTTP и все что с ним связано

TODO

## Веб-приложение

TODO

# Создано с помощью

* [Gradle](https://gradle.org) — Управление зависимостями
* [jfunc](https://github.com/RomanQed/jfunc) — Функциональные интерфейсы, утилиты
* [jtype](https://github.com/RomanQed/jtype) — Утилиты для работы с дженериками
* [jct](https://github.com/RomanQed/jct) — Токены для кооперативной отмены
* [jakarta.servlet](https://projects.eclipse.org/projects/ee4j.servlet/releases/6.0) — Servlet API

# Авторы

* [RomanQed](https://github.com/RomanQed) — *Основная работа*
* [max0000402](https://github.com/max0000402) — *Технические советы и идеи для фич*
* [N1ckBaran0v](https://github.com/N1ckBaran0v) — *Технические советы и идеи для фич*
* [1qwaka](https://github.com/1qwaka) — *Технические советы и идеи для фич*

Загляните также в список [участников](https://github.com/AmayaFramework/amaya-core/contributors), которые внесли вклад
в этот проект.

# Лицензия

Этот проект лицензирован под Apache License Version 2.0 - см. [LICENSE](../../LICENSE) файл для подробностей.
