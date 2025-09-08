package codingassignment.uiElements.components;

import codingassignment.uiElements.base.BaseComponent;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class RecommendationsModal extends BaseComponent {

    @FindBy(css = ".rec-modal-footer button[aria-label*='Continue shopping' i]")
    private WebElement continueShoppingBtn;

    @FindBy(css = ".rec-modal-footer button[aria-label*='Go to shopping bag' i]")
    private WebElement shoppingBagBtn;

    public RecommendationsModal(WebDriver driver, SearchContext modalRoot) {
        super(driver, modalRoot);
    }

    public void continueShopping() {
        clickWhenClickable(continueShoppingBtn);
    }

    public void goToShoppingBag() {
        clickWhenClickable(shoppingBagBtn);
    }
}