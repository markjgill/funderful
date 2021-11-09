package markjgill.functional.funderful;

import io.vavr.Function1;
import io.vavr.Function2;
import io.vavr.Tuple;

import java.math.BigDecimal;
import java.math.BigInteger;

import static markjgill.functional.funderful.Classical.is;
import static markjgill.functional.funderful.Logical.cond;

@SuppressWarnings("unchecked")
public class Mathematical {

    private Mathematical() {}

    public static <A extends Number> Function1<A, A> add(A value1) {
        return Curryable.<A>add()
                .curried()
                .apply(value1);
    }

    public static <A extends Number> A add(A value1, A value2) {
        return Curryable.<A>add()
                .curried()
                .apply(value1)
                .apply(value2);
    }

    public static <A extends Number> Function1<A, A> subtract(A value1) {
        return Curryable.<A>subtract()
                .curried()
                .apply(value1);
    }

    public static <A extends Number> A subtract(A value1, A value2) {
        return Curryable.<A>subtract()
                .curried()
                .apply(value1)
                .apply(value2);
    }

    public static <A extends Number> Function1<A, A> power(Integer value) {
        return Curryable.<A>power()
                .curried()
                .apply(value);
    }

    public static <A extends Number> A power(Integer value1, A value2) {
        return Curryable.<A>power()
                .curried()
                .apply(value1)
                .apply(value2);
    }

    private static class Curryable {

        private Curryable() {}

        public static <A extends Number> Function2<A, A, A> add() {
            return (value1, value2) -> (A) cond(
                    Tuple.of(is(BigDecimal.class), value -> ((BigDecimal) value).add((BigDecimal) value1)),
                    Tuple.of(is(BigInteger.class), value -> ((BigInteger) value).add((BigInteger) value1)),
                    Tuple.of(is(Integer.class), value -> (int) value + value1.intValue()),
                    Tuple.of(is(Long.class), value -> (long) value + value1.longValue())
            ).apply(value2).getOrElseThrow(() ->
                    new UnsupportedOperationException(String.format("%s is not supported", value2.getClass().getSimpleName())));
        }

        public static <A extends Number> Function2<A, A, A> subtract() {
            return (value1, value2) -> (A) cond(
                    Tuple.of(is(BigDecimal.class), value -> ((BigDecimal) value).subtract((BigDecimal) value1)),
                    Tuple.of(is(BigInteger.class), value -> ((BigInteger) value).subtract((BigInteger) value1)),
                    Tuple.of(is(Integer.class), value -> (int) value - value1.intValue()),
                    Tuple.of(is(Long.class), value -> (long) value - value1.longValue())
            ).apply(value2).getOrElseThrow(() ->
                    new UnsupportedOperationException(String.format("%s is not supported", value2.getClass().getSimpleName())));
        }

        public static <A extends Number> Function2<Integer, A, A> power() {
            return (value1, value2) -> (A) cond(
                    Tuple.of(is(BigDecimal.class), value -> ((BigDecimal) value).pow(value1)),
                    Tuple.of(is(BigInteger.class), value -> ((BigInteger) value).pow(value1)),
                    Tuple.of(is(Integer.class), value -> (int) Math.pow((int) value, value1.doubleValue())),
                    Tuple.of(is(Long.class), value -> (long) Math.pow((long) value, value1.longValue()))
            ).apply(value2).getOrElseThrow(() ->
                    new UnsupportedOperationException(String.format("%s is not supported", value2.getClass().getSimpleName())));
        }
    }
}