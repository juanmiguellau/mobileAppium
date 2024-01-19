import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.screenrecording.CanRecordScreen;
import org.apache.commons.io.FileUtils;
import org.example.TestReport;
import org.example.Utils;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;

import static org.openqa.selenium.By.id;


public class RunnerTest {


    static AppiumDriver<MobileElement> appiumDriver;
    static Utils utils;
    static ExtentSparkReporter spark;
    static ExtentReports extent;
    static ExtentTest test;
    static TestReport testReport;

    @BeforeAll
    public static void beforeAll() {

        try {
            DesiredCapabilities caps = new DesiredCapabilities();

            // Android
            caps.setCapability(MobileCapabilityType.DEVICE_NAME, "emulator-5554");
            caps.setCapability(MobileCapabilityType.PLATFORM_NAME, "Android");
            caps.setCapability(MobileCapabilityType.AUTOMATION_NAME, "UiAutomator2");
            caps.setCapability(MobileCapabilityType.APP, "//Users//juanmi//app.apk");
            // iOS
            //caps.setCapability(MobileCapabilityType.DEVICE_NAME, "iPhone 14 Pro Max");
            //caps.setCapability(MobileCapabilityType.PLATFORM_VERSION, "16.4");
            //caps.setCapability(MobileCapabilityType.PLATFORM_NAME, "iOS");
            //caps.setCapability(MobileCapabilityType.AUTOMATION_NAME, "XCuiTest");
            //caps.setCapability(MobileCapabilityType.APP, "//Users//juanmi//Downloads//ATest.app");

            URL appiumServer = new URL("http://127.0.0.1:4723");
            appiumDriver = new AppiumDriver<MobileElement>(appiumServer, caps);
            utils = new Utils(appiumDriver);
            testReport=new TestReport(appiumDriver);
            spark = new ExtentSparkReporter(System.getProperty("user.dir") + "/test-output/STMExtentReport.html");
            extent = new ExtentReports();
            extent.attachReporter(spark);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    @AfterAll
    public static void afterAll()  {
        extent.flush();

    }

    public static Utils getUtils() {
        return utils;
    }
    /**
    public static TestReport getTestReport() {
        return testReport;
    }

     */

    public static String captureFile(AppiumDriver<MobileElement> driver) throws IOException {
        File file = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        File dest = new File("src/../BImages/" + System.currentTimeMillis() + ".png");
        String errFilePath = dest.getAbsolutePath();
        FileUtils.copyFile(file, dest);
        return errFilePath;
    }

    public void writeText(String message, String messageFail, By locator, String key) throws IOException {
        //MobileElement usernameElement = utils.find(locator);
        assertionCustom(message, messageFail, utils.find(locator) != null);
        utils.find(locator).sendKeys(key);
    }

    public void clickElement(String message, String messageFail,By locator) throws IOException {
        assertionCustom(message,messageFail, utils.find(locator) !=null);
        utils.find(locator).click();
    }

    public void assertionGetText(String text, By locator) {
        assertionCustom("Text Element Found", "Text Element Not Found",utils.find(locator) !=null);
        assertEquals("Text equal","Text Not Equal", text,locator,utils.find(locator).getText().equals(text));
    }

    public void assertEquals(String message, String messageFail,String text,By locator, boolean condition) {
        if (condition) {
            pass(message);
        }else {
            fail(messageFail);
            Assertions.assertEquals(text,utils.find(locator).getText()); //serve per far apparire il fail su console altrimenti viene segnato come passato anche se il test fallisce
        }
    }

    public void assertionCustom(String message, String messageFail, boolean condition) {
        if (condition) {
            pass(message);
        } else {
            fail(messageFail);
        }
    }

    private static void pass(String message) {
        test.pass(message);
    }

    public static String captureByteArray(AppiumDriver<MobileElement> driver) {
        return (((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64));
    }

    public static void fail(String message) {
        test.fail(message, MediaEntityBuilder.createScreenCaptureFromBase64String(captureByteArray(appiumDriver)).build());
        // test.fail( (Markup) test.addScreenCaptureFromPath(capture(appiumDriver)));
    }
}
