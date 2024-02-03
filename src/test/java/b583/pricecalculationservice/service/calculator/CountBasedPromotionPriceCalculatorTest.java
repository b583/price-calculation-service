package b583.pricecalculationservice.service.calculator;

import b583.pricecalculationservice.config.CountBasedPromotionConfiguration;
import b583.pricecalculationservice.config.PercentOffPromotionConfiguration;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class CountBasedPromotionPriceCalculatorTest {

    @Test
    void calculatePrice_activePromotionAndBigOrder_maximalPromotionApplied() {
        final var now = Instant.now();

        // Given an active 15% off promotion
        final var promotionConfiguration = CountBasedPromotionConfiguration.builder()
                .withPromotionStartTime(now.minus(1, ChronoUnit.HOURS))
                .withPromotionEndTime(now.plus(1, ChronoUnit.HOURS))
                .withPercentOffSequence(List.of(BigDecimal.valueOf(3), BigDecimal.valueOf(5), BigDecimal.valueOf(7)))
                .build();

        // When calculating price of 199.99 product times 10
        final var price = CountBasedPromotionPriceCalculator
                .calculatePrice(promotionConfiguration, BigDecimal.valueOf(199.99), 10);

        // Then price is calculated with maximal promotion applied
        assertThat(price).isPresent();
        assertThat(price.get()).isEqualTo(new BigDecimal("1859.90"));
        // Use constructor to workaround BigDecimal.valueOf(1859.90) returning 1859.9, breaking assertion
    }

    @Test
    void calculatePrice_activePromotionAndMediumOrder_properPromotionApplied() {
        final var now = Instant.now();

        // Given an active 15% off promotion
        final var promotionConfiguration = CountBasedPromotionConfiguration.builder()
                .withPromotionStartTime(now.minus(1, ChronoUnit.HOURS))
                .withPromotionEndTime(now.plus(1, ChronoUnit.HOURS))
                .withPercentOffSequence(List.of(BigDecimal.valueOf(3), BigDecimal.valueOf(5), BigDecimal.valueOf(7)))
                .build();

        // When calculating price of 199.99 product times 2
        final var price = CountBasedPromotionPriceCalculator
                .calculatePrice(promotionConfiguration, BigDecimal.valueOf(199.99), 2);

        // Then price is calculated with proper promotion applied
        assertThat(price).isPresent();
        assertThat(price.get()).isEqualTo(BigDecimal.valueOf(379.98));
    }

    @Test
    void calculatePrice_inactivePromotion_promotionNotApplied() {
        final var now = Instant.now();

        // Given an inactive 15% off promotion
        final var promotionConfiguration = CountBasedPromotionConfiguration.builder()
                .withPromotionStartTime(now.minus(2, ChronoUnit.HOURS))
                .withPromotionEndTime(now.minus(1, ChronoUnit.HOURS))
                .withPercentOffSequence(List.of(BigDecimal.valueOf(3), BigDecimal.valueOf(5), BigDecimal.valueOf(7)))
                .build();

        // When calculating price of 199.99 product times 2
        final var price = CountBasedPromotionPriceCalculator
                .calculatePrice(promotionConfiguration, BigDecimal.valueOf(199.99), 2);

        // Then price is not calculated
        assertThat(price).isEmpty();
    }

}