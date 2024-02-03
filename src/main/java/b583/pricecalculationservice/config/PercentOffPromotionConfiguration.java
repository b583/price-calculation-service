package b583.pricecalculationservice.config;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.time.Instant;

public class PercentOffPromotionConfiguration {

    @NotNull
    @JsonProperty
    private Instant promotionStartTime;

    @NotNull
    @JsonProperty
    private Instant promotionEndTime;

    @NotNull
    @Positive
    @JsonProperty
    private BigDecimal percentOff;

    PercentOffPromotionConfiguration() {
        // Deserialization
    }

    PercentOffPromotionConfiguration(Instant promotionStartTime, Instant promotionEndTime, BigDecimal percentOff) {
        this.promotionStartTime = promotionStartTime;
        this.promotionEndTime = promotionEndTime;
        this.percentOff = percentOff;
    }

    public static PercentOffPromotionConfigurationBuilder builder() {
        return new PercentOffPromotionConfigurationBuilder();
    }

    public Instant getPromotionStartTime() {
        return promotionStartTime;
    }

    public Instant getPromotionEndTime() {
        return promotionEndTime;
    }

    public BigDecimal getPercentOff() {
        return percentOff;
    }
}
