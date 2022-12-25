package Runner;

import com.payconiq.services.utils.CommonUtils;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(publish = true,
        plugin = {"com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:"
        },
        features = {"src/test/features"},
        glue = {"com.payconiq.steps.auth", "com.payconiq.steps.booker"},
        tags = "@regression or @bookingApis and not @ignore")

public class TestRunner {

    @BeforeClass
    public static void setConfigs() {
        CommonUtils.setConfigJson();
    }
}
