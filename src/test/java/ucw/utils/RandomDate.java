package ucw.utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Random;

public class RandomDate {
    private final LocalDate minDate;
    private final LocalDate maxDate;
    private final Random random;

    public RandomDate(LocalDate minDate, LocalDate maxDate) {
        this.minDate = minDate;
        this.maxDate = maxDate;
        this.random = new Random();
    }

    public String nextDate() {
        int minDay = (int) minDate.toEpochDay();
        int maxDay = (int) maxDate.toEpochDay();
        long randomDay = minDay + random.nextInt(maxDay - minDay);
        LocalDate result = LocalDate.ofEpochDay(randomDay);
        return DateTimeFormatter.ofPattern("dd-MM-yyyy").format(result);
    }
}
