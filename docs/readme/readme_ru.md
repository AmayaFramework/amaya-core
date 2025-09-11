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

