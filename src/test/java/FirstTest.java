import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.concurrent.TimeUnit;

public class FirstTest {
    WebDriver driver;

    @DataProvider(name="urls")
    public static Object[][] urls(){

        return new Object[][] {

                {"http://newtours.demoaut.com/mercurywelcome.php", "homePageImpr"},
                {"http://newtours.demoaut.com/mercuryreservation.php", "reservationPageImpr"},
                {"http://newtours.demoaut.com/mercuryreservation2.php","reservationPage2Impr"},
                {"http://newtours.demoaut.com/mercurypurchase.php", "purchasePageImpr"}
        };
    }

    @Test(dataProvider = "urls")
    public void prepareBaseline(String url, String name)
    {
        driver = new ChromeDriver();
        driver.get("http://newtours.demoaut.com/");
        //driver.manage().timeouts().implicitlyWait(10,TimeUnit.SECONDS);
        //driver.manage().window().maximize();

        driver.findElement(By.name("userName")).sendKeys("tutorial");
        driver.findElement(By.name("password")).sendKeys("tutorial");
        driver.findElement(By.name("login")).click();

        driver.get(url);
        new ScreenCaptureUtility().prepareBaseline(driver, name);
        driver.close();
    }
    @Test
    public void compareImages()
    {
        System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir")+"/src/drivers/chromedriver");
        driver = new ChromeDriver();
        driver.get("http://newtours.demoaut.com/");
        //driver.manage().timeouts().implicitlyWait(10,TimeUnit.SECONDS);
        //driver.manage().window().maximize();

        new ScreenCaptureUtility().takePageScreenshot(driver, "srcHomePage");
        Assert.assertTrue(new ScreenCaptureUtility().areImageEqual("homePage", "srcHomePage"));

        driver.close();
    }
    @Test
    public void compareImagesToFail()
    {
        System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir")+"/src/drivers/chromedriver");
        driver = new ChromeDriver();
        driver.get("http://newtours.demoaut.com/");
        //driver.manage().timeouts().implicitlyWait(10,TimeUnit.SECONDS);
        //driver.manage().window().maximize();

        driver.findElement(By.name("userName")).sendKeys("tutorial");
        new ScreenCaptureUtility().takePageScreenshot(driver, "srcHomePage");
        Assert.assertTrue(new ScreenCaptureUtility().areImageEqual("homePage", "srcHomePage"));

        driver.close();
    }
    @Test
    public void test1()
    {
        System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir")+"/src/drivers/chromedriver");
        driver = new ChromeDriver();
        driver.get("http://newtours.demoaut.com/");
        //driver.manage().timeouts().implicitlyWait(10,TimeUnit.SECONDS);
        //driver.manage().window().maximize();

        new ScreenCaptureUtility().takePageScreenshot(driver, "myImage1");
        driver.close();

    }
    @Test
    public void test2()
    {
        System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir")+"/src/drivers/chromedriver");
        driver = new ChromeDriver();
        driver.get("http://newtours.demoaut.com/");
        //driver.manage().timeouts().implicitlyWait(10,TimeUnit.SECONDS);
        //driver.manage().window().maximize();
        WebElement logo = driver.findElement(By.xpath("//img[@alt='Mercury Tours']"));

        new ScreenCaptureUtility().takeElementScreenshot(driver, "logoImage", logo);

        driver.close();
        driver.quit();
    }

}
