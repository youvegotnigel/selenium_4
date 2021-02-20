package selenium4_tests;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.v84.network.Network;
import org.openqa.selenium.devtools.v84.network.model.ConnectionType;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.Optional;

public class SlowNetworkTests {

    private ChromeDriver driver;
    DevTools devTools;

    @BeforeClass
    public void setUp () {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        devTools = driver.getDevTools();
    }

    @Test
    public void enableSlowNetwork () {
        devTools.createSession();
        devTools.send(Network.enable(
                Optional.empty(),
                Optional.empty(),
                Optional.empty()));
        devTools.send(Network.emulateNetworkConditions(
                false,
                150,
                2500,
                2000,
                Optional.of(ConnectionType.CELLULAR3G)));
        driver.get("https://www.linkedin.com");
        System.out.println("Slow " + driver.getTitle());
    }

    @Test
    public void accessLinkedIn () {
        driver.get("https://www.linkedin.com");
        System.out.println("Access " + driver.getTitle());
    }

    @AfterClass
    public void tearDown(){
        driver.quit();
    }
}
