package markjgill.functional.funderful;

import io.vavr.Function1;
import io.vavr.Tuple2;
import io.vavr.collection.Map;
import io.vavr.collection.Seq;

import java.util.function.Predicate;

public class Sequential {

    private Sequential() {}

    public static <A> Function1<Seq<A>, Boolean> isEmpty() {
        return value -> value.isEmpty();
    }

    public static <A, B> Function1<Seq<A>, Seq<B>> map(Function1<A, B> func) {
        return value -> value.map(func);
    }

    public static <A, B> Function1<Seq<Seq<A>>, Seq<B>> flatmap(Function1<Seq<A>, Seq<B>> func) {
        return value -> value.flatMap(func);
    }

    public static <A> Function1<Seq<A>, Boolean> includes(A candidate) {
        return value -> value.contains(candidate);
    }

    public static <A, B> Function1<Seq<A>, Seq<Tuple2<A, B>>> zip(Seq<B> candidate) {
        return value -> value.zip(candidate);
    }

    public static <A, B> Function1<Seq<A>, Map<B, ? extends Seq<A>>> groupBy(Function1<A, B> func) {
        return value -> value.groupBy(func);
    }

    public static <A> Function1<Seq<A>, Tuple2<? extends Seq<A>, ? extends Seq<A>>> partition(Predicate<A> func) {
        return value -> value.partition(func);
    }
}