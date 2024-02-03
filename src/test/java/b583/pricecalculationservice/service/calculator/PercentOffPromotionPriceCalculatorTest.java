package b583.pricecalculationservice.service.calculator;

import b583.pricecalculationservice.config.PercentOffPromotionConfiguration;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

import static org.assertj.core.api.Assertions.assertThat;

class PercentOffPromotionPriceCalculatorTest {

    @Test
    void calculatePrice_activePromotion_promotionApplied() {
        final var now = Instant.now();

        // Given an active 15% off promotion
        final var promotionConfiguration = PercentOffPromotionConfiguration.builder()
                .withPromotionStartTime(now.minus(1, ChronoUnit.HOURS))
                .withPromotionEndTime(now.plus(1, ChronoUnit.HOURS))
                .withPercentOff(BigDecimal.valueOf(15))
                .build();

        // When calculating price of 199.99 product times 3
        final var price = PercentOffPromotionPriceCalculator
                .calculatePrice(promotionConfiguration, BigDecimal.valueOf(199.99), 3);

        // Then price is calculated with promotion applied
        assertThat(price).isPresent();
        assertThat(price.get()).isEqualTo(BigDecimal.valueOf(509.97));
    }

    @Test
    void calculatePrice_inactivePromotion_promotionNotApplied() {
        final var now = Instant.now();

        // Given an inactive 15% off promotion
        final var promotionConfiguration = PercentOffPromotionConfiguration.builder()
                .withPromotionStartTime(now.minus(2, ChronoUnit.HOURS))
                .withPromotionEndTime(now.minus(1, ChronoUnit.HOURS))
                .withPercentOff(BigDecimal.valueOf(15))
                .build();

        // When calculating price of 199.99 product times 3
        final var price = PercentOffPromotionPriceCalculator
                .calculatePrice(promotionConfiguration, BigDecimal.valueOf(199.99), 3);

        // Then price is not calculated
        assertThat(price).isEmpty();
    }

}