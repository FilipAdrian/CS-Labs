package Lab_6.steps;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterClass;

public class Utils {
    public static WebDriver driver;
    private static final String DRIVER_URL = "src/test/resources/chromedriver.exe";

    @Before
    public WebDriver init() {
        System.setProperty("webdriver.chrome.driver", DRIVER_URL);
        driver = new ChromeDriver();
        return driver;
    }

    @After
    public void exit() {
        driver.manage().deleteAllCookies();
        driver.quit();
    }
}
