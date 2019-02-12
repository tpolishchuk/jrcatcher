package cucumber.pages;

import cucumber.helpers.WebElementWaitHelper;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

public abstract class AbstractWebComponent {

    protected WebElementWaitHelper webElementWaitHelper;
    protected WebDriver driver;

    public AbstractWebComponent(WebDriver driver) {
        setDriver(driver);
        setWebElementWaitHelper(new WebElementWaitHelper(driver));
        PageFactory.initElements(driver, this);
    }

    private void setDriver(WebDriver driver) {
        this.driver = driver;
    }

    private void setWebElementWaitHelper(WebElementWaitHelper webElementWaitHelper) {
        this.webElementWaitHelper = webElementWaitHelper;
    }
}
