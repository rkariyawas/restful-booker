package com.payconiq.pojo;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

@Data
public class Token {
    @Setter(AccessLevel.NONE)
    private String token;

    public void setToken(String token) {
        this.token = "token="+token;
    }
}
