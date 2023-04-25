package new_features;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.Optional;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.v112.log.Log;
import org.openqa.selenium.devtools.v112.page.Page;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

/**
 * Selected test scenarios for Selenium Chrome Developer Tools Selenium 4 bridge
 * https://chromedevtools.github.io/devtools-protocol/tot/Console#method-enable
 * https://chromedevtools.github.io/devtools-protocol/tot/Log#method-enable
 *
 * @author: Serguei Kouzmine (kouzmine_serguei@yahoo.com)
 */

public class LoggingDevToolsTest {

    private static boolean runHeadless = false;
    //private static String osName = Utils.getOSName();
    private static ChromeDriver driver;
    private static DevTools chromeDevTools;

    private final static String baseURL = "https://www.google.com";

    @BeforeClass
    public static void setUp() throws Exception {

        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();

        chromeDevTools = driver.getDevTools();
        chromeDevTools.createSession();
    }

    @Test
    public static void beforeClass() throws Exception {
        // enable Console
        chromeDevTools.send(Log.enable());
        // add event listener to show in host console the browser console message
        chromeDevTools.addListener(Log.entryAdded(), o -> {
            assertThat(o.getText(), notNullValue());
            assertThat(o.getLineNumber(), notNullValue());
            assertThat(o.getTimestamp(), notNullValue());
            assertThat(o.getSource(), notNullValue());

        });
        chromeDevTools.addListener(Log.entryAdded(), System.err::println);
        driver.get(baseURL);
    }

    @AfterClass
    public static void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    // https://chromedevtools.github.io/devtools-protocol/1-3/Page/#method-navigate
    @Test
    public void test1() {
        chromeDevTools.send(Page.navigate(baseURL, Optional.empty(),
                Optional.empty(), Optional.empty(), Optional.empty()));
    }

}