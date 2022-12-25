package com.payconiq.steps.auth;

import com.payconiq.services.auth.AuthTokenService;
import com.payconiq.services.utils.BaseService;
import com.payconiq.services.utils.CommonUtils;
import io.cucumber.java.en.Then;
import io.restassured.response.Response;
import org.junit.Assert;

import java.util.Map;

public class CreateAuthSteps {
    AuthTokenService authService;

    @Then("User get the valid token")
    public void user_get_the_valid_token() {
        authService = new AuthTokenService();
        CommonUtils utils = new CommonUtils();
        Map<String, String> mapper = utils.serviceHeader(null);
        authService.setServices(BaseService.Services.CREATEAUTH.name(), "/auth", mapper);
        Response response = authService.genericServiceInvoke();

        Assert.assertTrue("Auth token service returns success", response.getStatusCode() == 200);
    }
}
