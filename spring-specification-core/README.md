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

#### Objective

Create minimal parameterized rules, instead of create many specific rules.

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
    
    ColorProductRule(String param) {
        this.color = param;
    }
    
    // ...
}

Rule redProductRule = new ColorProductRule("red");
Rule bleuProductRule = new ColorProductRule("bleu");
Rule greenProductRule = new ColorProductRule("green");
```

#### Usage

For **manual** usage the parameter can be set using _constructor_ or _setter_. 

For **dynamic engine usage**, the property (field or setter) must be annotated with `RuleParameter`.

```java
class ColorProductRule implements Rule<Model> {
    
    @RuleParameter("color") // one or ...
    String color;
    
    @Override
    boolean test(Model model) {
        System.out.println(color);
        // ...
    }
    
    @RuleParameter("color") // ... the other
    void setColor(String param) {
        color = param;
    }
}
```

### Context

The `Context` corresponds to the *context execution* of rules, with processed data.

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