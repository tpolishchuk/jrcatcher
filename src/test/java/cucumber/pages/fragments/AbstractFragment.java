package cucumber.pages.fragments;

import cucumber.pages.AbstractWebComponent;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public abstract class AbstractFragment extends AbstractWebComponent {

    private WebElement rootElement;

    public AbstractFragment(WebDriver driver, WebElement rootElement) {
        super(driver);
        setRootElement(rootElement);
    }

    protected WebElement getRootElement() {
        return rootElement;
    }

    protected void setRootElement(WebElement rootElement) {
        this.rootElement = rootElement;
    }

    protected WebElement getChildElement(By childElementLocator) {
        webElementWaitHelper.waitToBePresent(rootElement, childElementLocator);
        return rootElement.findElement(childElementLocator);
    }
}
