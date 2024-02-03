package b583.pricecalculationservice.web;

import com.codahale.metrics.annotation.Timed;
import com.google.inject.Singleton;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;

@Path("/v1/price")
@Produces(MediaType.APPLICATION_JSON)
@Singleton
public class PriceCalculationResource {

    @GET
    @Path("{productUuid}")
    @Timed
    public PriceCalculationDTO calculatePrice(@NotNull @NotBlank @PathParam("productUuid") String productUuid,
                                     @NotNull @Positive @QueryParam("amount") BigInteger amount) {

        // TODO implement business logic
        return new PriceCalculationDTO(BigDecimal.valueOf(1.116).setScale(2, RoundingMode.DOWN));
    }

}
