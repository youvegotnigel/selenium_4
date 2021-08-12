package new_features;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.logging.*;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.Date;
import java.util.logging.Level;

public class ViewConsoleLogsII {

    private WebDriver driver;
    private DevTools devTools;
    //private String url = "https://www.lego.com/404";
    private String url = "https://www.saucedemo.com/";

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
    public void viewConsoleLogs () {

        // Load The AUT
        driver.get(url);
        analyzeLog();
    }

    public void analyzeLog() {
        LogEntries logEntries = driver.manage().logs().get(LogType.BROWSER);
        for (LogEntry entry : logEntries) {
            //System.out.println(new Date(entry.getTimestamp()) + " " + entry.getLevel() + " " + entry.getMessage());
            System.out.println(entry.getMessage());
            //do something useful with the data
        }
    }

    @AfterClass
    public void tearDown(){
        driver.quit();
    }
}
