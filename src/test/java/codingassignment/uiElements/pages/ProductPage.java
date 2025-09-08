package codingassignment.uiElements.pages;

import codingassignment.uiElements.components.RecommendationsModal;
import codingassignment.uiElements.base.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class ProductPage extends BasePage {

    @FindBy(css = "[aria-label='Add to bag']")
    private WebElement addToCartBtn;

    @FindBy(className = "rec-sheet-modal")
    private WebElement recommendationsModalRoot;

    public ProductPage(WebDriver driver) {
        super(driver);
    }

    public void addToCart() {
        clickWhenClickable(addToCartBtn);
    }

    public RecommendationsModal recommendations() {
        return new RecommendationsModal(driver, recommendationsModalRoot);
    }
}
