package b583.pricecalculationservice;

import io.dropwizard.core.Configuration;
import jakarta.validation.constraints.NotBlank;

public class PriceCalculationServiceConfiguration extends Configuration {

    @NotBlank
    private String dummy;

    public String getDummy() {
        return dummy;
    }
}
