package cucumber.pages.fragments;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class EndpointActionRowFragment extends AbstractFragment {

    private By endpointPath = By.cssSelector(".endpoint-path");
    private By endpointActiveState = By.cssSelector(".endpoint-active-state");
    private By pauseListeningButton = By.cssSelector(".pause-endpoint-listening-button");
    private By resumeListeningButton = By.cssSelector(".resume-endpoint-listening-button");
    private By viewEndpointInfoButton = By.cssSelector(".view-endpoint-info-button");
    private By deleteEndpointButton = By.cssSelector(".delete-endpoint-button");

    public EndpointActionRowFragment(WebDriver driver, WebElement rootElement) {
        super(driver, rootElement);
    }

    public String getEndpointPath() {
        return getChildElement(endpointPath).getText();
    }

    public boolean isEndpointActive() {
        return Boolean.parseBoolean(getChildElement(endpointActiveState).getText());
    }

    public void pressPauseListeningButton() {
        getChildElement(pauseListeningButton).click();
    }

    public boolean isPauseListeningButtonShown() {
        return getChildElement(pauseListeningButton).isDisplayed();
    }

    public void pressResumeListeningButton() {
        getChildElement(resumeListeningButton).click();
    }

    public boolean isPressResumeListeningButtonSown() {
        return getChildElement(resumeListeningButton).isDisplayed();
    }

    public void pressViewEndpointInfoButton() {
        getChildElement(viewEndpointInfoButton).click();
    }

    public void pressDeleteEndpointButton() {
        getChildElement(deleteEndpointButton).click();
    }
}
