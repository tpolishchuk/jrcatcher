package cucumber.pages;

import cucumber.pages.fragments.EndpointActionRowFragment;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.ArrayList;
import java.util.List;

public class RootPage extends AbstractJrcPage {

    @FindBy(id = "add-listener-button")
    private WebElement addListenerButton;

    @FindBy(id = "add-listener-form")
    private WebElement addListenerForm;

    @FindBy(css = "#add-listener-form #path")
    private WebElement addListenerPathInput;

    @FindBy(css = "#add-listener-form [type='submit']")
    private WebElement addListenerFormSubmitButton;

    @FindBy(css = ".endpoints-list-table .endpoint-action-row")
    private List<WebElement> endpointActionRowElements;

    @FindBy(css = "#errors")
    private WebElement errorsAlert;

    @FindBy(css = "#errors li")
    private List<WebElement> errorMessages;

    private String url;

    public RootPage(WebDriver driver) {
        super(driver);
    }

    @Override
    protected void initUrl() {
        url = BASIC_URL;
    }

    @Override
    public void openAndVerify() {
        openAndVerify(url);
    }

    public void pressAddListenerButton() {
        addListenerButton.click();
    }

    public boolean isAddListenerFormDisplayed() {
        webElementWaitHelper.waitToBeVisible(addListenerForm);
        return addListenerForm.isDisplayed();
    }

    public boolean isAddListenerFormHidden() {
        webElementWaitHelper.waitToBeHidden(addListenerForm);
        return !addListenerForm.isDisplayed();
    }

    public void insertEndpointPath(String path) {
        addListenerPathInput.clear();
        addListenerPathInput.sendKeys(path);
    }

    public void submitAddListenerForm() {
        addListenerFormSubmitButton.click();
    }

    private List<EndpointActionRowFragment> getAllEndpointActionRows() {
        List<EndpointActionRowFragment> endpointActionRows = new ArrayList<>();

        for (WebElement endpointActionRowElement : endpointActionRowElements) {
            EndpointActionRowFragment endpointActionRowFragment =
                    new EndpointActionRowFragment(driver, endpointActionRowElement);
            endpointActionRows.add(endpointActionRowFragment);
        }

        return endpointActionRows;
    }

    public EndpointActionRowFragment getEndpointActionRow(String path) {
        for (EndpointActionRowFragment endpointActionRow : getAllEndpointActionRows()) {
            if (endpointActionRow.getEndpointPath().endsWith(path)) {
                return endpointActionRow;
            }
        }

        throw new IllegalStateException("Cannot find an endpoint with path " + path);
    }

    public boolean isErrorsAlertDisplayed() {
        webElementWaitHelper.waitToBeVisible(errorsAlert);
        return errorsAlert.isDisplayed();
    }

    public List<String> getAllErrors() {
        List<String> errors = new ArrayList<>();

        for (WebElement errorMessage : errorMessages) {
            errors.add(errorMessage.getText());
        }

        return errors;
    }
}
