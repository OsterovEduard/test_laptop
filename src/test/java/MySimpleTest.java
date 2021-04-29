import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import static org.openqa.selenium.support.ui.ExpectedConditions.presenceOfElementLocated;

public class MySimpleTest {

    private static ChromeDriver chromeDriver;
    private static WebDriverWait wait;

    @BeforeAll
    static void setUp() {
        System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver.exe");
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--kiosk");
        chromeDriver = new ChromeDriver(options);
        wait = new WebDriverWait(chromeDriver, 10);


    }

    @AfterAll
    static void tearDown() {
        chromeDriver.quit();
    }


    @Test
    void myBeautifulTest() throws IOException {
        chromeDriver.get("https://wwww.yandex.ru/");
        chromeDriver.manage().window().fullscreen();

        chromeDriver.findElement(By.xpath("//a[@data-id='market']")).click();
        String oldTab = chromeDriver.getWindowHandle();
        ArrayList<String> newTab = new ArrayList<>(chromeDriver.getWindowHandles());
        newTab.remove(oldTab);
        chromeDriver.switchTo().window(newTab.get(0));

        wait.until(presenceOfElementLocated(By.xpath("//input[@placeholder='Искать товары']")));
        chromeDriver.findElement(By.xpath("//input[@placeholder='Искать товары']")).sendKeys("ноутбук xiaomi redmibook");

        chromeDriver.findElement(By.xpath("//span[text()='Найти']")).click();

        chromeDriver.findElement(By.xpath("//div[@title='Сначала предложения в моём регионе']")).click();

        saveScreen(chromeDriver.getScreenshotAs(OutputType.BYTES));
    }

    void saveScreen(byte[] bytes) throws IOException {
        File file = new File("src/test/java/screen.png");
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        fileOutputStream.write(bytes);
        fileOutputStream.close();
    }
}

