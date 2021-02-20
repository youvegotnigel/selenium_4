package selenium4_tests;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WindowType;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class MultipleBrowserWindows {

    private WebDriver driver;

    @BeforeClass
    public void setUp () 	{
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
    }

    @Test
    public void testBrowserWindow(){

        SoftAssert softAssert = new SoftAssert();

        driver.get("https://www.google.com");
        softAssert.assertEquals(driver.getTitle(), "Google");

        // new tab in the same browser window
        driver.switchTo().newWindow(WindowType.TAB);

        driver.get("https://www.youtube.com");
        softAssert.assertEquals(driver.getTitle(), "Youtube");

        // new browser window
        driver.switchTo().newWindow(WindowType.WINDOW);
        driver.get("https://www.gmail.com");
        softAssert.assertEquals(driver.getTitle(), "Mail");


        softAssert.assertAll();
    }

    @AfterClass
    public void tearDown () {
        driver.quit();
    }
}
