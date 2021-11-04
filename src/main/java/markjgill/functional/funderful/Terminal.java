package markjgill.functional.funderful;

import io.vavr.Function1;
import io.vavr.Tuple2;
import io.vavr.collection.List;
import io.vavr.control.Option;
import io.vavr.control.Try;

import static markjgill.functional.funderful.Functional.compose;

public class Terminal {

    private Terminal() {}

    public static <A, B> Function1<A, Option<B>> path(Function1<A, B> func) {
        return value -> Try.of(() -> func.apply(value))
                .toOption();
    }

    public static <A, B, C> Function1<A, Option<C>> path(Function1<B, C> second,
                                                         Function1<A, B> first) {
        return value -> Try.of(() -> compose(second, first).apply(value))
                .toOption();
    }

    public static <A, B, C, D> Function1<A, Option<D>> path(Function1<C, D> third,
                                                            Function1<B, C> second,
                                                            Function1<A, B> first) {
        return value -> Try.of(() -> compose(third, second, first).apply(value))
                .toOption();
    }

    public static <A, B> Function1<A, B> pathOr(Function1<A, B> func,
                                                B alternative) {
        return value -> path(func).apply(value)
                .getOrElse(alternative);
    }

    public static <A, B, C> Function1<A, C> pathOr(Function1<B, C> second,
                                                   Function1<A, B> first,
                                                   C alternative) {
        return value -> path(second, first).apply(value)
                .getOrElse(alternative);
    }

    public static <A, B, C, D> Function1<A, D> pathOr(Function1<C, D> third,
                                                      Function1<B, C> second,
                                                      Function1<A, B> first,
                                                      D alternative) {
        return value -> path(third, second, first).apply(value)
                .getOrElse(alternative);
    }

    public static <A, B> Function1<A, Option<B>> match(Tuple2<Function1<A, Boolean>, Function1<A, B>>... funcs) {
        return value -> List.of(funcs)
                .dropUntil(tuple -> tuple._1().apply(value))
                .headOption()
                .map(tuple -> tuple._2().apply(value));
    }
}
