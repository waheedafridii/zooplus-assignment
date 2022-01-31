# Crypto Currency Java Web Application Using Thymeleaf

## Project Details:
Simple Implementation of CryptoCurrencies Exchange Rate using SpringBoot Thymeleaf.

### Way of Implementation (Assumption)

* Use Three External Calls in this projects
  * CoinAPI:
    * Fetch The cryptoCurrency to get list of all crypto.
    * Get Exchange Rate by providing currency Symbol i.e(USD for America)
  * Ip-Api:
    * Fetch the currency symbol against provided IP Address
* Add Cache Configuration to cache cryptoCurrencies when first time project run to avoid delay when page load every time.
* Add Seprate Gateway Classes for both CoinAPI and Ip-API Gateway.
* Upto 90 % tests are covered and success.
  

## How to Run

* Clone this repository
* You can build the project and run the tests by running ```mvn clean install```
* Once successfully built, you can run the service by one of these two methods:
```
    mvn spring-boot:run
```
....and navigate your browser to  http://localhost:8080/

Once the application runs you should see something like this

```
2022-01-31 22:26:47.754  INFO 13800 --- [           main] o.s.b.w.embedded.tomcat.TomcatWebServer  : Tomcat started on port(s): 8080 (http) with context path ''
2022-01-31 22:26:47.773  INFO 13800 --- [           main] c.c.c.CurrencyConverterApplication       : Started CurrencyConverterApplication in 7.899 seconds (JVM running for 9.863)
```

Enjoy !!
