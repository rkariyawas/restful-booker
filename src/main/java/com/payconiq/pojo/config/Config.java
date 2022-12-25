package com.payconiq.pojo.config;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

@Data
public class Config {

    private String serviceUrl;
    private AuthConf auth;

}
