package new_features;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.v91.security.Security;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class UntrustedWebsite {

    private ChromeDriver driver;
    DevTools devTools;


    @BeforeClass
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        devTools = driver.getDevTools();
    }

    @Test
    public void loadBadWebsite () {
        devTools.createSession();
        devTools.send(Security.setIgnoreCertificateErrors(true));
        driver.get("https://self-signed.badssl.com");
    }

    @Test
    public void doNotLoadBadWebsite () {
        devTools.createSession();
        devTools.send(Security.setIgnoreCertificateErrors(false));
        driver.get("https://self-signed.badssl.com");
    }

    @AfterClass
    public void tearDown() {
        driver.quit();
    }

}
