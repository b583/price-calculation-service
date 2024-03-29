package b583.pricecalculationservice.config;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

public class CountBasedPromotionConfiguration {

    @NotNull
    @JsonProperty
    private Instant promotionStartTime;

    @NotNull
    @JsonProperty
    private Instant promotionEndTime;

    @NotNull
    @NotEmpty
    @JsonProperty
    private List<@NotNull @Positive BigDecimal> percentOffSequence;

    CountBasedPromotionConfiguration() {
        // Deserialization
    }

    CountBasedPromotionConfiguration(Instant promotionStartTime, Instant promotionEndTime, List<@NotNull @Positive BigDecimal> percentOffSequence) {
        this.promotionStartTime = promotionStartTime;
        this.promotionEndTime = promotionEndTime;
        this.percentOffSequence = percentOffSequence;
    }

    public static CountBasedPromotionConfigurationBuilder builder() {
        return new CountBasedPromotionConfigurationBuilder();
    }

    public Instant getPromotionStartTime() {
        return promotionStartTime;
    }

    public Instant getPromotionEndTime() {
        return promotionEndTime;
    }

    public List<BigDecimal> getPercentOffSequence() {
        return percentOffSequence;
    }
}
