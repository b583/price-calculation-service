# Price Calculation Service

## How to run

### Build and package
```shell
mvn package
```
### Configure

#### Create yaml configuration
Example configuration for one product with two types of promotions.  
Please mind the `promotionStartTime` and `promotionEndTime` properties.  
Service operates on UTC time.
```yaml
products:
  {
    d651b6a0-6753-43a3-be55-c7917dce55d6:
      {
        basePrice: 199.99,
        percentOffPromotions: [
            {
              promotionStartTime: '2024-02-03T11:00:00Z',
              promotionEndTime: '2024-02-04T23:00:00Z',
              percentOff: 4
            }
          ],
        countBasedPromotions: [
          {
            promotionStartTime: '2024-02-03T11:00:00Z',
            promotionEndTime: '2024-02-04T23:00:00Z',
            # Increase the promotion with amount of bought products.
            # Note that currently the order of sequence (low to high) is not validated.
            percentOffSequence: [ 3, 5, 7, 10, 15 ]
          }
        ]
      },
```
### Run

#### Java
```shell
java -jar target/price-calculation-service-1.0-SNAPSHOT-jar-with-dependencies.jar server config.yaml 
```
Create a local configuration file next to `config.yaml` and pass its name to arguments instead.

#### Docker

##### Build docker container
```shell
docker image build -t price-calculation-service:latest .
```
##### Run
TODO env vars
```shell
docker run price-calculation-service:latest
```