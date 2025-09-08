package codingassignment.uiElements.base;

import org.openqa.selenium.*;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.DefaultElementLocatorFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

public abstract class BaseComponent extends BaseUi {
    protected final SearchContext root;

    //for self-locating root (header, footer, etc.)
    protected BaseComponent(WebDriver driver, By rootBy) {
        super(driver);
        this.root = wait.until(ExpectedConditions.presenceOfElementLocated(rootBy));
        PageFactory.initElements(new DefaultElementLocatorFactory(root), this);
    }

    //for root located in parent (easier re-query: cards in grid, rows in table etc.)
    protected BaseComponent(WebDriver driver, SearchContext root) {
        super(driver);
        this.root = root;
        PageFactory.initElements(new DefaultElementLocatorFactory(root), this);
    }
}
