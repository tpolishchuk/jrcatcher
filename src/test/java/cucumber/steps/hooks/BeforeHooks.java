package cucumber.steps.hooks;

import cucumber.api.Scenario;
import cucumber.api.java8.En;
import cucumber.helpers.request_helpers.DatabaseRequestHelper;
import cucumber.steps.AbstractSteps;

public class BeforeHooks extends AbstractSteps implements En {

    public BeforeHooks() {
        Before(new String[]{"@clear-db"}, (Scenario scenario) -> {
            DatabaseRequestHelper databaseRequestHelper = new DatabaseRequestHelper();
            databaseRequestHelper.clearDatabase();
        });
    }
}
