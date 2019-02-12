package cucumber.helpers;

import cucumber.enums.BrowserType;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;

public class WebDriverSessionManager {

    private static WebDriverSessionManager instance;

    private WebDriverSessionManager() {
    }

    public static WebDriverSessionManager getInstance() {
        if(instance == null) {
            instance = new WebDriverSessionManager();
        }
        return instance;
    }

    private WebDriver driver;

    public WebDriver getDriver() {
        if (driver == null) {
            createDriver();
        }
        return driver;
    }

    public void quitDriver() {
        if (driver != null) {
            driver.quit();
            driver = null;
        }
    }

    private void createDriver() {
        switch (TestConfigReader.getInstance().getBrowser()) {
            case CHROME:
                createChromeDriver();
                break;
            case FIREFOX:
                createFirefoxDriver();
                break;
            default:
                throw new IllegalArgumentException("Given browser is not supported. Please use "
                                                   + BrowserType.values().toString());
        }
    }

    private void createChromeDriver() {
        if (TestConfigReader.getInstance().runInHeadlessMode()) {
            WebDriverManager.chromedriver().setup();

            ChromeOptions options = new ChromeOptions();
            options.addArguments("--headless");
            options.addArguments("--disable-gpu");

            driver = new ChromeDriver(options);
            return;
        }

        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
    }

    private void createFirefoxDriver() {
        WebDriverManager.firefoxdriver().setup();
        driver = new FirefoxDriver();
    }
}
