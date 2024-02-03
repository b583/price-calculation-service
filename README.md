# Price Calculation Service

Small service with functionality to calculate product price based on configured base price and promotions.  
Two kinds of promotions are allowed:
- Percent Off Promotion
  - A static percent is subtracted from price, regardless of amount of ordered items.
- Count Based Promotion
  - With every ordered item (of the same kind) applied percent off increases until limit is reached.
  - Note that validation of proper (low to high) percent order is not implemented.

If no promotion is configured for a product, then price will be calculated without discount.

Promotions must have a start and end time configured in order to guarantee a consistent experience for buyers.

### Issues
I believe that the approach to pass the configuration via environment variable would be painful in the real world application,
requiring a redeployment on every promotion change. Also, environment variables don't really play well with whitespace and newlines, which could be frustrating for operators.

As remedy, an upstream product cache component should be introduced from which price-calculation-service could read product and promotion data.

### Nice to have missing things

- Swagger endpoint.
- Upstream product cache component mentioned in the Issues section.
- Configuration validation should be tested.
- More test cases should be added to ensure that edge-cases are covered.
- Request UUID should be sanitized, to reduce attack risk.
- Docker image should be built via Maven and version not hardcoded for future releases.

## How to run

### Build and package
```shell
mvn package
```

### Configuration
Example hardcoded configuration for one product with two types of promotions.  
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
            percentOffSequence: [ 3, 5, 7, 10, 15 ]
          }
        ]
      }
  }
```
#### Export Products section as environment variable
Set below (flattened products section from above) as `PRODUCTS_CONFIGURATION` (Operating System dependent)
```shell
  {     d651b6a0-6753-43a3-be55-c7917dce55d6:       {         basePrice: 199.99,         percentOffPromotions: [           {             promotionStartTime: '2024-02-03T11:00:00Z',             promotionEndTime: '2024-02-04T23:00:00Z',             percentOff: 4           }         ],         countBasedPromotions: [           {             promotionStartTime: '2024-02-03T11:00:00Z',             promotionEndTime: '2024-02-04T23:00:00Z',             percentOffSequence: [ 3, 5, 7, 10, 15 ]           }         ]       }   }
```

### Run

#### Java
```shell
java -jar target/price-calculation-service-1.0-SNAPSHOT-jar-with-dependencies.jar server config.yaml 
```
If you prefer not to use environment variables, create a local configuration file next to `config.yaml` and pass its name to arguments instead.

#### Docker

##### Build docker container
Remember to package first!
```shell
docker image build -t price-calculation-service:latest .
```
##### Run (PowerShell)
```shell
docker run -p 8080:8080 -e PRODUCTS_CONFIGURATION=$env:PRODUCTS_CONFIGURATION price-calculation-service:latest
```
##### Run (bash)
```shell
docker run -p 8080:8080 -e PRODUCTS_CONFIGURATION=$PRODUCTS_CONFIGURATION price-calculation-service:latest
```

### Validate
Send a simple request to validate that the application is working
```shell
curl http://localhost:8080/v1/price/d651b6a0-6753-43a3-be55-c7917dce55d6?amount=1


StatusCode        : 200
StatusDescription : OK
Content           : {"price":191.99}
RawContent        : HTTP/1.1 200 OK
                    Vary: Accept-Encoding
                    Content-Length: 16
                    Content-Type: application/json
                    Date: Sat, 03 Feb 2024 17:07:00 GMT

                    {"price":191.99}
Forms             : {}
Headers           : {[Vary, Accept-Encoding], [Content-Length, 16], [Content-Type, application/json], [Date, Sat, 03 Feb 2024 17:07:00 GMT]}
Images            : {}
InputFields       : {}
Links             : {}
ParsedHtml        : System.__ComObject
RawContentLength  : 16
```