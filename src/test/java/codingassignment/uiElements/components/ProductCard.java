package codingassignment.uiElements.components;

import codingassignment.uiElements.base.BaseComponent;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class ProductCard extends BaseComponent {

    @FindBy(className = "plp-product__image-link")
    private WebElement productLink;

    public ProductCard(WebDriver driver, SearchContext root) {
        super(driver, root);
    }

    public void openProductPage() {
        clickWhenClickable(productLink);
    }
}
