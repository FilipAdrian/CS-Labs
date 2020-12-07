package Lab_7.steps;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

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


    public static void navigate(String url) {
        driver.get(url);
    }

    public static WebElement getElement(By selector) {
        return driver.findElement(selector);
    }

    public static int getListElementSize(By selector) {
        return driver.findElements(selector).size();
    }

    public static boolean isElementPresent(WebElement element) {
        return element.isDisplayed();
    }

    public static String getCurrentURL() {
        return driver.getCurrentUrl();
    }
}
