package new_features;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.WindowType;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.locators.RelativeLocator;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.openqa.selenium.support.locators.RelativeLocator.with;

public class RelativeLocators{

    private WebDriver driver;

    @BeforeClass
    public void setUp () {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        driver.manage().window().maximize();
    }

    @AfterClass
    public void tearDown () {
        driver.quit();
    }

    @Test
    public void testRelativeLocator_Below () {
        driver.get("https://app.testproject.io/");
        WebElement signInButton = driver.findElement(By.id("tp-sign-in"));

        driver.findElement(with(By.tagName("a")).below(signInButton)).click();
        String title = driver.findElement(By.id("tp-title")).getText();
        System.out.println("Title: " + title);
    }

    @Test
    public void testMultipleRelativeLocators_AboveToRightOf () {
        driver.get("https://app.testproject.io/");
        WebElement signInButton = driver.findElement(By.id("tp-sign-in"));
        WebElement rememberMeCheckbox = driver.findElement(By.id("rememberMe"));
        WebElement rememberMeText = driver.findElement(with(By.tagName("span"))
                .above(signInButton)
                .toRightOf(rememberMeCheckbox));
        System.out.println("Text = " + rememberMeText.getText());
    }

    @Test
    public void testRelativeLocator_FindListOfWebElements () {
        driver.get("https://addons.testproject.io/");
        List<WebElement> allPlatforms = driver.findElements(RelativeLocator.with(By.tagName("img"))
                .toRightOf(By.id("q")));

        for (WebElement platform : allPlatforms) {
            System.out.println(platform.getAttribute("alt"));
        }
        driver.switchTo().newWindow(WindowType.TAB);
    }
}
