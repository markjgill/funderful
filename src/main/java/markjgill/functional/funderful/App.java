package markjgill.functional.funderful;

import io.vavr.Function1;
import io.vavr.control.Option;

import static markjgill.functional.funderful.Classical.is;
import static markjgill.functional.funderful.Classical.optionWith;

public class App {

    public static void main(String args[]) {
        System.out.println(is(Integer.class).apply("10"));
    }
}
