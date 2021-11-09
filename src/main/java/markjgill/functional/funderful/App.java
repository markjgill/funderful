package markjgill.functional.funderful;

import io.vavr.Function1;
import io.vavr.control.Option;

import java.time.LocalDate;
import java.util.function.Function;

import static java.time.DayOfWeek.MONDAY;
import static markjgill.functional.funderful.Classical.*;

public class App {

    public static void main(String args[]) {
        System.out.println(path(Person::getAddress, Person::getParent).apply(new Person()));
    }
}

class Address {

    private String city = "Auckland";
    private String street;

    public String getCity() {
        return city;
    }

    public String getStreet() {
        return street;
    }
}

class Person {

    private Address address = new Address();
    private Person parent;

    public Address getAddress() {
        return address;
    }

    public Person getParent() {
        return parent;
    }
}
