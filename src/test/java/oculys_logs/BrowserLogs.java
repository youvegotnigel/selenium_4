package oculys_logs;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.v108.log.Log;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.time.Duration;

public class BrowserLogs {

    private WebDriver driver;
    private DevTools devTools;

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
        navigate_to_bed_board();
    }

    @AfterClass
    public void tearDown() throws InterruptedException {

        Thread.sleep(5000);
        driver.quit();
    }

    public void explicitWaitMethod(By element) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        wait.until(ExpectedConditions.visibilityOfElementLocated(element));
    }

    private void navigate_to_bed_board(){
        driver.findElement(input_username).sendKeys("OculysAdmin");
        driver.findElement(input_password).sendKeys("Oculys123");
        driver.findElement(input_login).click();
        explicitWaitMethod(medicine_bed_board);
        driver.findElement(medicine_bed_board).click();
    }

    @Test
    public void test(){

        viewConsoleLogs();
        explicitWaitMethod(assign_button);
        driver.findElement(assign_button).click();
        viewConsoleLogs();

        explicitWaitMethod(discharge_button);
        driver.findElement(discharge_button).click();
        viewConsoleLogs();

        explicitWaitMethod(save_button);
        driver.findElement(save_button).click();
        viewConsoleLogs();

        //----------------------------------------------------------------

        explicitWaitMethod(dis_button);
        driver.findElement(dis_button).click();
        viewConsoleLogs();

        explicitWaitMethod(delete_button);
        driver.findElement(delete_button).click();
        viewConsoleLogs();

        String alertText = driver.switchTo().alert().getText();
        System.out.println("Alert Text ::: " + alertText);
        driver.switchTo().alert().accept();

        //wait for button reset
        explicitWaitMethod(assign_button);
        viewConsoleLogs();

    }


    public void viewConsoleLogs() {
        // Get The DevTools And Create A Session
        devTools = ((ChromeDriver)this.driver).getDevTools();
        devTools.createSession();

        // Send A Command To Enable The Logs
        devTools.send(Log.enable());

        // Add A Listener For The Logs
        devTools.addListener(Log.entryAdded(), logEntry -> {
            //System.out.println(logEntry.asSeleniumLogEntry());
            System.out.println("----------------------------------------\n");
            System.out.println("source = " + logEntry.getSource());
            System.out.println("level = " + logEntry.getLevel());
            System.out.println("text = " + logEntry.getText());
            System.out.println("timestamp = " + logEntry.getTimestamp());
            System.out.println("----------------------------------------\n");
        });

    }


}
