# Spring Specification

## REST API server (webservice)

TODO Swagger

## Deploy

### Spring Boot

With `@Import(SpringSpecificationServerApplication.class)` import.

```java
@SpringBootApplication
@Import(SpringSpecificationServerApplication.class)
public class SampleApplication {
    public static void main(String[] args) {
        SpringApplication.run(SampleApplication.class, args);
    }
}
```

### Node.js

TODO