package b583.pricecalculationservice.config;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.util.List;

public class ProductConfiguration {

    @NotNull
    @Positive
    @JsonProperty
    private BigDecimal basePrice;

    @Valid
    @NotNull
    @JsonProperty
    private List<@NotNull PercentOffPromotionConfiguration> percentOffPromotions;

    @Valid
    @NotNull
    @JsonProperty
    private List<@NotNull CountBasedPromotionConfiguration> countBasedPromotions;

    ProductConfiguration() {
        // Deserialization
    }

    ProductConfiguration(BigDecimal basePrice, List<@NotNull PercentOffPromotionConfiguration> percentOffPromotions, List<@NotNull CountBasedPromotionConfiguration> countBasedPromotions) {
        this.basePrice = basePrice;
        this.percentOffPromotions = percentOffPromotions;
        this.countBasedPromotions = countBasedPromotions;
    }

    public static ProductConfigurationBuilder builder() {
        return new ProductConfigurationBuilder();
    }

    public BigDecimal getBasePrice() {
        return basePrice;
    }

    public List<PercentOffPromotionConfiguration> getPercentOffPromotions() {
        return percentOffPromotions;
    }

    public List<CountBasedPromotionConfiguration> getCountBasedPromotions() {
        return countBasedPromotions;
    }
}
