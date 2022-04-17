package new_features;

import com.google.common.util.concurrent.Uninterruptibles;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.commons.codec.binary.Base64;
import org.junit.AfterClass;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.v99.network.Network;
import org.openqa.selenium.devtools.v99.network.model.Headers;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

public class BasicAuthentication {

    private WebDriver driver;
    DevTools devTools;

    private static final String username = "admin";
    private static final String password = "admin";

    @BeforeMethod
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
    }

    @Test
    public void testBasicAuthNewMethod(){

        devTools = ((ChromeDriver)this.driver).getDevTools();
        devTools.createSession();
        devTools.send(Network.enable(Optional.empty(), Optional.empty(), Optional.empty()));

        //send auth header
        Map<String, Object> headers = new HashMap<>();
        String basicAuth = "Basic " + new String(new Base64().encode(String.format("%s:%s",username,password).getBytes()));
        headers.put("Authorization", basicAuth);

        devTools.send(Network.setExtraHTTPHeaders(new Headers(headers)));
        driver.get("https://the-internet.herokuapp.com/basic_auth");
        Uninterruptibles.sleepUninterruptibly(5, TimeUnit.SECONDS);
        tearDown();

    }

    @Test
    public void testBasicAuthOldMethod(){
        driver.get("https://admin:admin@the-internet.herokuapp.com/basic_auth");
        tearDown();
    }

    @AfterClass
    public void tearDown() {
        driver.quit();
        System.out.println("quit driver");
    }
}
