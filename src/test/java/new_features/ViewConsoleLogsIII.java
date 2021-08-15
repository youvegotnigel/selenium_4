package new_features;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.logging.*;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.logging.Level;

public class ViewConsoleLogsIII {

    private WebDriver driver;

    private By input_username =By.xpath("//input[@id='username']");
    private By input_password =By.xpath("//input[@id='password']");
    private By li_location = By.xpath("//li[@id='Laboratory']");
    private By btn_login =By.xpath("//input[@id='loginButton']");


    private String url = "https://demo.openmrs.org/openmrs/login.htm";

    @BeforeClass
    public void setUp () {
        WebDriverManager.chromedriver().setup();

        ChromeOptions options = new ChromeOptions();
        LoggingPreferences logPrefs = new LoggingPreferences();
        logPrefs.enable(LogType.BROWSER, Level.ALL);
        options.setCapability(CapabilityType.LOGGING_PREFS, logPrefs);
        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
    }

    @Test
    public void login_with_valid_credentials () {
        // Load The AUT
        driver.get(url);
        driver.findElement(input_username).sendKeys("Admin");
        driver.findElement(input_password).sendKeys("Admin123");
        driver.findElement(li_location).click();
        driver.findElement(btn_login).click();

        System.out.println("\nConsole Logs for valid login");
        analyzeLog();

        Assert.assertEquals(driver.getCurrentUrl(),"https://demo.openmrs.org/openmrs/referenceapplication/home.page");
    }

    @Test
    public void login_with_invalid_credentials () {
        // Load The AUT
        driver.get(url);
        driver.findElement(input_username).sendKeys("user@phptravels.com");
        driver.findElement(input_password).sendKeys("test123");
        driver.findElement(li_location).click();
        driver.findElement(btn_login).click();

        System.out.println("\nConsole Logs for invalid login");
        analyzeLog();

        Assert.assertEquals(driver.getCurrentUrl(),"https://demo.openmrs.org/openmrs/referenceapplication/login.page");
    }

    public void analyzeLog() {

        LogEntries logEntries = driver.manage().logs().get(LogType.BROWSER);
        for (LogEntry entry : logEntries) {
            //System.out.println(new Date(entry.getTimestamp()) + " " + entry.getLevel() + " " + entry.getMessage());
            System.out.println(entry.getMessage());
            System.out.println(entry.getLevel());
        }

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void explicit_wait(By by){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.visibilityOfElementLocated(by));
    }

    @AfterClass
    public void tearDown(){
        driver.quit();
    }
}
