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

public class ViewConsoleLogsII {

    private WebDriver driver;

    private By input_email =By.xpath("//input[@placeholder='Email']");
    private By input_password =By.xpath("//input[@placeholder='Password']");
    private By btn_login =By.xpath("(//button[@type='submit'])[1]");

    private By div_logged_user = By.xpath("//div[@class='author-bio']");
    private By h4_logged_user = By.xpath("//h4/strong");

    //private String url = "https://www.lego.com/404";
    private String url = "https://www.phptravels.net/login";

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
        driver.findElement(input_email).sendKeys("user@phptravels.com");
        driver.findElement(input_password).sendKeys("demouser");
        driver.findElement(btn_login).click();

        System.out.println("\nConsole Logs for valid login");
        analyzeLog();

        explicit_wait(div_logged_user);
        Assert.assertEquals(driver.findElement(h4_logged_user).getText(),"Lionel");
    }

    @Test
    public void login_with_invalid_credentials () {
        // Load The AUT
        driver.get(url);
        driver.findElement(input_email).sendKeys("user@phptravels.com");
        driver.findElement(input_password).sendKeys("test123");
        driver.findElement(btn_login).click();

        System.out.println("\nConsole Logs for invalid login");
        analyzeLog();

        Assert.assertEquals(driver.getCurrentUrl(),"https://www.phptravels.net/login/failed");
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