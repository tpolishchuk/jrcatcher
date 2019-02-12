package cucumber.steps.api_steps;

import cucumber.api.java8.En;
import cucumber.helpers.request_helpers.EndpointsRequestHelper;
import cucumber.steps.AbstractSteps;
import cucumber.steps.world.LastResponseData;
import jrc.domain.Endpoint;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class EndpointsApiSteps extends AbstractSteps implements En {

    private EndpointsRequestHelper endpointsRequestHelper = new EndpointsRequestHelper();
    private LastResponseData lastResponseData;

    public EndpointsApiSteps(LastResponseData lastResponseData) {
        this.lastResponseData = lastResponseData;

        When("^(active|inactive) endpoint is added with path \"([^\"]*)\"$", (String state, String path) -> {
            Endpoint endpoint = new Endpoint();
            endpoint.setPath(path);
            endpoint.setActive("active".equals(state));

            endpointsRequestHelper.addEndpoint(endpoint);
        });

        Then("^the following response should be returned with (\\d+) code$", (Integer expectedCode, String expectedBody) -> {
            Integer actualCode = lastResponseData.getResponse().statusCode();
            String actualBody = lastResponseData.getResponse().body().asString();

            assertEquals(expectedCode, actualCode);
            assertEquals(expectedBody, actualBody);
        });

        Then("^the following response should be returned with (\\d+) code matching body$", (Integer expectedCode, String expectedBody) -> {
            Integer actualCode = lastResponseData.getResponse().statusCode();
            String actualBody = lastResponseData.getResponse().body().asString();
            expectedBody = expectedBody.replaceAll("\\n", "")
                                       .replaceAll("\\s+\"", "\"");

            assertEquals(expectedCode, actualCode);
            assertTrue(actualBody.matches(expectedBody));
        });
    }
}
