package com.payconiq.steps.booker;

import com.fasterxml.jackson.databind.JsonNode;
import com.payconiq.pojo.config.CommonKeys;
import com.payconiq.services.booker.BookingService;
import com.payconiq.services.utils.BaseService;
import com.payconiq.services.utils.CommonUtils;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class BookerSteps {
    private static Logger logger = LoggerFactory.getLogger(BookerSteps.class);
    BookingService bkngService;
    Response response;
    CommonUtils utils = new CommonUtils();
    Map<String, String> headers;

    @Given("user prepare the generic request for {string}")
    public void user_prepare_the_generic_request_for(String serviceName) {
        if (bkngService == null) {
            bkngService = new BookingService();
        }
        if (serviceName.equals(BaseService.Services.UPDATEBOOKING.name()) || serviceName.equals(BaseService.Services.DELETEBOOKING.name())) {
            headers = utils.serviceHeader(BaseService.token.getToken());
            bkngService.setServices(serviceName, "/" + bkngService.bookingId, headers);
        } else {
            headers = utils.serviceHeader(null);
            bkngService.setServices(serviceName, headers);
        }

    }

    @Given("user prepare the generic request for {string} by param {string}")
    public void user_prepare_the_generic_request_for_by_param(String serviceName, String param) {
        if (bkngService == null) {
            bkngService = new BookingService();
        }
        headers = utils.serviceHeader(null);
        bkngService.setServices(serviceName, "?"+param, headers);
    }

    @When("user invoke the request for {string}")
    public void user_invoke_the_request_for(String service) {
        response = bkngService.genericServiceInvoke();

        if (service.equals(BaseService.Services.DELETEBOOKING.name())) {
            Assert.assertEquals("Error in api invocation :" + service, 201, response.getStatusCode());
        } else {
            Assert.assertEquals("Error in api invocation :" + service, 200, response.getStatusCode());
        }

    }

    @Then("user should get the list for bookingIds")
    public void user_should_get_the_list_for_booking_ids() {
        JSONArray jsonBookingListArray = new JSONArray((String) new JSONArray(Collections.singleton(response.getBody().prettyPrint())).get(0));
        for (int i = 0; i < jsonBookingListArray.length(); i++) {
            JSONObject explrObject = jsonBookingListArray.getJSONObject(i);
            Assert.assertTrue("Invalid booking id returns from the booking Id list :" + explrObject.get("bookingid"), Integer.class.isInstance(explrObject.get("bookingid")));
        }

    }

    @Given("user prepare the request for {string} using id {string}")
    public void user_prepare_the_request_for_using_id(String serviceName, String bookingId) {
        bkngService = new BookingService();
        Map<String, String> mapper = utils.serviceHeader(null);
        bkngService.setServices(serviceName, "/" + bookingId, mapper);
    }

    @Then("user should get payload {string}")
    public void user_should_get_payload(String payload) {
        try {
            JsonNode expected = CommonUtils.getJsonFromString(String.valueOf((CommonKeys.class.getField(payload)).get(payload)));
            JsonNode actual = CommonUtils.getJsonFromString(response.getBody().prettyPrint());
            Assert.assertEquals("Error in api response validation", expected, actual);

        } catch (NoSuchFieldException | IllegalAccessException e) {
            logger.error("Api service payload assert:", e.getMessage(), e.getStackTrace());
        }
    }

    @Then("user should get the generic booking success response for {string}")
    public void user_should_get_the_generic_booking_success_response_for(String serviceName) {
        if(serviceName.equals(BaseService.Services.CREATEBOOKING.name())) {
            Assert.assertTrue("Error while validating booking response :" + serviceName, bkngService.validateBookCreation(BaseService.Services.valueOf(serviceName)));
        } else {
            Assert.assertTrue("Error while validating create booking response :"+ serviceName, bkngService.validateBookResponse(BaseService.Services.valueOf(serviceName)));
        }
        }

    @Then("user should validate the response with data store for {string} request")
    public void user_should_validate_the_response_with_data_store_for_request(String serviceName) {
        Assert.assertTrue("Error while validating create booking response :"+ serviceName, bkngService.validateBookResponse(BaseService.Services.valueOf(serviceName)));
    }

    @Then("user prepare the partial update request with payload {string}")
    public void user_prepare_the_partial_update_request_with_payload(String payload) {
        String service = BaseService.Services.PARTIALUPDATEBOOKING.name();
        String jsonStr = payload.replace('\'', '"');
        headers = utils.serviceHeader(BaseService.token.getToken());
        bkngService.setServices(service, "/" + bkngService.bookingId, jsonStr, headers);

    }

    @Then("verify updated values in the payload response")
    public void verify_updated_values_in_the_payload_response() {
        Assert.assertTrue("Error while validating partial updated response :", bkngService.validatePartialUpdatePayload());
    }

    @Then("verify able to delete successfully")
    public void verify_able_to_delete_successfully() {
        Assert.assertEquals("Error in delete booking api response :", 201, response.getStatusCode());
    }

    @Then("validate booking id is available in the booking Id List")
    public void validate_booking_id_is_available_in_the_booking_id_list() {

        headers = utils.serviceHeader(null);
        bkngService.setServices(BaseService.Services.GETBOOKINGIDS.name(), headers);
        response = bkngService.genericServiceInvoke();
        JSONArray jsonBookingListArray = new JSONArray((String) new JSONArray(Collections.singleton(response.getBody().prettyPrint())).get(0));
        boolean isFound=false;
        for (int i = 0; i < jsonBookingListArray.length(); i++) {
            JSONObject explrObject = jsonBookingListArray.getJSONObject(i);
             if(bkngService.bookingId == (Integer) explrObject.get("bookingid")){
                isFound = true;
                break;
            }
        }
        Assert.assertTrue("Booking id returns from the booking Id list :" + bkngService.bookingId, isFound);
    }

    @When("user prepare the request for {string} using bookingId")
    public void user_prepare_the_request_for_using_booking_id(String service) {
        headers = utils.serviceHeader(BaseService.token.getToken());
        bkngService.setServices(service, "/" + bkngService.bookingId, headers);
    }

    @Then("verify booking details unavailable for the booking Id")
    public void verify_booking_details_unavailable_for_the_booking_id() {
        response = bkngService.genericServiceInvoke();
        Assert.assertEquals("Error in delete booking or get booking api due to getBooking returns status code for deleted booking:", 404, response.getStatusCode());
    }
}
