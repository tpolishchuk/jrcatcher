package cucumber.steps.api_steps;

import cucumber.api.java8.En;
import cucumber.helpers.request_helpers.EmulatedRequestHelper;
import cucumber.steps.AbstractSteps;
import cucumber.steps.world.LastResponseData;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;

public class EmulatedRequestSteps extends AbstractSteps implements En {

    private EmulatedRequestHelper emulatedRequestHelper = new EmulatedRequestHelper();
    private LastResponseData lastResponseData;

    public EmulatedRequestSteps(LastResponseData lastResponseData) {
        this.lastResponseData = lastResponseData;

        When("^(\\w+) request is sent to \"([^\"]*)\" with the following body$", (String method, String url, String body) -> {
            ExtractableResponse<Response> response = emulatedRequestHelper.sendRequest(method,
                                                                                       url,
                                                                                       body);
            lastResponseData.setResponse(response);
        });
    }
}
