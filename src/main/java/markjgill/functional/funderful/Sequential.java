package markjgill.functional.funderful;

import io.vavr.Function1;
import io.vavr.collection.Seq;

public class Sequential {

    private Sequential() {}

    public static <A, B> Function1<Seq<A>, Seq<B>> map(Function1<A, B> func) {
        return value -> value.map(func);
    }

    public static <A, B> Function1<Seq<Seq<A>>, Seq<B>> flatmap(Function1<Seq<A>, Seq<B>> func) {
        return value -> value.flatMap(func);
    }
}