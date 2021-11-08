package markjgill.functional.funderful;

import java.time.LocalDate;

import static markjgill.functional.funderful.Classical.*;
import static markjgill.functional.funderful.Logical.all;
import static markjgill.functional.funderful.Logical.any;

public class App {

    public static void main(String[] args) {
        Test test = new Test();

        var a = all(is(Test.class), is(Test.class))
                .apply(test);

        System.out.println(a);
    }
}

class Test {

    private String name = "Temp";

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Test{" +
                "name='" + name + '\'' +
                '}';
    }
}

class TestCopy {

    private String first;
    private String last;
    private LocalDate birthday;

    public String getFirst() {
        return first;
    }

    public String getLast() {
        return last;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setFirst(String first) {
        this.first = first;
    }

    public void setLast(String last) {
        this.last = last;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    @Override
    public String toString() {
        return "TestCopy{" +
                "first='" + first + '\'' +
                ", last='" + last + '\'' +
                ", birthday=" + birthday +
                '}';
    }
}