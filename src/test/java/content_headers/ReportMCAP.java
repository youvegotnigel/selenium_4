package content_headers;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.v108.network.Network;
import org.openqa.selenium.devtools.v108.network.model.Headers;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Optional;
import java.util.Set;

public class ReportMCAP {

    private ChromeDriver driver;
    private DevTools devTools;
    private static final String REPORT_URL = "http://mcap2212php8.vh.local/export_pdf_report/MTIyMjlkZDA0NWRjYTIzNzRmZmEyYTlkYzg2YzI4N2O8ZElwq4vYfIpJK-r7kw9BV3J4YzZsVVV3OTZsRWxtakN6U2Y2OEZQTGV6d1NZdmFlRVkwN2FydUpYY0EyY3FCNDhVRy81eWliYVFXUkpTaFZaN3VvRTViM2ZqVHhTYXRZOStCVDRZSWpsMWpNNTNndGVKY3ZtWURwb0MwNnQzWGxlZlFhZEJseFV2dllwNG1FVFNOaXJDQU5LMzhOZHNlZHIrcURsUjEyaXJuOVlPeUtZeEc5UW9GY1BobEtXOWNEamNSa1NJRnVBYW53ekJOT0wwRHlrWTZsR1dZV0RrWkdrQVNJRTRhNmRTeHY3R0dlZE5kT2VMOVJsa3dhNkhZVjVPV3ZCSnNLU2NIZThsOVVYbzROdzAwSTFMbjNTQWZwYlJCRkZVRkdWUmVxU21LSnRCWVhTVkhaRHE3d3ZGR05VTkxtVnY2aVE2RlgxSThmZkx2VEIydnR5aXlQSUdGc1YzRUxIazNXYlpVWklSVlJ4ODR5Kyt2Sm5HZXUvamdtbWhTeGJLQUxjbVI5bzhUc21oWUk0eE1DU3YzVjVweEd5Q01UNzhkRWE2U2hVaDdlZHE2N21jNVNWalZHdXpBUkFoK3A4Q0JnN2NpVC9RT2tDZVUwcHNlTklUSExuYUFwUGIwWWc9PQ";
    private static final String COOKIE_VALUE = "v0dd3p9kjej4e7idt51k0u7bi2";

    @BeforeClass
    public void set_up() {

        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.get(REPORT_URL);
        driver.manage().window().maximize();

        Cookie mycookie = new Cookie("MCAPFE2212php8", COOKIE_VALUE);
        driver.manage().addCookie(mycookie);

        driver.navigate().refresh();
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        Set<Cookie> cookiesList = driver.manage().getCookies();
        for (Cookie getcookies : cookiesList) {
            System.out.println(getcookies + "\n");
        }

    }

    @Test(priority = 1)
    public void print_content_headers() {

        driver.navigate().to("https://www.seleniumhq.org/");
        devTools = driver.getDevTools();
        devTools.createSession();
        devTools.send(Network.enable(Optional.empty(), Optional.empty(), Optional.empty()));
        driver.navigate().refresh();
        devTools.addListener(Network.responseReceived(),
                responseReceived -> {
                    Headers headers = responseReceived.getResponse().getHeaders();
                    if (!headers.isEmpty()) {
                        System.out.println("Headers:");
                        headers.forEach((key, value) -> {
                            System.out.println("    " + key + "=" + value);
                        });
                    }else{
                        System.out.println("NO HEADERS");
                    }
                });

    }

    @Test(priority = 1)
    public void network_response(){

        try {
            HttpURLConnection cn= (HttpURLConnection)new URL(REPORT_URL).openConnection();

            cn.setRequestMethod("HEAD");
            cn.connect();

            System.out.println("Http response code: " + cn.getResponseCode());
            System.out.println("Http response Message: " + cn.getHeaderFields());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test(priority = 2)
    public void print_pdf_content(){

        System.out.println(readPdfContent(REPORT_URL));
    }

    public static String readPdfContent(String pdf_url){

        String pdf_content = "";
        try{

            URL pdfUrl = new URL(pdf_url);

            InputStream is = pdfUrl.openStream();
            BufferedInputStream fileParse = new BufferedInputStream(is);

            PDDocument pdfDocument = PDDocument.load(fileParse);
            pdf_content = new PDFTextStripper().getText(pdfDocument);
            System.out.println(pdf_content);

        }catch (Exception e) {
            System.out.println(pdf_content);
            e.printStackTrace();
        }
        return pdf_content;
    }

    @AfterClass
    public void teardown() {
        driver.quit();
    }
}
