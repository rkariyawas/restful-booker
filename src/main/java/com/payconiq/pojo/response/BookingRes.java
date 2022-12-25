package com.payconiq.pojo.response;

import com.payconiq.pojo.BookingDate;
import lombok.Data;

@Data
public class BookingRes {
    private String firstname;
    private String lastname;
    private int totalprice;
    private boolean depositpaid;
    private BookingDate bookingdates;
    private String additionalneeds;
}
