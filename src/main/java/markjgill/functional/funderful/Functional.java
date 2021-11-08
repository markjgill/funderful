package markjgill.functional.funderful;

import io.vavr.*;
import io.vavr.collection.List;
import io.vavr.control.Try;

public class Functional {

    private Functional() {}

    public static <A> Function1<A, A> identity() {
        return value -> value;
    }

    public static <A, B> Function1<A, B> always(B value2) {
        return value1 -> value2;
    }

//    public static Function0<Boolean> alwaysTrue() {
//        return always(true);
//    }
//
//    public static Function0<Boolean> alwaysFalse() {
//        return always(false);
//    }

    public static <A, B> Function1<A, B> tryCatch(Function1<A, B> tryer,
                                                  Function1<A, B> catcher) {
        return value -> Try.ofSupplier(() -> tryer.apply(value))
                .getOrElse(() -> catcher.apply(value));
    }

    public static <A, B> Function1<A, B> tryCatch(CheckedFunction1<A, B> tryer,
                                                  Function1<A, B> catcher) {
        return value -> Try.ofSupplier(() -> tryer.unchecked().apply(value))
                .getOrElse(() -> catcher.apply(value));
    }

    public static <A> Function0<A> tryCatch(CheckedFunction0<A> tryer,
                                            Function0<A> catcher) {
        return () -> Try.ofSupplier(() -> tryer.unchecked().apply())
                .getOrElse(() -> catcher.apply());
    }

    public static <A, B, C> Function1<A, C> compose(Function1<B, C> second,
                                                    Function1<A, B> first) {
        return value -> second.compose(first)
                .apply(value);
    }

    public static <A, B, C, D> Function1<A, D> compose(Function1<C, D> third,
                                                       Function1<B, C> second,
                                                       Function1<A, B> first) {
        return value -> compose(third, compose(second, first))
                .apply(value);
    }

    public static <A> Function1<A, A> compose(Function1<A, A>... functions) {
        return value -> List.of(functions)
                .reduceRight(Function1::compose)
                .apply(value);
    }

    public static <A, B> Function0<B> thunkify(Function1<A, B> function,
                                               A value) {
        return () -> function.apply(value);
    }

    public static <A, B, C> Function1<B, Function0<C>> thunkify(Function2<A, B, C> function,
                                                                A value1) {
        return Curryable.thunkify(function)
                .curried()
                .apply(value1);
    }

    public static <A, B, C> Function0<C> thunkify(Function2<A, B, C> function,
                                                  A value1,
                                                  B value2) {
        return Curryable.thunkify(function)
                .curried()
                .apply(value1)
                .apply(value2);
    }

    private static class Curryable {

        private Curryable() {}

        public static <A, B, C> Function2<A, B, Function0<C>> thunkify(Function2<A, B, C> function) {
            return (value1, value2) -> () -> function.apply(value1, value2);
        }
    }
}