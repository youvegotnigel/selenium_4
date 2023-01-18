package oculys_logs;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.devtools.DevTools;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

public class SelectButtonLog {

    private WebDriver driver;

    private By input_username = By.xpath("//input[@id='UserName']");
    private By input_password = By.xpath("//input[@id='Password']");
    private By input_login = By.xpath("//input[@value='Log in']");
    private By medicine_bed_board = By.xpath("//a[@href='BedBoard/Medicine/']");
    private By admit_request = By.xpath("(//a[@class='clickableButton red-button'])[1]");
    private By assign_button = By.xpath("(//a[contains(text(),'ASSIGN')])[1]");
    private By discharge_button = By.xpath("(//a[contains(text(),'Discharge')])[1]");
    private By dis_button = By.xpath("(//a[contains(text(),'DIS')])[1]");
    private By save_button = By.id("save-button");

    private By delete_button = By.id("delete-button");

    private static String LOGIN_URL = "https://ogh.test-oculys.com/Login";

    @BeforeClass
    public void setup() {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.setHeadless(false); //set headless mode true or false
        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        driver.get(LOGIN_URL);
        Assert.assertEquals(driver.getTitle(), "User Login - Performance");
       
    }

    @AfterClass
    public void tearDown() throws InterruptedException {

        Thread.sleep(5000);
        driver.quit();
    }
}
