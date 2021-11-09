package markjgill.functional.funderful;

import io.vavr.*;
import io.vavr.collection.List;
import io.vavr.control.Try;

public class Functional {

    private Functional() {}

    public static <A> Function1<A, A> identity() {
        return Function1.identity();
    }

    public static <A, B> Function1<A, B> always(B value) {
        return Function1.constant(value);
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

    public static <A> Function0<A> tryCatch(CheckedFunction0<A> tryer,
                                            Function0<A> catcher) {
        return () -> Try.ofSupplier(() -> tryer.unchecked().apply())
                .getOrElse(() -> catcher.apply());
    }

    public static <A, B, C> Function1<A, C> compose(Function1<B, C> second,
                                                    Function1<A, B> first) {
        return Curryable.<A, B, C, C, C, C, C, C>compose()
                .apply(identity())
                .apply(identity())
                .apply(identity())
                .apply(identity())
                .apply(identity())
                .apply(second)
                .apply(first);
    }

    public static <A, B, C, D> Function1<A, D> compose(Function1<C, D> third,
                                                       Function1<B, C> second,
                                                       Function1<A, B> first) {
        return Curryable.<A, B, C ,D, D, D, D, D>compose()
                .apply(identity(), identity(), identity(), identity())
                .apply(third)
                .apply(second)
                .apply(first);
    }

    public static <A, B, C, D, E> Function1<A, E> compose(Function1<D, E> fourth,
                                                          Function1<C, D> third,
                                                          Function1<B, C> second,
                                                          Function1<A, B> first) {
        return Curryable.<A, B, C ,D, E, E, E, E>compose()
                .apply(identity(), identity(), identity())
                .apply(fourth)
                .apply(third)
                .apply(second)
                .apply(first);
    }

    public static <A, B, C, D, E, F> Function1<A, F> compose(Function1<E, F> fifth,
                                                             Function1<D, E> fourth,
                                                             Function1<C, D> third,
                                                             Function1<B, C> second,
                                                             Function1<A, B> first) {
        return Curryable.<A, B, C ,D, E, F, F, F>compose()
                .apply(identity(), identity())
                .apply(fifth)
                .apply(fourth)
                .apply(third)
                .apply(second)
                .apply(first);
    }

    public static <A, B, C, D, E, F, G> Function1<A, G> compose(Function1<F, G> sixth,
                                                                Function1<E, F> fifth,
                                                                Function1<D, E> fourth,
                                                                Function1<C, D> third,
                                                                Function1<B, C> second,
                                                                Function1<A, B> first) {
        return Curryable.<A, B, C ,D, E, F, G, G>compose()
                .apply(identity())
                .apply(sixth)
                .apply(fifth)
                .apply(fourth)
                .apply(third)
                .apply(second)
                .apply(first);
    }

    public static <A, B, C, D, E, F, G, H> Function1<A, H> compose(Function1<G, H> seventh,
                                                                   Function1<F, G> sixth,
                                                                   Function1<E, F> fifth,
                                                                   Function1<D, E> fourth,
                                                                   Function1<C, D> third,
                                                                   Function1<B, C> second,
                                                                   Function1<A, B> first) {
        return Curryable.<A, B, C ,D, E, F, G, H>compose()
                .apply(seventh)
                .apply(sixth)
                .apply(fifth)
                .apply(fourth)
                .apply(third)
                .apply(second)
                .apply(first);
    }

    @SafeVarargs
    public static <A> Function1<A, A> compose(Function1<A, A>... functions) {
        return value -> List.of(functions)
                .reduceRight(Function1::compose)
                .apply(value);
    }

    private static class Curryable {

        private Curryable() {}

        public static <A, B, C, D, E, F, G, H> Function8<Function1<G, H>,
                                                         Function1<F, G>,
                                                         Function1<E, F>,
                                                         Function1<D, E>,
                                                         Function1<C, D>,
                                                         Function1<B, C>,
                                                         Function1<A, B>,
                                                         A,
                                                         H> compose() {
            return (seventh, sixth, fifth, fourth, third, second, first, value) -> seventh.compose(sixth)
                    .compose(fifth)
                    .compose(fourth)
                    .compose(third)
                    .compose(second)
                    .compose(first)
                    .apply(value);
        }
    }
}