package b583.pricecalculationservice.config;

import java.math.BigDecimal;
import java.time.Instant;

public class PercentOffPromotionConfigurationBuilder {
    private Instant promotionStartTime;
    private Instant promotionEndTime;
    private BigDecimal percentOff;

    PercentOffPromotionConfigurationBuilder() {}

    public PercentOffPromotionConfigurationBuilder withPromotionStartTime(Instant promotionStartTime) {
        this.promotionStartTime = promotionStartTime;
        return this;
    }

    public PercentOffPromotionConfigurationBuilder withPromotionEndTime(Instant promotionEndTime) {
        this.promotionEndTime = promotionEndTime;
        return this;
    }

    public PercentOffPromotionConfigurationBuilder withPercentOff(BigDecimal percentOff) {
        this.percentOff = percentOff;
        return this;
    }

    public PercentOffPromotionConfiguration build() {
        return new PercentOffPromotionConfiguration(promotionStartTime, promotionEndTime, percentOff);
    }
}