# funderful
A functional library for Java

## Usage
- Composing functions
```java
var result = compose(
    ...
    either(equalTo(SATURDAY), equalTo(SUNDAY)),
    prop(LocalDate::getDayOfWeek)
).apply(LocalDate.now());
```

- Pattern matching
```java
var result = cond(
    Tuple.of(either(equalTo(MONDAY), equalTo(TUESDAY)), always("Uphill Struggle")),
    Tuple.of(equalTo(WEDNESDAY), always("Hump Day")),
    Tuple.of(always(true), always("Downhill all the way"))
).apply(LocalDate.now().getDayOfWeek());
```

- Object instantiation
```java
var result = compose(
    set(Person::setAge, 29),
    set(Person::setSurname, "Smith"),
    set(Person::setFirstname, "Joe"),
    construct()
).apply(Person::new);
```

- Handling exceptions
```java
CheckedFunction1<Path, List<String>> fileReader = path -> Files.readAllLines(path);
var result = tryCatch(fileReader, path -> List.of())
    .apply(pathObject);
```