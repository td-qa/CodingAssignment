package codingassignment.uiElements.base;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public abstract class BaseUi {

    public static final Duration DEFAULT_TIMEOUT = Duration.ofSeconds(12);
    public static final Duration SHORT_TIMEOUT = Duration.ofSeconds(3);
    public static final Duration LONG_TIMEOUT = Duration.ofSeconds(20);

    private static final CharSequence SELECT_ALL =
            Keys.chord(isMac() ? Keys.COMMAND : Keys.CONTROL, "a");

    protected final WebDriver driver;
    protected final WebDriverWait wait;

    private static boolean consentHandled = false;

    protected BaseUi(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, DEFAULT_TIMEOUT);
    }

    // -- actions --

    public void ensureCookieConsentHandled() {
        if (consentHandled) return;
        try {
            var els = driver.findElements(org.openqa.selenium.By.id("onetrust-accept-btn-handler"));
            if (!els.isEmpty() && els.getFirst().isDisplayed()) {
                clickWhenClickable(els.getFirst());
            }
        } catch (RuntimeException ignore) {
        }
        consentHandled = true;
    }

    public void waitUntilVisible(WebElement el) {
        wait.until(ExpectedConditions.visibilityOf(el));
    }

    protected WebElement waitUntilVisible(By by) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(by));
    }

    protected void clickWhenClickable(WebElement el) {
        wait.until(ExpectedConditions.elementToBeClickable(el)).click();
    }

    protected void clickWhenClickable(By by) {
        wait.until(ExpectedConditions.elementToBeClickable(by)).click();
    }

    protected void typeWhenVisible(WebElement el, String text) {
        waitUntilVisible(el);
        el.sendKeys(SELECT_ALL, Keys.DELETE);
        el.sendKeys(text);
    }

    public void jsClick(WebElement el) {
        ((JavascriptExecutor) driver).executeScript(
                "var el=arguments[0]; el.scrollIntoView({block:'center',inline:'nearest'}); el.click();", el
        );
    }

    // --utilities--
    protected String getTextSafely(WebElement el) {
        try {
            return el.getText().trim();
        } catch (RuntimeException e) {
            return "";
        }
    }

    private static boolean isMac() {
        return System.getProperty("os.name").toLowerCase().contains("mac");
    }
}
