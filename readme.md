# Price Tracker Application
A spring boot application that allows users to set price alerts for products and get notified when prices drop.

# Tech Stack:

- Java 21
- Spring Boot 3.2.0
- Spring Data JPA
- H2 Database
- Liquibase
- JUnit 5

# Run the application:
The application will start on http://localhost:8989/api/price-alerts

## Sample request and response
POST
```bash
 ** Request **
{
    "productUrl":"https://www.apple.com/iphone-15-pro/",
    "userId":"1",
    "frequency":"midnight",
    "desiredPrice":"22"
}

 ** Success Response **
{
    "message": "Price alert has been successfully set! You will be notified when the price drops to or below your desired price.",
    "alertId": 6,
    "productUrl": "https://www.apple.com/iphone-15-pro/",
    "desiredPrice": 22,
    "frequency": "midnight",
    "createdAt": "2025-08-07T01:32:10.427661"
}
