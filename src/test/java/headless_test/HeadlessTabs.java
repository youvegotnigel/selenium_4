package headless_test;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WindowType;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.Screenshot;
import ru.yandex.qatools.ashot.shooting.ShootingStrategies;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class HeadlessTabs {

    private WebDriver driver;

    @BeforeMethod
    public void setup() {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.setHeadless(true); //set headless mode true or false
        options.addArguments("window-size=1536,824");

        driver = new ChromeDriver(options);

        //driver.manage().window().maximize();
        driver.get("https://the-internet.herokuapp.com/large");
    }

    @Test
    public void test(){

        SoftAssert softAssert = new SoftAssert();

        takePageScreenshot();
        //takeFullPageScreenshot();
        driver.switchTo().newWindow(WindowType.TAB);
        driver.get("https://twitter.com/");

        takePageScreenshot();
        //takeFullPageScreenshot();
        softAssert.assertEquals(driver.getTitle(), "Twitter. It’s what’s happening / Twitter");
        String youTubeWindow= driver.getWindowHandle();
        System.out.println("YouTube Window Handle = " + youTubeWindow);

        driver.switchTo().newWindow(WindowType.WINDOW);
        driver.get("https://www.facebook.com");

        takePageScreenshot();
        //takeFullPageScreenshot();
        softAssert.assertEquals(driver.getTitle(), "Facebook – log in or sign up");
        String facebookWindow= driver.getWindowHandle();
        System.out.println("YouTube Window Handle = " + facebookWindow);
    }

    public void takeFullPageScreenshot() {

        String fileName = System.currentTimeMillis() + ".png";
        String filepath = System.getProperty("user.dir") + "\\src\\test\\java\\headless_test\\screenshots\\" + fileName;

        //take screenshot of the entire page
        Screenshot screenshot = new AShot().shootingStrategy(ShootingStrategies.viewportPasting(1000)).takeScreenshot(driver);
        try {
            ImageIO.write(screenshot.getImage(),"PNG", new File(filepath));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void takePageScreenshot(){

        String fileName = System.currentTimeMillis() + ".png";
        String filepath = System.getProperty("user.dir") + "\\src\\test\\java\\headless_test\\screenshots\\" + fileName;

        File source = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        try {
            FileUtils.copyFile(source, new File(filepath));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @AfterMethod
    public void tearDown(){
        driver.quit();
    }
}
