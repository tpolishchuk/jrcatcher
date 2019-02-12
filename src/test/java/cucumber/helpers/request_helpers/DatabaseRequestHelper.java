package cucumber.helpers.request_helpers;

import static io.restassured.RestAssured.when;

public class DatabaseRequestHelper extends AbstractRequestHelper {

    public void clearDatabase() {
        when().post(BASIC_URL + "/clear-database")
              .then().statusCode(302);
    }
}
