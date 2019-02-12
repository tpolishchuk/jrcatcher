package cucumber.steps.world;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;

public class LastResponseData {

    private ExtractableResponse<Response> response;

    public ExtractableResponse<Response> getResponse() {
        return response;
    }

    public void setResponse(ExtractableResponse<Response> response) {
        this.response = response;
    }
}
