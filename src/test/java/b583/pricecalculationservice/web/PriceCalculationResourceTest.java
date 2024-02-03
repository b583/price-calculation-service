package b583.pricecalculationservice.web;

import b583.pricecalculationservice.PriceCalculationServiceApplication;
import b583.pricecalculationservice.config.PriceCalculationServiceConfiguration;
import io.dropwizard.testing.junit5.DropwizardAppExtension;
import io.dropwizard.testing.junit5.DropwizardExtensionsSupport;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(DropwizardExtensionsSupport.class)
class PriceCalculationResourceTest {

    private static final DropwizardAppExtension<PriceCalculationServiceConfiguration> EXT = new DropwizardAppExtension<>(
            PriceCalculationServiceApplication.class,
            "src/test/resources/b583/pricecalculationservice/web/config-test.yaml"
    );

    @Test
    void v1Price_requestingPriceForConfiguredProduct_priceCalculated() {
        // Given application running and product configured
        // When making a request to calculate product price
        final var response = EXT.client().target(
                "http://localhost:%d/v1/price/d651b6a0-6753-43a3-be55-c7917dce55d6?amount=1".formatted(EXT.getLocalPort()))
                .request()
                .get();

        // Then response is 200 OK with price correctly calculated
        assertThat(response.getStatus()).isEqualTo(200);
        assertThat(response.readEntity(PriceCalculationDTO.class).price()).isEqualTo(BigDecimal.valueOf(189.99));
    }

    @Test
    void v1Price_productNotPresentInRequest_notFoundReturned() {
        // Given application running and product configured
        // When making a request without a product
        final var response = EXT.client().target(
                        "http://localhost:%d/v1/price/".formatted(EXT.getLocalPort()))
                .request()
                .get();

        // Then response is 404
        assertThat(response.getStatus()).isEqualTo(404);
    }

    @Test
    void v1Price_requestingPriceForInvalidProductUUID_notFoundReturned() {
        // Given application running and product configured
        // When making a request for invalid product uuid
        final var response = EXT.client().target(
                        "http://localhost:%d/v1/price/boom?amount=1".formatted(EXT.getLocalPort()))
                .request()
                .get();

        // Then response is 404
        assertThat(response.getStatus()).isEqualTo(404);
    }

    @Test
    void v1Price_productMissingInConfiguration_notFoundReturned() {
        // Given application running and product configured
        // When making a request to calculate product price of missing product
        final var response = EXT.client().target(
                        "http://localhost:%d/v1/price/97aee9c6-a843-4abd-8cd8-ed1ed5d5216e?amount=1".formatted(EXT.getLocalPort()))
                .request()
                .get();

        // Then response is 404
        assertThat(response.getStatus()).isEqualTo(404);
    }

    @Test
    void v1Price_requestingPriceForConfiguredProductWithoutAmount_badRequestReturned() {
        // Given application running and product configured
        // When making a request with missing amount to calculate product price
        final var response = EXT.client().target(
                        "http://localhost:%d/v1/price/d651b6a0-6753-43a3-be55-c7917dce55d6".formatted(EXT.getLocalPort()))
                .request()
                .get();

        // Then response is 400
        assertThat(response.getStatus()).isEqualTo(400);
    }

    @Test
    void v1Price_requestingPriceForConfiguredProductWithNegativeAmount_badRequestReturned() {
        // Given application running and product configured
        // When making a request with negative amount to calculate product price
        final var response = EXT.client().target(
                        "http://localhost:%d/v1/price/d651b6a0-6753-43a3-be55-c7917dce55d6?amount=-1".formatted(EXT.getLocalPort()))
                .request()
                .get();

        // Then response is 400
        assertThat(response.getStatus()).isEqualTo(400);
    }

}