package tau_course;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;


public class RelativeLocators {

    private WebDriver driver;
    private By login_pannel = By.id("logInPanelHeading");

    @BeforeClass
    public void setUp () {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
    }

    @AfterClass
    public void tearDown () {
        driver.quit();
    }


    @Test
    public void test_01(){

        driver.get("https://opensource-demo.orangehrmlive.com/login");


    }
}
