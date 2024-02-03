package b583.pricecalculationservice.service.calculator;

import b583.pricecalculationservice.config.CountBasedPromotionConfiguration;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static b583.pricecalculationservice.service.calculator.PercentOffPromotionPriceCalculator.calculatePercentOffPromotionPrice;
import static org.apache.commons.lang3.BooleanUtils.isFalse;

public class CountBasedPromotionPriceCalculator {

    private CountBasedPromotionPriceCalculator() {}

    public static Optional<BigDecimal> calculatePrice(CountBasedPromotionConfiguration promotion, BigDecimal price, Integer amount) {
        if (isFalse(TimeUtils.isNowInRange(promotion.getPromotionStartTime(), promotion.getPromotionEndTime()))) {
            return Optional.empty();
        }

        final var percentOffSequence = promotion.getPercentOffSequence();

        if (doesAmountExceedSequenceSize(percentOffSequence, amount)) {
            // Apply last percentage
            return Optional.of(
                    // Subtract 1 from size to avoid IndexOutOfBounds
                    calculatePercentOffPromotionPrice(price, amount, percentOffSequence.get(percentOffSequence.size() - 1))
            );
        }

        // Apply percentage from the sequence
        return Optional.of(
                // amount: 1 -> sequence 0
                // amount: 2 -> sequence 1
                // and so on
                calculatePercentOffPromotionPrice(price, amount, percentOffSequence.get(amount - 1))
        );
    }

    private static boolean doesAmountExceedSequenceSize(List<BigDecimal> percentOffSequence, Integer amount) {
        return percentOffSequence.size() < amount;
    }

}
