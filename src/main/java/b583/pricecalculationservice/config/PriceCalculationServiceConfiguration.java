package b583.pricecalculationservice.config;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.core.Configuration;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import java.util.Map;
import java.util.UUID;

public class PriceCalculationServiceConfiguration extends Configuration {

    @Valid
    @NotNull
    @JsonProperty
    private Map<@NotNull UUID, @NotNull ProductConfiguration> products;

    public Map<UUID, ProductConfiguration> getProducts() {
        return products;
    }
}
