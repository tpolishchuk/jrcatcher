package cucumber.steps.hooks;

import cucumber.api.Scenario;
import cucumber.api.java8.En;
import cucumber.steps.AbstractSteps;

public class AfterHooks extends AbstractSteps implements En {

    public AfterHooks() {
        After(new String[]{"@ui"}, (Scenario scenario) -> {
            quitDriver();
        });
    }
}
