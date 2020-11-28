package Lab_6.steps;

import Lab_5.Search;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

public class SearchSteps {
    private WebDriver driver;

    public SearchSteps() {
        this.driver = Utils.driver;
    }

    @When("input search word is computer")
    public void input_search_word_is_computer() {
        WebElement searchId = driver.findElement(By.xpath("//*[@id=\"js-search-input\"]"));
        searchId.sendKeys("computer");
        searchId.sendKeys(Keys.RETURN);
    }

    @Then("website logo is displayed")
    public void website_logo_is_displayed() {
        Boolean isLogoDisplayed = driver.findElement(By.cssSelector(".header_bar_logo a")).isDisplayed();
        Assert.assertTrue(isLogoDisplayed);
    }
}
