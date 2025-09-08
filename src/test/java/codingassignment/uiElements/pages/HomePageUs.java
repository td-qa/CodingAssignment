package codingassignment.uiElements.pages;

import codingassignment.uiElements.base.BasePage;
import org.openqa.selenium.WebDriver;

public class HomePageUs extends BasePage {

    public HomePageUs(WebDriver driver) {
        super(driver);
    }

    public HomePageUs open(String baseUrl) {
        get(baseUrl + "/");
        return this;
    }
}
