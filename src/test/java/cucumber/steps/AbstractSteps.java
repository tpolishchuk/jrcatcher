package cucumber.steps;

import cucumber.helpers.WebDriverSessionManager;
import org.openqa.selenium.WebDriver;

public abstract class AbstractSteps {

    public WebDriver getDriver() {
        return WebDriverSessionManager.getInstance().getDriver();
    }

    public void quitDriver() {
        WebDriverSessionManager.getInstance().quitDriver();
    }
}
