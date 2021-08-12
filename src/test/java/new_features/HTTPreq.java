package new_features;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.v91.network.Network;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.Optional;

public class HTTPreq{

    private WebDriver driver;
    private DevTools devTools;
    //private String url = "https://www.lego.com/404";
    private String url = "https://www.saucedemo.com/";

    @BeforeClass
    public void setUp () {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
    }

    @Test
    public void CaptureHTTPRequests () {

        // Get The DevTools And Create A Session
        devTools = ((ChromeDriver)this.driver).getDevTools();
        devTools.createSession();

        devTools.send(Network.enable(Optional.empty(), Optional.empty(), Optional.empty()));
        devTools.addListener(Network.requestWillBeSent(),
                entry -> {
                    System.out.println("Request URI ::: " + entry.getRequest().getUrl());
                    System.out.println("Method TYPE ::: " + entry.getRequest().getMethod());
                });

        try {
            Thread.sleep(6000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Load The AUT
        driver.get(url);
        devTools.send(Network.disable());

    }

    @AfterClass
    public void tearDown(){
        driver.quit();
    }
}
