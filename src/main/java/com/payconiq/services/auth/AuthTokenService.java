package com.payconiq.services.auth;

import com.payconiq.pojo.RequestObj;
import com.payconiq.pojo.config.AuthConf;
import com.payconiq.services.utils.BaseService;
import com.payconiq.services.utils.CommonUtils;
import com.payconiq.services.utils.RequestSender;
import io.restassured.response.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

public class AuthTokenService implements BaseService{

    private static Logger logger = LoggerFactory.getLogger(AuthTokenService.class);
    RequestObj requestObj;

    @Override
    public void setServices(String service, Map<String, String> headers) {
    }

    @Override
    public void setServices(String service, String param, Map<String, String> headers) {
        requestObj = new RequestObj();
        requestObj.setUrl(CommonUtils.config.getServiceUrl().replace("/booking", param));
        requestObj.setHeaders(headers);
        requestObj.setJsonBody(CommonUtils.getStringJson(CommonUtils.objectToJsonNode((AuthConf)CommonUtils.config.getAuth())));
        requestObj.setServiceType(apiMethod.POST.name());
        requestObj.setServiceName(Services.valueOf(service));
    }

    @Override
    public void setServices(String service, String param, String json, Map<String, String> headers) {

    }

    @Override
    public Response genericServiceInvoke() {
        RequestSender requestSender = new RequestSender();
        Response response = requestSender.apiRequestSend(requestObj);
        if (response.getStatusCode() != 200) {
            logger.error("Auth token api service :" + requestObj.getServiceName() + " returns error response :" + response.getBody().prettyPrint());
        }
        token.setToken(CommonUtils.getJsonFromString(response.getBody().prettyPrint()).get("token").asText());
        return response;
    }

}
