package markjgill.functional.funderful;

import io.vavr.Function1;
import io.vavr.Function2;
import io.vavr.Tuple2;
import io.vavr.collection.Map;
import io.vavr.collection.Seq;

import java.util.function.Predicate;

public class Sequential {

    private Sequential() {}

    public static <A> Function1<Seq<A>, Boolean> isEmpty() {
        return seq -> seq.isEmpty();
    }

    public static <A, B> Function1<Seq<A>, Seq<B>> map(Function1<A, B> function) {
        return Curryable.<A, B>map()
                .apply(function);
    }

    public static <A, B> Function1<Seq<Seq<A>>, Seq<B>> flatmap(Function1<Seq<A>, Seq<B>> function) {
        return Curryable.<A, B>flatMap()
                .apply(function);
    }

    public static <A> Function1<Seq<A>, Boolean> includes(A value) {
        return Curryable.<A>contains()
                .apply(value);
    }

    public static <A, B> Function1<Seq<B>, Seq<Tuple2<A, B>>> zip(Seq<A> seq1) {
        return Curryable.<A, B>zip()
                .apply(seq1);
    }

    public static <A, B> Function1<Seq<A>, Map<B, ? extends Seq<A>>> groupBy(Function1<A, B> function) {
        return Curryable.<A, B>groupBy()
                .apply(function);
    }

    public static <A> Function1<Seq<A>, Tuple2<? extends Seq<A>, ? extends Seq<A>>> partition(Predicate<A> predicate) {
        return Curryable.<A>partition()
                .apply(predicate);
    }

    private static class Curryable {

        private Curryable() {}

        public static <A, B> Function2<Function1<A, B>, Seq<A>, Seq<B>> map() {
            return (function, seq) -> seq.map(function);
        }

        public static <A, B> Function2<Function1<Seq<A>, Seq<B>>, Seq<Seq<A>>, Seq<B>> flatMap() {
            return (function, seq) -> seq.flatMap(function);
        }

        public static <A> Function2<A, Seq<A>, Boolean> contains() {
            return (value, seq) -> seq.contains(value);
        }

        public static <A, B> Function2<Seq<A>, Seq<B>, Seq<Tuple2<A, B>>> zip() {
            return (seq1, seq2) -> seq1.zip(seq2);
        }

        public static <A, B> Function2<Function1<A, B>, Seq<A>, Map<B, ? extends Seq<A>>> groupBy() {
            return (function, seq) -> seq.groupBy(function);
        }

        public static <A> Function2<Predicate<A>, Seq<A>, Tuple2<? extends Seq<A>, ? extends Seq<A>>> partition() {
            return (predicate, seq) -> seq.partition(predicate);
        }
    }
}
