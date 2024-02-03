package b583.pricecalculationservice;

import io.dropwizard.core.Application;
import io.dropwizard.core.setup.Environment;

public class PriceCalculationServiceApplication extends Application<PriceCalculationServiceConfiguration> {

    public static void main(String[] args) throws Exception {
        new PriceCalculationServiceApplication().run(args);
    }

    @Override
    public void run(PriceCalculationServiceConfiguration priceCalculationServiceConfiguration, Environment environment) throws Exception {
        System.out.println(priceCalculationServiceConfiguration.getDummy());
    }
}