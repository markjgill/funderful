package markjgill.functional.funderful;

import io.vavr.Function1;

public class Relational {

    private Relational() {}

    public static <A, B> Function1<A, Boolean> propEquals(Function1<A, B> func, B candidate) {
        return value -> func.apply(value).equals(candidate);
    }

    public static <A, B> Function1<B, Function1<A, Boolean>> propEquals(Function1<A, B> func) {
        return value -> propEquals(func, value);
    }

    public static <A, B> Function1<A, Boolean> equalTo(B candidate) {
        return value -> value.equals(candidate);
    }
}
