package com.payconiq.services.utils;

import com.payconiq.pojo.RequestObj;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static io.restassured.RestAssured.given;

public class RequestSender {

    private static Logger logger = LoggerFactory.getLogger(RequestSender.class);

    /**
     * Can use this method to send REST requests and can extend
     * the switch cases as per the needs
     * @param requestObj has all the request parameters
     * @return Response object which returns as the response
     */
    public Response apiRequestSend(RequestObj requestObj){
        Response response = null;
        switch (requestObj.getServiceType()){
            case "GET":
                response= given()
                        .headers(requestObj.getHeaders())
                        .get(requestObj.getUrl())
                        .then().using().extract().response();
                break;
            case "POST":
                response= given().contentType(ContentType.JSON).body(requestObj.getJsonBody())
                        .headers(requestObj.getHeaders())
                        .post(requestObj.getUrl())
                        .then().using().extract().response();
                break;
            case "PUT":
                response= given().contentType(ContentType.JSON).body(requestObj.getJsonBody())
                        .headers(requestObj.getHeaders())
                        .put(requestObj.getUrl())
                        .then().using().extract().response();
                break;
            case "PATCH":
                response= given().contentType(ContentType.JSON).body(requestObj.getJsonBody())
                        .headers(requestObj.getHeaders())
                        .patch(requestObj.getUrl())
                        .then().using().extract().response();
                break;
            case "DELETE":
                response= given()
                        .headers(requestObj.getHeaders())
                        .delete(requestObj.getUrl())
                        .then().using().extract().response();
                break;
        }

        logger.info("API service is :"+requestObj.getServiceName()+" and response code is :"+response.getStatusCode());
        return response;
    }
}
