package org.example;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.remote.MobileCapabilityType;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class TestReport extends Utils {
    ExtentTest test;
    ExtentSparkReporter spark;
    ExtentReports extent;
    AppiumDriver<MobileElement> appiumDriver;

    public TestReport(AppiumDriver<MobileElement> driver) {
        super(driver);
    }

    public void setUp() {

        try {
            DesiredCapabilities caps = new DesiredCapabilities();
            caps.setCapability(MobileCapabilityType.DEVICE_NAME, "emulator-5554");
            caps.setCapability(MobileCapabilityType.PLATFORM_NAME, "Android");
            caps.setCapability(MobileCapabilityType.AUTOMATION_NAME, "UiAutomator2");
            caps.setCapability(MobileCapabilityType.APP, "//Users//juanmi//app.apk");

            URL appiumServer = new URL("http://127.0.0.1:4723/wd/hub");
            appiumDriver = new AppiumDriver<MobileElement>(appiumServer, caps);
            spark = new ExtentSparkReporter(System.getProperty("user.dir") + "/test-output/STMExtentReport.html");
            extent = new ExtentReports();
            extent.attachReporter(spark);
            extent.flush();


        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public  void writeText(By locator, String key) throws IOException {
        //MobileElement usernameElement = utils.find(locator);
        find(locator);
        assertionCustom("Username Element found", "username Element not found",find(locator) != null);
        find(locator).sendKeys(key);
    }

    public  void assertionCustom(  String message, String messageFail, boolean condition) throws IOException {
        if(condition) {
            pass(message);
        } else {
            fail(messageFail);}
    }

    private void pass(String message) {
        test.pass("login access success");
    }

    public static String captureByteArray(AppiumDriver<MobileElement> driver) {
        return (((TakesScreenshot)driver).getScreenshotAs(OutputType.BASE64));
    }

    public void fail(String message)throws IOException {
       test.fail(message, MediaEntityBuilder.createScreenCaptureFromBase64String(captureByteArray(appiumDriver)).build());
        // test.fail( (Markup) test.addScreenCaptureFromPath(capture(appiumDriver)));
    }
}