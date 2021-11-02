package markjgill.functional.funderful;

import io.vavr.Function1;

public class Logical {

    private Logical() {}

    public static <A> Function1<A, Boolean> either(Function1<A, Boolean> first,
                                                   Function1<A, Boolean> second) {
        return value -> first.apply(value) || second.apply(value);
    }

    public static <A> Function1<A, Boolean> both(Function1<A, Boolean> first,
                                                 Function1<A, Boolean> second) {
        return value -> first.apply(value) && second.apply(value);
    }

    public static <A, B> Function1<A, B> ifElse(Function1<A, Boolean> condition,
                                                Function1<A, B> onTrue,
                                                Function1<A, B> onFalse) {
        return value -> condition.apply(value) ? onTrue.apply(value) : onFalse.apply(value);
    }
}
