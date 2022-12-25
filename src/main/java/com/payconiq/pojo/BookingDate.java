package com.payconiq.pojo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class BookingDate {
    private String checkin;
    private String checkout;
}
