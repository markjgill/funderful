package markjgill.functional.funderful;

import com.aldaviva.easter4j.Easter4J;
import io.vavr.*;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;

import static java.time.DayOfWeek.FRIDAY;
import static java.time.DayOfWeek.MONDAY;
import static java.time.temporal.TemporalAdjusters.*;

public class Dates {

    private Dates() {}

    public static Function0<LocalDate> from(Integer day, Month month, Integer year) {
        return () -> from(day, month).apply(year);
    }

    public static Function1<Integer, LocalDate> from(Integer day, Month month) {
        return from(day).apply(month);
    }

    public static Function2<Month, Integer, LocalDate> from(Integer day) {
        return from().apply(day);
    }

    public static Function3<Integer, Month, Integer, LocalDate> from() {
        return (day, month, year) -> LocalDate.of(year, month, day);
    }

    public static Function0<LocalDate> fromDayOfWeek(Integer ordinal, DayOfWeek dayOfWeek, Month month, Integer year) {
        return () -> fromDayOfWeek(ordinal, dayOfWeek, month).apply(year);
    }

    public static Function1<Integer, LocalDate> fromDayOfWeek(Integer ordinal, DayOfWeek dayOfWeek, Month month) {
        return fromDayOfWeek(ordinal, dayOfWeek).apply(month);
    }

    public static Function2<Month, Integer, LocalDate> fromDayOfWeek(Integer ordinal, DayOfWeek dayOfWeek) {
        return fromDayOfWeek(ordinal).apply(dayOfWeek);
    }

    public static Function3<DayOfWeek, Month, Integer, LocalDate> fromDayOfWeek(Integer ordinal) {
        return fromDayOfWeek().apply(ordinal);
    }

    public static Function4<Integer, DayOfWeek, Month, Integer, LocalDate> fromDayOfWeek() {
        return (ordinal, dayOfWeek, month, year) -> toNth(ordinal, dayOfWeek).compose(from(1, month)).apply(year);
    }

    public static Function1<LocalDate, LocalDate> toNext(DayOfWeek dayOfWeek) {
        return date -> date.with(next(dayOfWeek));
    }

    public static Function1<LocalDate, LocalDate> toPrevious(DayOfWeek dayOfWeek) {
        return date -> date.with(previous(dayOfWeek));
    }

    public static Function1<LocalDate, LocalDate> toNth(Integer ordinal, DayOfWeek dayOfWeek) {
        return date -> date.with(dayOfWeekInMonth(ordinal, dayOfWeek));
    }

    public static Function0<LocalDate> easterSunday(Integer year) {
        return () -> easterSunday().apply(year);
    }

    public static Function1<Integer, LocalDate> easterSunday() {
        return Easter4J::getEaster;
    };

    public static Function0<LocalDate> goodFriday(Integer year) {
        return () -> goodFriday().apply(year);
    }

    public static Function1<Integer, LocalDate> goodFriday() {
        return year -> toPrevious(FRIDAY).compose(easterSunday()).apply(year);
    }

    public static Function0<LocalDate> easterMonday(Integer year) {
        return () -> easterMonday().apply(year);
    }

    public static Function1<Integer, LocalDate> easterMonday() {
        return year -> toNext(MONDAY).compose(easterSunday()).apply(year);
    }

    public static Function1<LocalDate, Boolean> is(DayOfWeek dayOfWeek) {
        return date -> date.getDayOfWeek() == dayOfWeek;
    }
}