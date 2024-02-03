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
