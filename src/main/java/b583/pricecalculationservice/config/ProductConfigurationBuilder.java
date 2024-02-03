package b583.pricecalculationservice.config;

import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.List;

public class ProductConfigurationBuilder {
    private BigDecimal basePrice;
    private List<@NotNull PercentOffPromotionConfiguration> percentOffPromotions;
    private List<@NotNull CountBasedPromotionConfiguration> countBasedPromotions;

    ProductConfigurationBuilder() {
    }

    public ProductConfigurationBuilder withBasePrice(BigDecimal basePrice) {
        this.basePrice = basePrice;
        return this;
    }

    public ProductConfigurationBuilder withPercentOffPromotions(List<@NotNull PercentOffPromotionConfiguration> percentOffPromotions) {
        this.percentOffPromotions = percentOffPromotions;
        return this;
    }

    public ProductConfigurationBuilder withCountBasedPromotions(List<@NotNull CountBasedPromotionConfiguration> countBasedPromotions) {
        this.countBasedPromotions = countBasedPromotions;
        return this;
    }

    public ProductConfiguration build() {
        return new ProductConfiguration(basePrice, percentOffPromotions, countBasedPromotions);
    }
}