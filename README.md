## restful-booker ##
This is the test automation framework for booking api services

-- In this test frame work I used below tech stack --

* Cucmber - front layer for the business users
* Junit - test layer
* REST - api service handler
* Java - primary language
* Jackson - Json mapping
* Lombok - POJO Object handling
* slf4j - logs
* extentreports - reporting

## Api info ##
api spec can find through:
http://restful-booker.herokuapp.com/apidoc/index.html#api-Booking-GetBookings


postman collection : Please request access key seperatly
https://api.postman.com/collections/9140958-aa13da56-5699-460b-a947-ef667e9be589?access_key=

###### Prerequisites:
Java 11
Maven > 3.8.1

Compile with Maven:

run from project root: `mvn clean compile`

Run tests:

run `mvn test`

###### Reports

![image](https://user-images.githubusercontent.com/60919528/209482517-b011b7cc-0a7a-4a8e-8666-55257d2faadc.png)



