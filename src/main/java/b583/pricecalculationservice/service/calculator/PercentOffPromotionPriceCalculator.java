package b583.pricecalculationservice.service.calculator;

import b583.pricecalculationservice.config.PercentOffPromotionConfiguration;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Optional;

import static org.apache.commons.lang3.BooleanUtils.isFalse;

public class PercentOffPromotionPriceCalculator {

    private PercentOffPromotionPriceCalculator() {}

    public static Optional<BigDecimal> calculatePrice(PercentOffPromotionConfiguration promotion, BigDecimal price, Integer amount) {
        if (isFalse(TimeUtils.isNowInRange(promotion.getPromotionStartTime(), promotion.getPromotionEndTime()))) {
            return Optional.empty();
        }

        return Optional.of(calculatePercentOffPromotionPrice(price, amount, promotion.getPercentOff()));
    }

    static BigDecimal calculatePercentOffPromotionPrice(BigDecimal price, Integer amount, BigDecimal percentOff) {
        return price.multiply(BigDecimal.valueOf(amount)).divide(percentOff, RoundingMode.DOWN);
    }

}
