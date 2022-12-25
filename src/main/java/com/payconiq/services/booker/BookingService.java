package com.payconiq.services.booker;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.payconiq.pojo.Booker;
import com.payconiq.pojo.BookingDate;
import com.payconiq.pojo.RequestObj;
import com.payconiq.pojo.config.CommonKeys;
import com.payconiq.pojo.response.BookingRes;
import com.payconiq.pojo.response.CreateBookRes;
import com.payconiq.services.utils.BaseService;
import com.payconiq.services.utils.CommonUtils;
import com.payconiq.services.utils.CompareJson;
import com.payconiq.services.utils.RequestSender;
import io.restassured.response.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class BookingService implements BaseService {
    private static Logger logger = LoggerFactory.getLogger(BookingService.class);
    RequestObj requestObj;
    Booker booker;
    Response response;
    public int bookingId;
    Map<BaseService.Services, RequestObj> dataStore= new HashMap();

    @Override
    public void setServices(String service, Map<String, String> headers) {
        requestObj = new RequestObj();
        switch (Services.valueOf(service)) {
            case GETBOOKINGIDS:
                requestObj.setServiceName(Services.valueOf(service));
                requestObj.setServiceType(apiMethod.GET.name());
                requestObj.setUrl(CommonUtils.config.getServiceUrl());
                requestObj.setHeaders(headers);
                break;
            case CREATEBOOKING:
                requestObj.setServiceName(Services.valueOf(service));
                requestObj.setServiceType(apiMethod.POST.name());
                requestObj.setUrl(CommonUtils.config.getServiceUrl());
                requestObj.setHeaders(headers);
                requestObj.setJsonBody(CommonUtils.getStringJson(createBookingWithDefault(new Booker())));
                dataStore.put(Services.valueOf(service), requestObj);
                break;
        }
        logger.info("Api genericOperation :" + Services.valueOf(service));
    }

    @Override
    public void setServices(String service, String param, Map<String, String> headers) {
        requestObj = new RequestObj();
        switch (Services.valueOf(service)) {
            case GETBOOKINGBYID:
            case GETBOOKINGIDS:
                requestObj.setServiceName(Services.valueOf(service));
                requestObj.setServiceType(apiMethod.GET.name());
                requestObj.setUrl(CommonUtils.config.getServiceUrl()+param);
                requestObj.setHeaders(headers);
                break;
            case UPDATEBOOKING:
                requestObj.setServiceType(apiMethod.PUT.name());
                requestObj.setUrl(CommonUtils.config.getServiceUrl()+param);
                requestObj.setHeaders(headers);
                requestObj.setJsonBody(CommonUtils.getStringJson(updateBookingWithDefault(new Booker())));
                dataStore.put(Services.valueOf(service), requestObj);
                break;
            case DELETEBOOKING:
                requestObj.setServiceName(Services.valueOf(service));
                requestObj.setServiceType(apiMethod.DELETE.name());
                requestObj.setUrl(CommonUtils.config.getServiceUrl()+param);
                requestObj.setHeaders(headers);
                break;
        }
        logger.info("Api genericOperation :" + Services.valueOf(service));
    }

    @Override
    public void setServices(String service, String param, String json, Map<String, String> headers) {
        requestObj.setServiceType(apiMethod.PATCH.name());
        requestObj.setUrl(CommonUtils.config.getServiceUrl()+param);
        requestObj.setHeaders(headers);
        requestObj.setJsonBody(json);
    }


    @Override
    public Response genericServiceInvoke() {
        RequestSender requestSender = new RequestSender();
        response = requestSender.apiRequestSend(requestObj);
        if (response.getStatusCode() != 200 && response.getStatusCode() != 201) {
            logger.info("Api service :" + requestObj.getServiceName() + " returns error response :" + response.getBody().prettyPrint());
        }
        return response;
    }


    /**
     * compare the create book request object with the response
     * here adding the missing attribute from the response and create new
     * temporary response object
     * @return book creation response validation status
     */
    public boolean validateBookCreation(BaseService.Services services){

        JsonNode jsnRes = CommonUtils.getJsonFromString(response.getBody().prettyPrint());
        if(requestObj.getJsonBody() == null){
            requestObj = dataStore.get(services);
        }
        JsonNode tmpReqst = CommonUtils.getJsonFromString(requestObj.getJsonBody());

        // create booking object response for the compare
        CreateBookRes tmpRes = new CreateBookRes();
        // get booking id from response and set to the new json for json compare
        bookingId=jsnRes.get(CommonKeys.CREATE_BOOKING_RES_ID).asInt();
        tmpRes.setBookingid(bookingId);

        BookingRes bkRes = new BookingRes();
        bkRes.setFirstname(tmpReqst.get("firstname").asText());
        bkRes.setLastname(tmpReqst.get("lastname").asText());
        bkRes.setTotalprice(tmpReqst.get("totalprice").asInt());
        bkRes.setDepositpaid(tmpReqst.get("depositpaid").asBoolean());
        bkRes.setAdditionalneeds(tmpReqst.get("additionalneeds").asText());

        BookingDate bkDate = new BookingDate();
        bkDate.setCheckin(tmpReqst.findValue("checkin").asText());
        bkDate.setCheckout(tmpReqst.findValue("checkout").asText());
        bkRes.setBookingdates(bkDate);
        tmpRes.setBooking(bkRes);

        tmpReqst = CommonUtils.objectToJsonNode(tmpRes);

        logger.info("Create booking response compare", tmpReqst, jsnRes);
        return jsnRes.equals(tmpReqst);
    }

    /**
     * compare the update book request object with the response
     * @services API service type
     * @return book creation response validation status
     */
    public boolean validateBookResponse(BaseService.Services services){

        JsonNode jsnRes = CommonUtils.getJsonFromString(response.getBody().prettyPrint());
        if(requestObj.getJsonBody() == null){
            requestObj = dataStore.get(services);
        }
        JsonNode tmpReqst = CommonUtils.getJsonFromString(requestObj.getJsonBody());

        logger.info("Update booking response compare", tmpReqst, jsnRes);
        return jsnRes.equals(tmpReqst);
    }

    /**
     * validate partial updated response
     * @return
     */
    public boolean validatePartialUpdatePayload() {
       JsonNode tmpReqst = CommonUtils.getJsonFromString(requestObj.getJsonBody());
       JsonNode jsnRes = CommonUtils.getJsonFromString(response.getBody().prettyPrint());
        CompareJson requestPayload = new CompareJson(tmpReqst);
        CompareJson responsePayload = new CompareJson(jsnRes);

        logger.info("Partial update booking response compare", tmpReqst, jsnRes);
        return responsePayload.contains(requestPayload);
    }

}
