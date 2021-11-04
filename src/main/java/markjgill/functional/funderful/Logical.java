package markjgill.functional.funderful;

import io.vavr.Function1;
import io.vavr.collection.List;

public class Logical {

    private Logical() {}

    public static <A> Function1<A, Boolean> either(Function1<A, Boolean> first,
                                                   Function1<A, Boolean> second) {
        return any(first, second);
    }

    public static <A> Function1<A, Boolean> both(Function1<A, Boolean> first,
                                                 Function1<A, Boolean> second) {
        return all(first, second);
    }

    public static <A> Function1<A, Boolean> any(Function1<A, Boolean>... funcs) {
        return value -> List.of(funcs)
                .dropUntil(func -> func.apply(value))
                .headOption()
                .isDefined();
    }

    public static <A> Function1<A, Boolean> all(Function1<A, Boolean>... funcs) {
        return value -> List.of(funcs)
                .dropWhile(func -> func.apply(value))
                .headOption()
                .isEmpty();
    }

    public static <A> Function1<A, Boolean> complement(Function1<A, Boolean> func) {
        return value -> !(func).apply(value);
    }

    public static <A, B> Function1<A, B> ifElse(Function1<A, Boolean> condition,
                                                Function1<A, B> onTrue,
                                                Function1<A, B> onFalse) {
        return value -> condition.apply(value) ? onTrue.apply(value) : onFalse.apply(value);
    }
}