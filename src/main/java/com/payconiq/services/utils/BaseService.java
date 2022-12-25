package com.payconiq.services.utils;


import com.payconiq.pojo.Booker;
import com.payconiq.pojo.BookingDate;
import com.payconiq.pojo.Token;
import io.restassured.response.Response;

import java.util.Map;

public interface BaseService {

    Token token = new Token();


    /**
     * set services use for prepare the service requests
     * @param service service name
     * @param headers request headers
     */
    void setServices(String service, Map<String, String> headers);

    /**
     *
     * set services use for prepare the service requests
     * @param service service name
     * @param headers request headers
     * @param param query and path parameters
     */
    void setServices(String service, String param, Map<String, String> headers);

    /**
     * set services use for prepare the service requests
     * @param service service name
     * @param param query and path parameters
     * @param json json payload to be sent
     * @param headers request headers
     */
    void setServices(String service, String param, String json, Map<String, String> headers);

    /**
     * generic method for api invocation
     * @return
     */
    Response genericServiceInvoke();

    enum Services {
      CREATEAUTH, GETBOOKINGIDS, CREATEBOOKING, GETBOOKINGBYID, UPDATEBOOKING, PARTIALUPDATEBOOKING, DELETEBOOKING
    }

    enum apiMethod {
        GET, POST, PUT, PATCH, DELETE
    }

    /**
     * default create booking request for the test suite
     * @param booker
     * @return object for the create booking request
     */
    default Booker createBookingWithDefault(Booker booker){
        booker.setFirstname("Kariyawasam");
        booker.setLastname("Gamage");
        booker.setTotalprice(320);
        booker.setDepositpaid(true);
        BookingDate bkDate = new BookingDate();
        bkDate.setCheckin("2022-12-01");
        bkDate.setCheckout("2022-12-03");
        booker.setBookingdates(bkDate);
        booker.setAdditionalneeds("sea view");
        return booker;
    }

    /**
     * default update booking request for the test suite
     * @param booker
     * @return object for the update booking request
     */
    default Booker updateBookingWithDefault(Booker booker){
        booker.setFirstname("Mark");
        booker.setLastname("Luvis");
        booker.setTotalprice(7000);
        booker.setDepositpaid(true);
        BookingDate bkDate = new BookingDate();
        bkDate.setCheckin("2022-12-08");
        bkDate.setCheckout("2022-12-10");
        booker.setBookingdates(bkDate);
        booker.setAdditionalneeds("private pool");
        return booker;
    }

}
