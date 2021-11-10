# funderful - A Wonderful Functional Library for Java

<b>Note: This is in development and makes no guarantee as to the accuracy of the functions

## Build / Import
Requires JDK11+ to build

```shell
mvn clean install
```

```xml
<dependency>
    <groupId>markjgill.functional</groupId>
    <artifactId>funderful</artifactId>
    <version>...</version>
</dependency>
```

## API

### Classical

#### optionOf() : A -> Option\<A\>
Returns A is wrapped in an Option
```java
optionOf().apply(5); // Some(5)
optionOf().apply(null); // Some(null)
```

#### optionWith() : (A -> Boolean) -> (A -> Option\<A\>) -> Option\<A\>
Returns A wrapped in an Option if the function returns true, Option.None otherwise
```java
optionWith(a -> a > 10).apply(15); // Some(15)
optionWith(a -> a > 10).apply(5); // None
optionWith(a -> a > 10).apply(null); // None
```

#### is() : Class\<B\> -> (A -> Boolean) -> Boolean
Returns true if the class of A is the same as B, false otherwise
```java
is(Integer.class).apply(10); // true
is(Integer.class).apply("10"); // false
```

#### prop() : (A -> B) -> (A -> B) -> B
Returns the value of the field referred to by the function
```java
prop(LocalDate::getDayOfWeek).apply(LocalDate.now()); // SATURDAY
prop(Person::getName).apply(new Person()); // null
```

#### propOr() : B -> (A -> B) -> (A -> B) -> B
Returns the value of the field referred to by the function, or B if null
```java
propOr(MONDAY, LocalDate::getDayOfWeek).apply(LocalDate.now()); // SATURDAY
propOr("Name", Person::getName).apply(new Person()); // "Name"
propOr(10); // (A -> B) -> (A -> B) -> B
```

#### path() : (D -> E) ->  ... -> (A -> B) -> (A -> Option\<E\>) -> Option\<E\>
Return an option of the value of the field referred to by the path
```java
Address address = new Address("Auckland");
Person person = new Person(address);

path(Address::getCity, Person::getAddress).apply(person); // Some("Auckland")
path(Address::getStreet, Person::getAddress).apply(person); // Some(null)
path(Person::getAddress, Person::getParent).apply(person); // None
```

## Example Usages
Each function must be statically imported into your class

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
var result = construct(
    set(lens(Person::getAge, Person::setAge), 29),
    set(lens(Person::getSurname, Person::setSurname), "Smith"),
    set(lens(Person::getFirstname, Person::setFirstname), "Joe"),
).apply(Person::new);
```

- Handling exceptions
```java
CheckedFunction1<Path, List<String>> fileReader = path -> Files.readAllLines(path);
var result = tryCatch(fileReader, path -> List.of())
    .apply(pathObject);
```
