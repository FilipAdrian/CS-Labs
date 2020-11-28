package Lab_6.runner;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "classpath:features",
        glue = "classpath:Lab_6.steps",
        plugin = "html:target/shopping-cart-report.html")
public class ShoppingCartRunner {
}
