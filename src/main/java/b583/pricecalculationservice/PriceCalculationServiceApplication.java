package b583.pricecalculationservice;

import b583.pricecalculationservice.config.PriceCalculationServiceConfiguration;
import b583.pricecalculationservice.web.PriceCalculationResource;
import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import io.dropwizard.core.Application;
import io.dropwizard.core.setup.Environment;

public class PriceCalculationServiceApplication extends Application<PriceCalculationServiceConfiguration> {

    public static void main(String[] args) throws Exception {
        new PriceCalculationServiceApplication().run(args);
    }

    @Override
    public void run(PriceCalculationServiceConfiguration priceCalculationServiceConfiguration, Environment environment) throws Exception {

        // Setup dependency injection
        final var injector = Guice.createInjector(
                new AbstractModule() {
                    @Override
                    protected void configure() {
                        bind(PriceCalculationServiceConfiguration.class).toInstance(priceCalculationServiceConfiguration);
                    }
                }
        );

        // Register web resources
        environment.jersey().register(injector.getInstance(PriceCalculationResource.class));
    }
}