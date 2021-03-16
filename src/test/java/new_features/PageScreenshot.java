package new_features;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.io.FileHandler;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.Screenshot;
import ru.yandex.qatools.ashot.shooting.ShootingStrategies;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.time.Duration;

public class PageScreenshot {

    private WebDriver driver;
    private WebDriver driver2;

    private By logo = By.id("nav-logo");

    @BeforeClass
    public void setUp() {

        WebDriverManager.firefoxdriver().setup();
        driver = new FirefoxDriver();
        driver.manage().window().maximize();
        driver.get("https://www.amazon.com/");

        WebDriverManager.chromedriver().setup();
        driver2 = new ChromeDriver();
        driver2.manage().window().maximize();
        //driver2.get("https://www.airbnb.co.in/s/India/");
        driver2.get("https://www.amazon.com/");
    }

    @Test
    public void takeFullPageScreenshot() throws IOException {
        File source = ((FirefoxDriver) driver).getFullPageScreenshotAs(OutputType.FILE);
        FileHandler.copy(source, new File("Amazon Full Page Screenshot.png"));
    }

    @Test
    public void takeFullPageScreenshot_aShot() {

        //take screenshot of the entire page
        Screenshot screenshot = new AShot().shootingStrategy(ShootingStrategies.viewportPasting(1000)).takeScreenshot(driver2);
        try {
            ImageIO.write(screenshot.getImage(),"PNG", new File(".\\Amazon Full Page Screenshot-2.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void takePageScreenshot() throws IOException {
        File source = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(source, new File("Amazon Screenshot.png"));
    }

    @Test
    public void takeElementScreenshot() throws IOException {

        /** WebDriverWait method new parameter introduced in in selenium 4
         *  Old parameter with only time is deprecated.
         * */
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        wait.until(ExpectedConditions.visibilityOfElementLocated(logo));

        WebElement amazonLogo = driver.findElement(logo);

        File screenshot = amazonLogo.getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(screenshot, new File("Amazon Logo.png"));

    }

    @AfterClass
    public void tearDown() {
        driver.quit();
        driver2.quit();
    }
}
