package cucumber.helpers.request_helpers;

import cucumber.helpers.TestConfigReader;
import org.apache.commons.lang3.StringUtils;

import java.util.Map;

public abstract class AbstractRequestHelper {

    protected static final String BASIC_URL = TestConfigReader.getInstance().getBasicUrl();

    protected String convertMapToUrlencodedBody(Map<String, String> params) {
        String body = StringUtils.EMPTY;

        for (Map.Entry param : params.entrySet()) {
            body += String.format("%s=%s&", param.getKey(), param.getValue());
        }

        return body;
    }
}
