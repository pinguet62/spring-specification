# Spring Specification

## API

### Usage

Example for this composition:
```
(NOT first) AND (second OR third)
```
with these 3 *basic rules*:
```java
Rule first = ...
Rule second = ...
Rule third = ...
```

#### Composite pattern

See `AndRule`/`OrRule`/`NotRule` special `Rule` implementations.

```java
Rule composedRule = new AndRule(
                        new NotRule(first),
                        new OrRule(
                            second,
                            third
                        )
                    );
```

#### Method chaining

See `Rule` *default* methods.

```java
Rule composedRule = first.not().and( second.or(third) );
```

#### Utility methods

See `RuleUtils` *static* methods.

```java
Rule composedRule = first.not().and( second.or(third) );
```

### Parameter

Objective: create minimal parameterized rules, instead of create many specific rules.

Bad:
```java
class RedProductRule {}
class BlueProductRule {}
class GreenProductRule {}
```

Good:
```java
class ColorProductRule implements Rule {
    String color;
    
    ColorProductRule(Map<String, Object> parameters) {
        this.color = parameters.get("color");
    }
    
    // ...
}

Rule redProductRule = new ColorProductRule( Map.of("color","red") );
Rule bleuProductRule = new ColorProductRule( Map.of("color","bleu") );
Rule greenProductRule = new ColorProductRule( Map.of("color","green") );
```

TODO: injection (by setter or annotation) instead of `Map`

### Context

The `Context` correspondsto the *context execution* of rules, with processed data.

```java
Context context = new ContextImpl(
        ofEntries(
            entry("user", new User()),
            entry("product", new Product())
        )
);
```
```java
class SampleRule implements Rule {
    @Override
    public boolean test(Context context) {
        User user = context.get("user", User.class);
        Product product = context.get("product", Product.class);
    }
}
```
```java
Rule rule = SampleRule();
rule.test(context);
```