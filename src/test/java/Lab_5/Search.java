package Lab_5;


import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

public class Search {
    public static WebDriver driver;
    private static final String DRIVER_URL = "src/test/resources/chromedriver.exe";
    private static final String NINE_URL = "https://999.md/";

    @BeforeSuite
    public static void init() {
        System.setProperty("webdriver.chrome.driver", DRIVER_URL);
        driver = new ChromeDriver();
        driver.get(NINE_URL);
    }

    @Test(testName = " Check If Logo is present after search")
    public static void searchOnYoutube() {
        WebElement searchId = driver.findElement(By.xpath("//*[@id=\"js-search-input\"]"));
        searchId.sendKeys("computer");
        searchId.sendKeys(Keys.RETURN);
        Boolean isLogoDisplayed = driver.findElement(By.cssSelector(".header_bar_logo a")).isDisplayed();
        Assert.assertTrue(isLogoDisplayed);
    }

    @AfterTest
    public static void exit(){
        driver.manage().deleteAllCookies();
        driver.quit();
    }


}
