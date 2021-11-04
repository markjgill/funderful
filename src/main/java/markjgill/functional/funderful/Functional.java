package markjgill.functional.funderful;

import io.vavr.CheckedFunction1;
import io.vavr.Function1;
import io.vavr.control.Try;

public class Functional {

    private Functional() {}

    public static <A, B, C> Function1<A, C> compose(Function1<B, C> second,
                                                    Function1<A, B> first) {
        return second.compose(first);
    }

    public static <A, B, C, D> Function1<A, D> compose(Function1<C, D> third,
                                                       Function1<B, C> second,
                                                       Function1<A, B> first) {
        return compose(third, compose(second, first));
    }

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
}