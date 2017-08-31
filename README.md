# Spring Specification

[![Libraries.io for GitHub](https://img.shields.io/librariesio/github/pinguet62/spring-specification.svg)](https://libraries.io/github/pinguet62/spring-specification)
[![Known Vulnerabilities](https://snyk.io/test/github/pinguet62/spring-specification/badge.svg)](https://snyk.io/test/github/pinguet62/spring-specification)
[![Codacy Badge](https://api.codacy.com/project/badge/Grade/7aa6eb05ba514f7aa13b86a6f4d567b9)](https://www.codacy.com/app/pinguet62/spring-specification?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=pinguet62/spring-specification&amp;utm_campaign=Badge_Grade)
[![Build Status](https://travis-ci.org/pinguet62/spring-specification.svg?branch=master)](https://travis-ci.org/pinguet62/spring-specification)
[![codecov.io](https://codecov.io/github/pinguet62/spring-specification/coverage.svg?branch=master)](https://codecov.io/github/pinguet62/spring-specification?branch=master)
[![Coverage Status](https://coveralls.io/repos/github/pinguet62/spring-specification/badge.svg?branch=master)](https://coveralls.io/github/pinguet62/spring-specification?branch=master)
[![Maven Central](https://img.shields.io/maven-central/v/fr.pinguet62.xjc/spring-specification.svg)](https://maven-badges.herokuapp.com/maven-central/fr.pinguet62.xjc/spring-specification)

This project provides:
* **specification pattern** basic API
* **Spring** support, to be integrated simply into any Spring application
* **database storage** and corresponding builder, for dynamic build
* **admin interface** for dynamic update of rules

## Sample

```java
@AutowiredRuleBuilder builder;

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
