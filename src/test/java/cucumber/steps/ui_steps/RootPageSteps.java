package cucumber.steps.ui_steps;

import cucumber.api.java8.En;
import cucumber.pages.RootPage;
import cucumber.pages.fragments.EndpointActionRowFragment;
import cucumber.steps.AbstractSteps;
import io.cucumber.datatable.DataTable;
import org.apache.commons.lang3.RandomStringUtils;

import java.util.List;

import static org.junit.Assert.*;

public class RootPageSteps extends AbstractSteps implements En {

    private RootPage rootPage;

    public RootPageSteps() {
        Given("^a root page is opened$", () -> {
            rootPage = new RootPage(getDriver());
            rootPage.openAndVerify();
        });

        When("I press \"Add Listener\" button", () -> {
            rootPage.pressAddListenerButton();
        });

        Then("an endpoint addition form should be shown", () -> {
            assertTrue(rootPage.isAddListenerFormDisplayed());
        });

        When("^I add \"([^\"]*)\" endpoint$", (String path) -> {
            rootPage.insertEndpointPath(path);
            rootPage.submitAddListenerForm();
        });

        When("^I add endpoint with path containing (\\d+) characters$", (Integer pathLength) -> {
            String path = RandomStringUtils.randomAlphanumeric(pathLength);
            rootPage.insertEndpointPath(path);
            rootPage.submitAddListenerForm();
        });

        Then("an endpoint addition form should be hidden", () -> {
            assertTrue(rootPage.isAddListenerFormHidden());
        });

        Then("an endpoint \"([^\"]*)\" should be shown in endpoints list as active", (String path) -> {
            EndpointActionRowFragment actionRow = rootPage.getEndpointActionRow(path);
            assertTrue(actionRow.isEndpointActive());
            assertTrue(actionRow.isPauseListeningButtonShown());
        });

        Then("an endpoint \"([^\"]*)\" should be shown in endpoints list as inactive", (String path) -> {
            EndpointActionRowFragment actionRow = rootPage.getEndpointActionRow(path);
            assertFalse(actionRow.isEndpointActive());
            assertTrue(actionRow.isPressResumeListeningButtonSown());
        });

        Then("^errors alert should be displayed$", () -> {
            assertTrue(rootPage.isErrorsAlertDisplayed());
        });

        And("^errors list should match the following errors list$", (DataTable expectedErrorsTable) -> {
            List<String> expectedErrors = expectedErrorsTable.asList(String.class);
            List<String> actualErrors = rootPage.getAllErrors();
            assertEquals(expectedErrors.size(), actualErrors.size());
            assertTrue(actualErrors.containsAll(expectedErrors));
        });

        When("^an endpoint \"([^\"]*)\" is activated on root page$", (String path) -> {
            EndpointActionRowFragment actionRow = rootPage.getEndpointActionRow(path);
            actionRow.pressResumeListeningButton();
        });

        When("^an endpoint \"([^\"]*)\" is deactivated on root page$", (String path) -> {
            EndpointActionRowFragment actionRow = rootPage.getEndpointActionRow(path);
            actionRow.pressPauseListeningButton();
        });
    }
}
