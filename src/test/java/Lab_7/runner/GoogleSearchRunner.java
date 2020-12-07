package Lab_7.runner;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "classpath:features/GoogleSearch.feature",
        glue = "classpath:Lab_7.steps",
        plugin = "html:target/google-search-report.html")
public class GoogleSearchRunner {
}
