package markjgill.functional.funderful;

import io.vavr.Function1;
import io.vavr.Function2;
import io.vavr.Function3;

public class Relational {

    private Relational() {}

    public static <A, B> Function1<A, Boolean> propSatisfies(Function1<B, Boolean> function,
                                                             Function1<A, B> getter) {
        return Curryable.<A, B>propSatisfies()
                .curried()
                .apply(function)
                .apply(getter);
    }

    public static <A, B> Function1<B, Function1<A, Boolean>> propEquals(Function1<A, B> function) {
        return Curryable.<A, B>propEquals()
                .curried()
                .apply(function);
    }

    public static <A, B> Function1<A, Boolean> propEquals(Function1<A, B> function,
                                                          B candidate) {
        return Curryable.<A, B> propEquals()
                .curried()
                .apply(function)
                .apply(candidate);
    }

    public static <A, B> Function1<B, Boolean> equalTo(A candidate) {
        return Curryable.<A, B>equalTo()
                .apply(candidate);
    }

    private static class Curryable {

        private Curryable() {}

        public static <A, B> Function3<Function1<B, Boolean>, Function1<A, B>, A, Boolean> propSatisfies() {
            return (function, getter, value) -> function.apply(getter.apply(value));
        }

        public static <A, B> Function3<Function1<A, B>, B, A, Boolean> propEquals() {
            return (function, candidate, value) -> function.apply(value).equals(candidate);
        }

        public static <A, B> Function2<A, B, Boolean> equalTo() {
            return (candidate, value) -> value.equals(candidate);
        }
    }
}
