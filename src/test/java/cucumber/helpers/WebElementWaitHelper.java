package cucumber.helpers;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import static org.openqa.selenium.support.ui.ExpectedConditions.*;

public class WebElementWaitHelper {

    private static final long MAX_WAIT_TIMEOUT = TestConfigReader.getInstance()
                                                                 .getWebElementWaitTimeoutSeconds();

    private WebDriver driver;
    private WebDriverWait wait;

    public WebElementWaitHelper(WebDriver driver) {
        setDriver(driver);
        setWait(new WebDriverWait(driver, MAX_WAIT_TIMEOUT));
    }

    public void setDriver(WebDriver driver) {
        this.driver = driver;
    }

    public void setWait(WebDriverWait wait) {
        this.wait = wait;
    }

    public void waitToBeVisible(WebElement element) {
        wait.until(visibilityOf(element));
    }

    public void waitToBeHidden(WebElement element) {
        wait.until(invisibilityOf(element));
    }

    public void waitToBePresent(WebElement element, By childLocator) {
        wait.until(presenceOfNestedElementLocatedBy(element, childLocator));
    }
}
