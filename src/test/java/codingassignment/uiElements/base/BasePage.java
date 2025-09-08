package codingassignment.uiElements.base;

import codingassignment.uiElements.components.Header;
import org.openqa.selenium.*;
import org.openqa.selenium.support.PageFactory;

public abstract class BasePage extends BaseUi {

    //lazy getter + cached header
    private Header header;

    public Header header() {
        if (header == null) {
            header = new Header(driver);
        }
        return header;
    }

    protected void get(String url) {
        driver.get(url);
        resetHeader();
    }

    protected void resetHeader() {
        header = null;
    }

    protected BasePage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }
}