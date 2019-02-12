package cucumber.runner;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(plugin = {"pretty"},
                 features = "src/test/resources/features/",
                 glue = {"cucumber.steps"},
                 tags = {"not @excluded"})
public class CucumberRunner {

}
