package codingassignment.uiElements.pages;

import codingassignment.uiElements.components.ProductCard;
import codingassignment.uiElements.base.BasePage;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.util.List;

public class ProductsCatalogPage extends BasePage {

    //lists using By directly instead of a findby because sometimes re-queries & waits are needed
    //public since search method needs it, but putting it on searchbar makes it potentially harder to find
    public static final By PRODUCT_CARD_BY = By.cssSelector("[id*='product-list'] [data-testid*='plp-product-card']");

    public ProductsCatalogPage(WebDriver driver) {
        super(driver);
    }

    public ProductsCatalogPage waitUntilReady() {
        wait.until(ExpectedConditions.numberOfElementsToBeMoreThan(PRODUCT_CARD_BY, 0));
        return this;
    }

    public List<ProductCard> productCards() {
        var roots = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(PRODUCT_CARD_BY));
        return roots.stream().map(r -> new ProductCard(driver, r)).toList();
    }
}
