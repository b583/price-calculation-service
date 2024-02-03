package b583.pricecalculationservice.web;

import b583.pricecalculationservice.service.PriceCalculationService;
import com.codahale.metrics.annotation.Timed;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

import java.util.UUID;

@Path("/v1/price")
@Produces(MediaType.APPLICATION_JSON)
@Singleton
public class PriceCalculationResource {

    @Inject
    private PriceCalculationService priceCalculationService;

    @GET
    @Path("{productUuid}")
    @Timed
    public PriceCalculationDTO calculatePrice(@NotNull @NotBlank @PathParam("productUuid") String productUuid,
                                     @NotNull @Positive @QueryParam("amount") Integer amount) {

        return new PriceCalculationDTO(priceCalculationService.calculatePrice(stringToUuid(productUuid), amount));
    }

    private UUID stringToUuid(String uuid) {
        try {
            return UUID.fromString(uuid);
        } catch (IllegalArgumentException e) {
            // Return 404 in case invalid UUID is given
            throw new NotFoundException();
        }
    }

}
