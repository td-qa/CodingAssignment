package codingassignment.uiElements.components;

import codingassignment.uiElements.base.BaseComponent;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static codingassignment.uiElements.pages.ProductsCatalogPage.PRODUCT_CARD_BY;

public class SearchBar extends BaseComponent {

    @FindBy(id = "ikea-search-input")
    private WebElement searchInput;

    public SearchBar(WebDriver driver, SearchContext root) {
        super(driver, root);
    }

    public void search(String searchQuery) {
        typeWhenVisible(searchInput, searchQuery);
        searchInput.sendKeys(Keys.ENTER);
        if (!waitForSearchResultsShort()) {
            By submitBy = By.className("search-box-btn"); //fresh element to avoid stale element reference
            clickWhenClickable(submitBy);
            waitForSearchResultsDefault();
        }
    }

    private void waitForSearchResultsDefault() {
        waitForSearchResults(DEFAULT_TIMEOUT);
    }

    private boolean waitForSearchResultsShort() {
        return waitForSearchResults(SHORT_TIMEOUT);
    }

    private boolean waitForSearchResults(Duration timeout) {
        try {
            new WebDriverWait(driver, timeout)
                    .until(ExpectedConditions.or(
                            ExpectedConditions.stalenessOf(searchInput),
                            ExpectedConditions.presenceOfAllElementsLocatedBy(PRODUCT_CARD_BY),
                            ExpectedConditions.urlContains("/search")
                    ));
            return true;
        } catch (TimeoutException e) {
            return false;
        }
    }
}
