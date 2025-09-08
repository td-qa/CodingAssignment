package codingassignment.uiElements.pages;

import codingassignment.uiElements.base.BasePage;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class ShoppingBagPage extends BasePage {

    private static final String EXPECTED_ERROR_MESSAGE = "Please enter a valid discount code.";

    //lists using By directly instead of a Findby because sometimes re-queries & waits are needed
    private static final By CART_ITEM_BY = By.cssSelector("[itemscope][itemtype*='schema.org/Product']");

    @FindBy(id = "SEC_cart-coupon_title")
    private WebElement discountCodeToggle;

    @FindBy(id = "discountcode-input")
    private WebElement discountCodeInput;

    @FindBy(xpath = "//*[@id='discountcode-input']/ancestor::*[contains(@class,'cart-ingka-accordion-item')]//button[contains(@class,'cart-ingka-btn--primary')]")
    private WebElement discountCodeApplyBtn;

    @FindBy(className = "cart-ingka-helper-text--error")
    private WebElement discountCodeErrorMessage;

    public ShoppingBagPage(WebDriver driver) {
        super(driver);
    }

    public ShoppingBagPage waitUntilReady(boolean allowEmpty) {
        wait.until(ExpectedConditions.visibilityOf(discountCodeToggle));
        if (!allowEmpty) {
            wait.until(ExpectedConditions
                    .numberOfElementsToBeMoreThan(CART_ITEM_BY, 0));
        }
        return this;
    }

    public void applyDiscount(String code) {
        clickWhenClickable(discountCodeToggle);
        typeWhenVisible(discountCodeInput, code);
        clickWhenClickable(discountCodeApplyBtn);
    }

    public int waitUntilItemCountIs(int n) {
        try {
            wait.until(ExpectedConditions.numberOfElementsToBe(CART_ITEM_BY, n));
        } catch (TimeoutException ignore) {
        }
        return driver.findElements(CART_ITEM_BY).size();
    }

    public WebElement discountError() {
        return discountCodeErrorMessage;
    }

    public String discountErrorText() {
        return getTextSafely((discountCodeErrorMessage));
    }

    public String expectedDiscountError() {
        return EXPECTED_ERROR_MESSAGE;
    }
}
