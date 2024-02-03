package b583.pricecalculationservice.config;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigInteger;
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
    private List<@NotNull @Positive BigInteger> percentOffSequence;

    public Instant getPromotionStartTime() {
        return promotionStartTime;
    }

    public Instant getPromotionEndTime() {
        return promotionEndTime;
    }

    public List<BigInteger> getPercentOffSequence() {
        return percentOffSequence;
    }
}
