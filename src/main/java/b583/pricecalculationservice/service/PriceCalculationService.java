package b583.pricecalculationservice.service;

import b583.pricecalculationservice.config.PriceCalculationServiceConfiguration;
import b583.pricecalculationservice.service.calculator.CountBasedPromotionPriceCalculator;
import b583.pricecalculationservice.service.calculator.PercentOffPromotionPriceCalculator;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import jakarta.ws.rs.NotFoundException;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

import static java.util.Objects.isNull;

@Singleton
public class PriceCalculationService {

    @Inject
    private PriceCalculationServiceConfiguration configuration;

    public BigDecimal calculatePrice(UUID productUuid, Integer amount) {
        final var product = configuration.getProducts().get(productUuid);

        if (isNull(product)) {
            throw new NotFoundException();
        }

        final var percentOffPromotionPrices = product.getPercentOffPromotions()
                .stream()
                .map(promotion -> PercentOffPromotionPriceCalculator.calculatePrice(promotion, product.getBasePrice(), amount));

        final var countBasedPromotionPrices = product.getCountBasedPromotions()
                .stream()
                .map(promotion -> CountBasedPromotionPriceCalculator.calculatePrice(promotion, product.getBasePrice(), amount));

        // Check all configured promotions to find the best price for the consumer
        return Stream.concat(percentOffPromotionPrices, countBasedPromotionPrices)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .min(BigDecimal::compareTo)
                // In case there are no active promotions, fallback to basic price calculation
                .orElseGet(() -> product.getBasePrice().multiply(BigDecimal.valueOf(amount)));
    }

}


