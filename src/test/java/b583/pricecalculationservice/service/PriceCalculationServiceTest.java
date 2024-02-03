package b583.pricecalculationservice.service;

import b583.pricecalculationservice.config.*;
import jakarta.ws.rs.NotFoundException;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class PriceCalculationServiceTest {

    private static final UUID PRESENT_PRODUCT_UUID = UUID.fromString("d651b6a0-6753-43a3-be55-c7917dce55d6");
    private static final UUID PRESENT_PRODUCT_WITH_NO_PROMOTIONS_UUID = UUID.fromString("0f9e3558-a1e2-438a-9e16-8950fc8b43d4");
    private static final UUID MISSING_PRODUCT_UUID = UUID.fromString("97aee9c6-a843-4abd-8cd8-ed1ed5d5216e");

    private final static Instant NOW = Instant.now();

    private static final PriceCalculationServiceConfiguration CONFIGURATION = new PriceCalculationServiceConfiguration(
            Map.of(PRESENT_PRODUCT_UUID,
                    ProductConfiguration.builder()
                            .withBasePrice(BigDecimal.valueOf(199.99))
                            .withCountBasedPromotions(List.of(
                                    CountBasedPromotionConfiguration.builder()
                                            .withPromotionStartTime(NOW.minus(2, ChronoUnit.HOURS))
                                            .withPromotionEndTime(NOW.minus(1, ChronoUnit.HOURS))
                                            .withPercentOffSequence(List.of(BigDecimal.valueOf(15), BigDecimal.valueOf(25)))
                                            .build()
                            ))
                            .withPercentOffPromotions(List.of(
                                    PercentOffPromotionConfiguration.builder()
                                            .withPromotionStartTime(NOW.minus(1, ChronoUnit.HOURS))
                                            .withPromotionEndTime(NOW.plus(1, ChronoUnit.HOURS))
                                            .withPercentOff(BigDecimal.valueOf(5))
                                            .build(),
                                    PercentOffPromotionConfiguration.builder()
                                            .withPromotionStartTime(NOW.minus(1, ChronoUnit.HOURS))
                                            .withPromotionEndTime(NOW.plus(1, ChronoUnit.HOURS))
                                            .withPercentOff(BigDecimal.valueOf(10))
                                            .build()
                            ))
                            .build(),

                    PRESENT_PRODUCT_WITH_NO_PROMOTIONS_UUID,
                     ProductConfiguration.builder()
                             .withBasePrice(BigDecimal.valueOf(299.99))
                             .withCountBasedPromotions(Collections.emptyList())
                             .withPercentOffPromotions(Collections.emptyList())
                             .build()
            )
    );

    @Test
    void calculatePrice_productIsPresentAndHasPromotions_bestPromotionApplied() {
        // Given a configured PriceCalculationService
        final var priceCalculationService = new PriceCalculationService(CONFIGURATION);

        // When calculating price for a product with configured promotions
        final var price = priceCalculationService.calculatePrice(PRESENT_PRODUCT_UUID, 2);

        // Then price is calculated properly:
        // 15%, 25% off count based promotion filtered out due to being inactive
        // 5% promotion not taken into account as it is not the best deal
        // 10% promotion applied
        assertThat(price).isEqualTo(BigDecimal.valueOf(359.98));
    }

    @Test
    void calculatePrice_productIsPresentAndHasNoPromotions_basicPriceCalculated() {
        // Given a configured PriceCalculationService
        final var priceCalculationService = new PriceCalculationService(CONFIGURATION);

        // When calculating price for a product with no configured promotions
        final var price = priceCalculationService.calculatePrice(PRESENT_PRODUCT_WITH_NO_PROMOTIONS_UUID, 2);

        // Then price is calculated properly:
        // No promotions are applied
        // Basic price times amount calculation returned
        assertThat(price).isEqualTo(BigDecimal.valueOf(599.98));
    }

    @Test
    void calculatePrice_productIsMissing_notFoundExceptionThrown() {
        // Given a configured PriceCalculationService
        final var priceCalculationService = new PriceCalculationService(CONFIGURATION);

        // When calculating price for a missing product
        // Then NotFoundException is thrown
        assertThatThrownBy(() -> priceCalculationService.calculatePrice(MISSING_PRODUCT_UUID, 1))
                .isInstanceOf(NotFoundException.class);
    }

}