# Tomcat Slowness in Spring Boot

This is a simple test project, it demonstrates the slowness of the specific class loader provided by the embedded tomcat in spring-boot.

The problem is effective only when packaging a spring-boot application in a **WAR**.

* The problematic class loader is :
```java
org.springframework.boot.web.embedded.tomcat.TomcatEmbeddedWebappClassLoader
```

* It can be obtained in a Rest Controller by invoking the function:
```java
Thread.currentThread().getContextClassLoader().
```

The slowness appears while trying to load with ``Class.forName()`` a class _that does not exist_.
