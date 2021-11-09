package markjgill.functional.funderful;

import io.vavr.*;
import io.vavr.control.Option;
import io.vavr.control.Try;

import java.util.function.BiConsumer;

import static markjgill.functional.funderful.Functional.*;

public class Classical {

    private Classical() {}

    public static <A> Function1<A, Option<A>> optionOf() {
        return object -> Option.of(object);
    }

    public static <A> Function1<A, Option<A>> optionWith(Function1<A, Boolean> condition) {
        return Curryable.<A>optionWith()
                .curried()
                .apply(condition);
    }

    public static <A, B> Function1<A, Boolean> is(Class<B> clazz) {
        return Curryable.<A, B>is()
                .curried()
                .apply(clazz);
    }

    public static <A, B> Function1<A, B> prop(Function1<A, B> getter) {
        return Curryable.<A, B>prop()
                .curried()
                .apply(getter);
    }

    public static <A, B> Function2<Function1<A, B>, A, B> propOr(B alternative) {
        return Curryable.<A, B>propOr()
                .apply(alternative);
    }

    public static <A, B> Function1<A, B> propOr(B alternative,
                                                Function1<A, B> getter) {
        return Curryable.<A, B> propOr()
                .apply(alternative)
                .apply(getter);
    }

    public static <A, B, C> Function1<A, Option<C>> path(Function1<B, C> second,
                                                         Function1<A, B> first) {
        return Curryable.<A, B, C, C, C>path()
                .apply(first)
                .apply(second)
                .apply(identity())
                .apply(identity());
    }

    public static <A, B, C, D> Function1<A, Option<D>> path(Function1<C, D> third,
                                                            Function1<B, C> second,
                                                            Function1<A, B> first) {
        return Curryable.<A, B, C, D, D>path()
                .apply(first)
                .apply(second)
                .apply(third)
                .apply(identity());
    }

    public static <A, B, C, D, E> Function1<A, Option<E>> path(Function1<D, E> fourth,
                                                               Function1<C, D> third,
                                                               Function1<B, C> second,
                                                               Function1<A, B> first) {
        return Curryable.<A, B, C, D, E>path()
                .apply(first)
                .apply(second)
                .apply(third)
                .apply(fourth);
    }

    public static <A, B, C> Function1<A, C> pathOr(C alternative,
                                                   Function1<B, C> second,
                                                   Function1<A, B> first) {
        return Curryable.<A, B, C, C, C>pathOr()
                .apply(alternative)
                .apply(first)
                .apply(second)
                .apply(identity())
                .apply(identity());
    }

    public static <A, B, C, D> Function1<A, D> pathOr(D alternative,
                                                      Function1<C, D> third,
                                                      Function1<B, C> second,
                                                      Function1<A, B> first) {
        return Curryable.<A, B, C, D, D>pathOr()
                .apply(alternative)
                .apply(first)
                .apply(second)
                .apply(third)
                .apply(identity());
    }

    public static <A, B, C, D, E> Function1<A, E> pathOr(E alternative,
                                                         Function1<D, E> fourth,
                                                         Function1<C, D> third,
                                                         Function1<B, C> second,
                                                         Function1<A, B> first) {
        return Curryable.<A, B, C, D, E>pathOr()
                .apply(alternative)
                .apply(first)
                .apply(second)
                .apply(third)
                .apply(fourth);
    }

    public static <A, B> Function1<Function1<A, B>, Function1<BiConsumer<A, B>, Function0<Tuple3<A, Function1<A, B>, BiConsumer<A, B>>>>> lens(A object) {
        return Curryable.<A, B>lens()
                .curried()
                .apply(object);
    }

    public static <A, B> Function1<BiConsumer<A, B>, Function0<Tuple3<A, Function1<A, B>, BiConsumer<A, B>>>> lens(A object,
                                                                                                                   Function1<A, B> getter) {
        return Curryable.<A, B>lens()
                .curried()
                .apply(object)
                .apply(getter);
    }

    public static <A, B> Function0<Tuple3<A, Function1<A, B>, BiConsumer<A, B>>> lens(A object,
                                                                                      Function1<A, B> getter,
                                                                                      BiConsumer<A, B> setter) {
        return Curryable.<A, B>lens()
                .curried()
                .apply(object)
                .apply(getter)
                .apply(setter);
    }

    public static <A> Function1<Function0<A>, A> construct() {
        return function -> function.apply();
    }

    @SafeVarargs
    public static <A> Function1<Function0<A>, A> construct(Function1<A, A> setter,
                                                           Function1<A, A>... others) {
        return function -> compose(
                compose(others),
                setter,
                construct()
        ).apply(function);
    }

    public static <A, B> Function1<A, A> set(BiConsumer<A, B> setter,
                                             B value) {
        return object -> setWith(lens(object, always(value), setter), setter)
                .apply(object);
    }

    public static <A, B, C> Function1<C, C> setWith(Function1<BiConsumer<A, B>, Function0<Tuple3<A, Function1<A, B>, BiConsumer<A, B>>>> lens,
                                                 BiConsumer<C, B> setter) {
        return Curryable.<A, B, C>setWith()
                .curried()
                .apply(lens.apply((a, b) -> new UnsupportedOperationException("Setter is unsupported")))
                .apply(setter);
    }

    public static <A, B, C> Function1<C, C> setWith(Function0<Tuple3<A, Function1<A, B>, BiConsumer<A, B>>> lens,
                                                    BiConsumer<C, B> setter) {
        return Curryable.<A, B, C>setWith()
                .curried()
                .apply(lens)
                .apply(setter);
    }

    private static class Curryable {

        private Curryable() {}

        public static <A> Function2<Function1<A, Boolean>, A, Option<A>> optionWith() {
            return (function, value) -> value != null && function.apply(value)
                    ? Option.of(value)
                    : Option.none();
        }

        public static <A, B> Function2<Class<B>, A, Boolean> is() {
            return (clazz, object) -> object.getClass().equals(clazz);
        }

        public static <A, B> Function2<Function1<A, B>, A, B> prop() {
            return Function1::apply;
        }

        public static <A, B> Function3<B, Function1<A, B>, A, B> propOr() {
            return (alternative, getter, object) -> Option.of(getter.apply(object))
                    .getOrElse(alternative);
        }

        public static <A, B, C, D, E> Function5<Function1<A, B>, Function1<B, C>, Function1<C, D>, Function1<D, E>, A, Option<E>> path() {
            return (first, second, third, fourth, object) -> Try.of(() -> compose(
                    fourth,
                    third,
                    second,
                    first
            ).apply(object)).toOption();
        }

        public static <A, B, C, D, E> Function6<E, Function1<A, B>, Function1<B, C>, Function1<C, D>, Function1<D, E>, A, E> pathOr() {
            return (alternative, first, second, third, fourth, object) -> Curryable.<A, B, C, D, E>path()
                    .apply(first, second, third, fourth, object)
                    .getOrElse(alternative);
        }

        public static <A, B> Function3<A, Function1<A, B>, BiConsumer<A, B>, Function0<Tuple3<A, Function1<A, B>, BiConsumer<A, B>>>> lens() {
            return (object, getter, setter) -> Function0.constant(Tuple.of(object, getter, setter));
        }

        public static <A, B, C> Function3<Function0<Tuple3<A, Function1<A, B>, BiConsumer<A, B>>>, BiConsumer<C, B>, C, C> setWith() {
            return (lens, setter, value) -> {
                Tuple3<A, Function1<A, B>, BiConsumer<A, B>> tuple = lens.apply();
                setter.accept(value, tuple._2().apply(tuple._1()));
                return value;
            };
        }
    }
}