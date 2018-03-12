# Spring Specification

![Maintained](https://img.shields.io/badge/maintained%3F-yes-brightgreen.svg?style=flat)
[![Libraries.io for GitHub](https://img.shields.io/librariesio/github/pinguet62/spring-specification.svg)](https://libraries.io/github/pinguet62/spring-specification)
[![Known Vulnerabilities](https://snyk.io/test/github/pinguet62/spring-specification/badge.svg)](https://snyk.io/test/github/pinguet62/spring-specification)
[![Codacy Badge](https://api.codacy.com/project/badge/Grade/7aa6eb05ba514f7aa13b86a6f4d567b9)](https://www.codacy.com/app/pinguet62/spring-specification?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=pinguet62/spring-specification&amp;utm_campaign=Badge_Grade)
[![Build Status](https://travis-ci.org/pinguet62/spring-specification.svg?branch=master)](https://travis-ci.org/pinguet62/spring-specification)
[![codecov.io](https://codecov.io/github/pinguet62/spring-specification/coverage.svg?branch=master)](https://codecov.io/github/pinguet62/spring-specification?branch=master)
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/fr.pinguet62/spring-specification/badge.svg)](https://maven-badges.herokuapp.com/maven-central/fr.pinguet62/spring-specification)
[![Javadocs](https://www.javadoc.io/badge/fr.pinguet62/spring-specification.svg)](https://www.javadoc.io/doc/fr.pinguet62/spring-specification)
[![Swagger](https://img.shields.io/swagger/valid/2.0/https/raw.githubusercontent.com/OAI/OpenAPI-Specification/master/examples/v2.0/json/petstore-expanded.json.svg)](https://spring-specification-sample.herokuapp.com/swagger-ui.html)

This project provides:
* **specification pattern** basic API
* **Spring** support, to be integrated simply into any Spring application
* **database storage** and corresponding builder, for dynamic build
* **admin interface** for dynamic update of rules

## Sample

```java
@Autowired
RuleBuilder builder;

Product product = ...
Rule<Product> rule = (Rule<Product>) builder.apply("CanSellToMinor");
boolean result = rule.test(product);
```

## Admin console

* create/delete **business rules**
* manage **tree** of rules
* manage rule **parameters**

![](./spring-specification-doc/src/main/asciidoc/img/screenshot/rules.png?raw=true)

![](./spring-specification-doc/src/main/asciidoc/img/screenshot/details.png?raw=true)

![](./spring-specification-doc/src/main/asciidoc/img/screenshot/rule_edit.png?raw=true)

![](./spring-specification-doc/src/main/asciidoc/img/screenshot/parameters_edit.png?raw=true)

## Try sample!

* Admin UI: https://spring-specification-sample.herokuapp.com/spring-specification-admin-client/
* Client: https://spring-specification-sample.herokuapp.com/sample.html
