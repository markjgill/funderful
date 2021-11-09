package markjgill.functional.funderful;

import io.vavr.Function1;
import io.vavr.Function2;

public class Lexical {

    private Lexical() {}

    public static Function1<String, Boolean> startsWith(String candidate) {
        return Curryable.startsWith()
                .apply(candidate);
    }

    private static class Curryable {

        private Curryable() {}

        public static Function2<String, String, Boolean> startsWith() {
            return (candidate, string) -> string.startsWith(candidate);
        }
    }
}
