package b583.pricecalculationservice.service.calculator;

import java.time.Instant;

class TimeUtils {

    private TimeUtils() {}

    static boolean isNowInRange(Instant from, Instant to) {
        final var now = Instant.now();
        return now.isAfter(from) && now.isBefore(to);
    }

}
