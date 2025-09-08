package codingassignment.tests.base;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.AfterTestExecutionCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public abstract class BaseUiTest {
    protected WebDriver driver;
    protected static final String BASE_URL = System.getProperty("baseUrl", "https://ikea.com/us/en");
    private static final Path SCREENSHOTS_DIR = Paths.get("target", "screenshots");
    private static final DateTimeFormatter TS_FORMAT = DateTimeFormatter.ofPattern("yyyyMMdd-HHmmss");

    protected final String baseUrl = System.getProperty("baseUrl", BASE_URL);

    static {
        try {
            Files.createDirectories(SCREENSHOTS_DIR);
        } catch (Exception ignored) {
        }
    }

    @RegisterExtension
    AfterTestExecutionCallback captureArtifacts =
            context -> {
                if (driver == null) return;

                if (context.getExecutionException().isPresent()) {
                    try {
                        Files.createDirectories(SCREENSHOTS_DIR); // for extra safety

                        String artifactBaseName = buildArtifactBaseName(context);

                        byte[] screenshotPng = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
                        Files.write(SCREENSHOTS_DIR.resolve(artifactBaseName + ".png"), screenshotPng);

                        String pageHtml = driver.getPageSource();
                        Files.writeString(
                                SCREENSHOTS_DIR.resolve(artifactBaseName + ".html"),
                                pageHtml == null ? "" : pageHtml,
                                StandardCharsets.UTF_8
                        );

                        String currentUrl = "";
                        try {
                            currentUrl = driver.getCurrentUrl();
                        } catch (Exception ignored) {
                        }
                        assert currentUrl != null;
                        System.out.println("Saved failure artifacts: target/screenshots/" + artifactBaseName + ".png"
                                + " | target/screenshots/" + artifactBaseName + ".html"
                                + (currentUrl.isEmpty() ? "" : " | URL: " + currentUrl));
                    } catch (Exception ignored) {
                        // never let artifact capture break the test report
                    }
                }
            };

    private static String buildArtifactBaseName(ExtensionContext context) {
        String className = context.getRequiredTestClass().getSimpleName();
        String testDisplay = context.getDisplayName().replaceAll("[^a-zA-Z0-9._-]", "_");
        String timestamp = LocalDateTime.now().format(TS_FORMAT);
        return className + "_" + testDisplay + "_" + timestamp;
    }

    @BeforeEach
    void setUp() {
        ChromeOptions options = new ChromeOptions();
        if (Boolean.getBoolean("headless")) {
            options.addArguments("--headless=new", "--window-size=1366,900");
        }
        driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(Duration.ZERO);
    }

    @AfterEach
    void tearDown() {
        if (driver != null) driver.quit();
    }
}
