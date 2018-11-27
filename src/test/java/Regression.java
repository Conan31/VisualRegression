import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class Regression {
    WebDriver driver;

    @BeforeClass
    public void beforeClass()
    {
        try {
            FileUtils.cleanDirectory(new File(System.getProperty("user.dir") + "/src/images/screenshots"));
            FileUtils.cleanDirectory(new File(System.getProperty("user.dir") + "/src/images/diffimages"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        driver = new ChromeDriver();
        driver.get("http://newtours.demoaut.com/");

        try
        {
            Thread.sleep(3000);
        } catch (InterruptedException e){}

        driver.findElement(By.name("userName")).sendKeys("tutorial");
        driver.findElement(By.name("password")).sendKeys("tutorial");
        driver.findElement(By.name("login")).click();
    }

    @DataProvider(name="urls")
    public static Object[][] urls(){

        return new Object[][] {

                {"http://newtours.demoaut.com/mercurywelcome.php", "homePage"},
                {"http://newtours.demoaut.com/mercuryreservation.php", "reservationPage"},
                {"http://newtours.demoaut.com/mercuryreservation2.php","reservationPage2"},
                {"http://newtours.demoaut.com/mercurypurchase.php", "purchasePage"}
        };
    }

    @Test(dataProvider = "urls")
    public void regression(String url, String name)
    {
        driver.get(url);

        new ScreenCaptureUtility().takePageScreenshot(driver, name);
        Assert.assertTrue(new ScreenCaptureUtility().areImageEqual(name, name));

    }

    @AfterClass
    public void afterClass()
    {
      driver.quit();
    }
}
