
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;

public class Regression {
    WebDriver driver;
    ExtentReports report;
    ExtentTest test;

    @BeforeClass
    public void beforeClass()
    {
        report = new ExtentReports(System.getProperty("user.dir") + "/report/Report.html");

        try {
            FileUtils.cleanDirectory(new File(System.getProperty("user.dir") + "/src/images/screenshots"));
            FileUtils.cleanDirectory(new File(System.getProperty("user.dir") + "/src/images/diffimages"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir")+"/src/drivers/chromedriver");
        driver = new ChromeDriver();
        driver.get("http://newtours.demoaut.com/");
        driver.manage().timeouts().implicitlyWait(10,TimeUnit.SECONDS);
        driver.manage().window().maximize();

        try
        {
            Thread.sleep(3000);
        } catch (InterruptedException e){}

        driver.findElement(By.name("userName")).sendKeys("tutorial");
        driver.findElement(By.name("password")).sendKeys("tutorial");
        driver.findElement(By.name("login")).click();
    }

    @BeforeMethod
    public void beforeMethod(Method method)
    {
        test = report.startTest(method.getName());
    }

    @DataProvider(name="urls")
    public static Object[][] urls(){

        return new Object[][]{

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
        driver.manage().timeouts().implicitlyWait(10,TimeUnit.SECONDS);
        driver.manage().window().maximize();

        new ScreenCaptureUtility().takePageScreenshot(driver, name);
        Assert.assertTrue(new ScreenCaptureUtility().areImageEqual(name, name));

    }

    @AfterMethod
    public void afterMethod(ITestResult result)
    {
        if(result.getStatus()==ITestResult.SUCCESS)
        {
            test.log(LogStatus.PASS,"Test passed");
        }

        if(result.getStatus()==ITestResult.FAILURE)
        {
            test.log(LogStatus.FAIL,"Test failed");
            //test.log(LogStatus.FAIL), result.getThrowable();
        }

        if(result.getStatus()==ITestResult.SKIP)
        {
            test.log(LogStatus.SKIP,"Test skipped");
        }
    }

    @AfterClass
    public void afterClass()
    {
      driver.quit();

      report.endTest(test);
      report.flush();
    }
}
