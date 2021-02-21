package new_features;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

public class GeoLocation {

    private ChromeDriver driver;

    @BeforeClass
    public void setUp () {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
    }

    @Test
    public void mockGeolocation () {

        Map coordinates = new HashMap()
        {{
            put("latitude", 7.2008);
            put("longitude", 79.8737);
            put("accuracy", 1);
        }};
        driver.executeCdpCommand(
                "Emulation.setGeolocationOverride",coordinates);
        driver.get("https://where-am-i.org/");
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @AfterClass
    public void tearDown(){
        driver.quit();
    }
}
