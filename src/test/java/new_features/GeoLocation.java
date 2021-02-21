package new_features;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

public class GeoLocation {

    private ChromeDriver driver;

    private double latitude = 7.2008;
    private double longitude = 79.8737;
    private double accuracy = 1;

    private By latitudeLocation = By.id("latitude");
    private By longitudeLocation = By.id("longitude");

    @BeforeClass
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
    }

    @Test
    public void mockGeolocation() {

//        Map coordinates = new HashMap() {{
//            put("latitude", 7.2008);
//            put("longitude", 79.8737);
//            put("accuracy", 1);
//        }};

        Map coordinates = new HashMap();

        coordinates.put("latitude", latitude);
        coordinates.put("longitude", longitude);
        coordinates.put("accuracy", accuracy);

        driver.executeCdpCommand("Emulation.setGeolocationOverride", coordinates);

        driver.get("https://where-am-i.org/");

        explicitWait(latitudeLocation);
        Assert.assertEquals(driver.findElement(latitudeLocation).getText(), String.valueOf(latitude));
        explicitWait(longitudeLocation);
        Assert.assertEquals(driver.findElement(longitudeLocation).getText(), String.valueOf(longitude));
    }

    public void explicitWait(By element){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        wait.until(ExpectedConditions.visibilityOfElementLocated(element));
    }

    @AfterClass
    public void tearDown() {
        driver.quit();
    }
}
