package cucumber.helpers.request_helpers;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;

import java.util.Collections;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class EmulatedRequestHelper extends AbstractRequestHelper {

    public ExtractableResponse<Response> sendRequest(String method,
                                                     String relativeUrl,
                                                     String body) {
        return sendRequest(method, relativeUrl, body, Collections.emptyMap(), Collections.emptyMap());
    }

    public ExtractableResponse<Response> sendRequest(String method,
                                                     String relativeUrl,
                                                     String body,
                                                     Map<String, String> headers,
                                                     Map<String, String> params) {
        return given().body(body)
                      .headers(headers)
                      .params(params)
                      .when().request(method, BASIC_URL + relativeUrl)
                      .then().extract();
    }
}
