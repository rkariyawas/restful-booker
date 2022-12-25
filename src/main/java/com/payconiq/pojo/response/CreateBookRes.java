package com.payconiq.pojo.response;

import lombok.Data;

@Data
public class CreateBookRes {
    private int bookingid;
    private BookingRes booking;
}
