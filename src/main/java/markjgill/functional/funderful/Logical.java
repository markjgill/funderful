package markjgill.functional.funderful;

import io.vavr.*;
import io.vavr.collection.List;
import io.vavr.control.Option;

public class Logical {

    private Logical() {}

    public static <A> Function1<A, Boolean> complement(Function1<A, Boolean> function) {
        return Curryable.<A>complement()
                .apply(function);
    }

    public static <A> Function1<Function1<A, Boolean>, Function1<A, Boolean>> either(Function1<A, Boolean> function) {
        return Curryable.<A>either()
                .curried()
                .apply(function);
    }

    public static <A> Function1<A, Boolean> either(Function1<A, Boolean> function1,
                                                   Function1<A, Boolean> function2) {
        return Curryable.<A>either()
                .apply(function1, function2);
    }

    public static <A> Function1<Function1<A, Boolean>, Function1<A, Boolean>> both(Function1<A, Boolean> function) {
        return Curryable.<A>both()
                .curried()
                .apply(function);
    }

    public static <A> Function1<A, Boolean> both(Function1<A, Boolean> function1,
                                                 Function1<A, Boolean> function2) {
        return Curryable.<A>both()
                .apply(function1, function2);
    }

    public static <A> Function1<A, Boolean> any(Function1<A, Boolean> function,
                                                Function1<A, Boolean>... otherFunctions) {
        return Curryable.<A>any()
                .apply(function)
                .apply(otherFunctions);
    }

    public static <A> Function1<A, Boolean> all(Function1<A, Boolean> function,
                                                Function1<A, Boolean>... otherFunctions) {
        return Curryable.<A>all()
                .apply(function)
                .apply(otherFunctions);
    }

    public static <A, B> Function1<Function1<A, B>, Function1<Function1<A, B>, Function1<A, B>>> ifElse(Function1<A, Boolean> condition) {
        return Curryable.<A, B>ifElse()
                .curried()
                .apply(condition);
    }

    public static <A, B> Function1<Function1<A, B>, Function1<A, B>> ifElse(Function1<A, Boolean> condition,
                                                                            Function1<A, B> onTrue) {
        return Curryable.<A, B>ifElse()
                .curried()
                .apply(condition)
                .apply(onTrue);
    }

    public static <A, B> Function1<A, B> ifElse(Function1<A, Boolean> condition,
                                                Function1<A, B> onTrue,
                                                Function1<A, B> onFalse) {
        return Curryable.<A, B>ifElse()
                .apply(condition, onTrue, onFalse);
    }

    public static <A> Function1<Function1<A, A>, Function1<A, A>> when(Function1<A, Boolean> condition) {
        return Curryable.<A>when()
                .curried()
                .apply(condition);
    }

    public static <A> Function1<A, A> when(Function1<A, Boolean> condition,
                                           Function1<A, A> onTrue) {
        return Curryable.<A>when()
                .apply(condition, onTrue);
    }

    public static <A, B> Function1<A, Option<B>> cond(Tuple2<Function1<A, Boolean>, Function1<A, B>> condition,
                                                      Tuple2<Function1<A, Boolean>, Function1<A, B>>... otherConditions) {
        return Curryable.<A, B>cond()
                .apply(condition, otherConditions);
    }

    private static class Curryable {

        private Curryable() {}

        public static <A> Function2<Function1<A, Boolean>, A, Boolean> complement() {
            return (function, object) -> !function.apply(object);
        }

        public static <A> Function2<Function1<A, Boolean>, Function1<A, Boolean>, Function1<A, Boolean>> either() {
            return (function1, function2) -> value -> function1.apply(value) || function2.apply(value);
        }

        public static <A> Function2<Function1<A, Boolean>, Function1<A, Boolean>, Function1<A, Boolean>> both() {
            return (function1, function2) -> value -> function1.apply(value) && function2.apply(value);
        }

        public static <A> Function3<Function1<A, Boolean>, Function1<A, Boolean>[], A, Boolean> any() {
            return (function, otherFunctions, value) -> List.of(function)
                    .appendAll(List.of(otherFunctions))
                    .reduceRight((a, b) -> Curryable.<A>either().apply(a, b))
                    .apply(value);
        }

        public static <A> Function3<Function1<A, Boolean>, Function1<A, Boolean>[], A, Boolean> all() {
            return (function, otherFunctions, value) -> List.of(function)
                    .appendAll(List.of(otherFunctions))
                    .reduceRight((a, b) -> Curryable.<A>both().apply(a, b))
                    .apply(value);
        }

        public static <A, B> Function4<Function1<A, Boolean>, Function1<A, B>, Function1<A, B>, A, B> ifElse() {
            return (condition, onTrue, onFalse, object) -> condition.apply(object)
                    ? onTrue.apply(object)
                    : onFalse.apply(object);
        }

        public static <A> Function3<Function1<A, Boolean>, Function1<A, A>, A, A> when() {
            return (condition, onTrue, object) -> condition.apply(object)
                    ? onTrue.apply(object)
                    : object;
        }

        public static <A, B> Function3<Tuple2<Function1<A, Boolean>, Function1<A, B>>,
                                       Tuple2<Function1<A, Boolean>, Function1<A, B>>[],
                                       A, Option<B>> cond() {
            return (condition, otherConditions, object) -> List.of(condition)
                    .appendAll(List.of(otherConditions))
                    .dropUntil(cond -> cond._1().apply(object))
                    .headOption()
                    .map(cond -> cond._2().apply(object));
        }
    }
}