package b583.pricecalculationservice.config;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

public class CountBasedPromotionConfigurationBuilder {
    private Instant promotionStartTime;
    private Instant promotionEndTime;
    private List<@NotNull @Positive BigDecimal> percentOffSequence;

    CountBasedPromotionConfigurationBuilder() {
    }

    public CountBasedPromotionConfigurationBuilder withPromotionStartTime(Instant promotionStartTime) {
        this.promotionStartTime = promotionStartTime;
        return this;
    }

    public CountBasedPromotionConfigurationBuilder withPromotionEndTime(Instant promotionEndTime) {
        this.promotionEndTime = promotionEndTime;
        return this;
    }

    public CountBasedPromotionConfigurationBuilder withPercentOffSequence(List<@NotNull @Positive BigDecimal> percentOffSequence) {
        this.percentOffSequence = percentOffSequence;
        return this;
    }

    public CountBasedPromotionConfiguration build() {
        return new CountBasedPromotionConfiguration(promotionStartTime, promotionEndTime, percentOffSequence);
    }
}