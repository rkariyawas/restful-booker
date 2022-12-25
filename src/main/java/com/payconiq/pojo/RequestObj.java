package com.payconiq.pojo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.payconiq.services.utils.BaseService;
import lombok.Data;

import java.util.Map;
import java.util.TreeMap;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class RequestObj {

    private BaseService.Services serviceName;
    private String url;
    private String serviceType;
    private String jsonBody;
    private Map<String, String> headers = new TreeMap<>();

}
