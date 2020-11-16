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
    private static WebDriver driver;
    private static final String DRIVER_URL = "C:\\Users\\adrya\\IdeaProjects\\chromedriver.exe";
    private static final String YOUTUBE_URL = "https://999.md/";

    @BeforeSuite
    public void init() {
        System.setProperty("webdriver.chrome.driver", DRIVER_URL);
        driver = new ChromeDriver();
        driver.get(YOUTUBE_URL);
    }

    @Test(testName = " Check If Logo is present after search")
    public static void searchOnYoutube() {
        WebDriverWait wait1 = new WebDriverWait(driver, 10);
        WebElement searchId = driver.findElement(By.xpath("//*[@id=\"js-search-input\"]"));
        wait1.until(ExpectedConditions.visibilityOf(searchId));
        searchId.sendKeys("computer");
        searchId.sendKeys(Keys.RETURN);
        Boolean isLogoDisplayed = driver.findElement(By.xpath("//*[@id=\"header\"]/div[2]/div/h1/a")).isDisplayed();
        Assert.assertTrue(isLogoDisplayed);
    }

    @AfterTest
    public static void exit(){
        driver.manage().deleteAllCookies();
        driver.close();
        driver.quit();
    }


}
