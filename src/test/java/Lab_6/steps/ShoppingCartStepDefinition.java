package Lab_6.steps;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import javafx.util.converter.BigDecimalStringConverter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.*;

public class ShoppingCartStepDefinition {
    static Logger logger = LogManager.getLogger(ShoppingCartStepDefinition.class.getName());
    private Map<String, Object> stepContext = new HashMap<>();
    private WebDriver driver;
    private WebDriverWait driverWait;

    public ShoppingCartStepDefinition() {
        driver = Utils.driver;
        driverWait = new WebDriverWait(driver, 10);
        logger.info("Start Testing");
    }

    @Given("I'm on {} page")
    public void iAmOnPage(String page) {
        driver.get(page);
    }

    @When("Click on {string} button of more than {int} products")
    public void clickOnButtonNTimes(String buttonName, Integer clickedNumber) {
        String buttonXpathUrl = ".//input[@value=\"" + buttonName + "\" and @class=\"button\"]";
        List<WebElement> products = driver.findElements(By.xpath("//*[ @class=\"col-md-3 product-men\"]"));

        //Scroll to products list
        WebElement productsView = driver.findElement(By.cssSelector(".single-pro"));
        Actions actions = new Actions(driver);
        actions.moveToElement(productsView);
        actions.perform();

        //Select random products
        Random random = new Random();
        DecimalFormat df = new DecimalFormat("#.##");
        Set productsName = new HashSet();
        float totalPrice = 0;
        for (int i = 0; i < clickedNumber; i++) {
            int currentProduct = random.nextInt(products.size());
            float price = Float.parseFloat(products.get(currentProduct).findElement(By.xpath(".//span[ @class=\"item_price\"]")).getText().substring(1));
            totalPrice += price;
            String name = products.get(currentProduct).findElement(By.cssSelector(".item-info-product a")).getText().toLowerCase();
            productsName.add(name);
            products.get(currentProduct).findElement(By.xpath(buttonXpathUrl)).click();
        }

        //Store data in step context
        stepContext.put("totalPrice", df.format(totalPrice));
        stepContext.put("productsList", productsName);
    }

    @Then("Shopping Cart Must be Displayed")
    public void shoppingCartMustBeDisplayed() {
        WebElement shoppingCart = driver.findElement(By.xpath("//*[@id=\"PPMiniCart\"]"));
        boolean isDisplayed = shoppingCart.isEnabled();
        stepContext.put("shoppingCart", shoppingCart);
        Assert.assertTrue(isDisplayed);
    }

    @Then("All selected products are diplayed in shopping cart")
    public void selectedProductsAreDisplayedInShoppingCart() {
        WebElement shoppingCart = (WebElement) stepContext.get("shoppingCart");
        HashSet productsList = (HashSet<String>) stepContext.get("productsList");
        List<WebElement> products = shoppingCart.findElements(By.cssSelector(".minicart-details-name a"));
        logger.info(productsList);
        for (WebElement product : products) {
            logger.info(product.getText().toLowerCase());
            Assert.assertTrue(productsList.contains(product.getText().toLowerCase()));
        }
    }

    @Then("Total Price must be correct")
    public void totalPriceIsCorrect() {
        WebElement shoppingCart = (WebElement) stepContext.get("shoppingCart");
        String displayedPrice = shoppingCart.findElement(By.cssSelector(".minicart-subtotal")).getText().trim();
        logger.info("Displayed Price: " + displayedPrice);
        logger.info("Correct Total Price: " + stepContext.get("totalPrice"));
        Assert.assertTrue(displayedPrice.contains(stepContext.get("totalPrice").toString()));
    }


    @When("A item id added to shopping cart, its quantity is changed to {int}")
    public void itemQuantityIsChanged(int itemQuantity) {
        clickOnButtonNTimes("Add to cart", 1);
        shoppingCartMustBeDisplayed();
        WebElement shoppingCart = (WebElement) stepContext.get("shoppingCart");
        WebElement input = shoppingCart.findElement(By.cssSelector(".minicart-details-quantity .minicart-quantity"));
        logger.info("Displayed Price: " + shoppingCart.findElement(By.cssSelector(".minicart-details-price .minicart-price")).getText());
        float price = Float.parseFloat(shoppingCart.findElement(By.cssSelector(".minicart-details-price .minicart-price")).getText().substring(1));
        float newPrice = price * itemQuantity;
        logger.info("New price: " + newPrice);
        stepContext.put("newPrice", newPrice);
        logger.info("Initial price: " + price);
        stepContext.put("initialPrice", price);

        input.click();
        input.sendKeys(String.valueOf(itemQuantity));

    }

    @Then("New price is computed right")
    public void newPriceIsComputedRight() {
        String initialPrice = stepContext.get("initialPrice").toString();
        String newPrice = stepContext.get("newPrice").toString();
        WebElement shoppingCart = (WebElement) stepContext.get("shoppingCart");
        WebElement itemPrice = shoppingCart.findElement(By.cssSelector(".minicart-details-price .minicart-price"));
        driverWait.until(ExpectedConditions.not(ExpectedConditions.textToBePresentInElement(itemPrice, initialPrice)));
        String price = shoppingCart.findElement(By.cssSelector(".minicart-details-price .minicart-price")).getText().substring(1);
        logger.info("Computed price: " + price);
        price = new BigDecimal(price).setScale(1, RoundingMode.HALF_UP).toString();
        Assert.assertTrue(price.equalsIgnoreCase(newPrice));
    }

    @Given("Adding more than {int} product to shopping cart")
    public void addingMoreThanProductToShoppingCart(int productsNumber) {
        clickOnButtonNTimes("Add to cart", productsNumber);
    }

    @When("{int}nd item is deleted")
    public void itemIsSelected(int productNumber) {
        WebElement deleteButton = driver.findElements(By.cssSelector(".minicart-details-remove .minicart-remove")).get(productNumber-1);
        String productName = driver.findElements(By.cssSelector(".minicart-name")).get(productNumber-1).getText();
        stepContext.put("deletedProductName", productName);
        logger.info("Deleted Product Name: " + productName);
        deleteButton.click();
    }

    @Then("Item must be removed from the list")
    public void itemMustBeRemovedFromList() {
        String deletedProduct = stepContext.get("deletedProductName").toString();
        List<WebElement> products = driver.findElements(By.cssSelector(".minicart-name"));
        boolean isDeleted = true;
        for(WebElement product : products){
            logger.info(product.getText());
            if (product.getText().equalsIgnoreCase(deletedProduct)){
                isDeleted = false;
            }
        }

        Assert.assertTrue(isDeleted);

    }
}