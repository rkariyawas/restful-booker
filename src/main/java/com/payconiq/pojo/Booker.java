package com.payconiq.pojo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class Booker {
    private String firstname;
    private String lastname;
    private int totalprice;
    private boolean depositpaid;
    private String additionalneeds;
    private BookingDate bookingdates;
}
