package new_features;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.devtools.DevTools;

import org.openqa.selenium.devtools.v108.log.Log;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class ViewConsoleLogs {

    private WebDriver driver;
    private DevTools devTools;
    //private String url = "https://www.lego.com/404";
    private String url = "https://qa.eshrewd.net/resilience/";

    @BeforeClass
    public void setUp () {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();

    }

    @Test
    public void viewConsoleLogs () {
        // Get The DevTools And Create A Session
        devTools = ((ChromeDriver)this.driver).getDevTools();
        devTools.createSession();

        // Send A Command To Enable The Logs
        devTools.send(Log.enable());

        // Add A Listener For The Logs
        devTools.addListener(Log.entryAdded(), logEntry -> {
            //System.out.println(logEntry.asSeleniumLogEntry());
            System.out.println("----------");
            System.out.println("source = " + logEntry.getSource());
            System.out.println("level = " + logEntry.getLevel());
            System.out.println("text = " + logEntry.getText());
            System.out.println("timestamp = " + logEntry.getTimestamp());
        });

        // Load The AUT
        driver.get(url);

    }


    @AfterClass
    public void tearDown(){
        driver.quit();
    }
}
