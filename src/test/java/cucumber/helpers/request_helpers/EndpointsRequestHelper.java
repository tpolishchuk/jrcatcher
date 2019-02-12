package cucumber.helpers.request_helpers;

import io.restassured.http.ContentType;
import jrc.domain.Endpoint;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class EndpointsRequestHelper extends AbstractRequestHelper {

    public void addEndpoint(Endpoint endpoint) {
        Map<String, String> params = new HashMap<>();
        params.put("path", endpoint.getPath());
        params.put("active", endpoint.getActive().toString());

        given().contentType(ContentType.URLENC)
               .body(convertMapToUrlencodedBody(params))
               .when().post(BASIC_URL + "/endpoints-manager")
               .then().statusCode(302);
    }
}
