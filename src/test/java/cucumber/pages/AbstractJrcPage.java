package cucumber.pages;

import cucumber.helpers.TestConfigReader;
import org.openqa.selenium.WebDriver;

public abstract class AbstractJrcPage extends AbstractWebComponent {

    protected static final String BASIC_URL = TestConfigReader.getInstance().getBasicUrl();

    public AbstractJrcPage(WebDriver driver) {
        super(driver);
        initUrl();
    }

    protected abstract void initUrl();

    public abstract void openAndVerify();

    protected void openAndVerify(String url) {
        driver.get(url);

        String currentUrl = driver.getCurrentUrl();

        if (!currentUrl.startsWith(url)) {
            String errorMessagePattern = "Cannot open page '%s'. Current url: %s";
            throw new RuntimeException(String.format(errorMessagePattern, url, currentUrl));
        }
    }
}
