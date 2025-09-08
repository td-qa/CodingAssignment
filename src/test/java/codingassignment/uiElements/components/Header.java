package codingassignment.uiElements.components;

import codingassignment.uiElements.base.BaseComponent;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class Header extends BaseComponent {

    public final SearchBar searchBar; // reuse, don’t duplicate

    public Header(WebDriver driver) {
        super(driver, By.cssSelector("[class*='hnf-header']"));
        this.searchBar = new SearchBar(driver, root);
    }
}
