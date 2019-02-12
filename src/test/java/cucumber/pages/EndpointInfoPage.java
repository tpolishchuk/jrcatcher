package cucumber.pages;

import org.openqa.selenium.WebDriver;

public class EndpointInfoPage extends AbstractJrcPage {

    private int endpointId;
    private String url;

    public EndpointInfoPage(WebDriver driver, int endpointId) {
        super(driver);
        this.endpointId = endpointId;
    }

    @Override
    protected void initUrl() {
        url = BASIC_URL + "/" + String.valueOf(endpointId);
    }

    @Override
    public void openAndVerify() {
        openAndVerify(url);
    }
}
