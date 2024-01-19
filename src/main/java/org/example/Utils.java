package org.example;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Utils {

    WebDriverWait wait;

    AppiumDriver<MobileElement> appiumDriver;

    public Utils (AppiumDriver<MobileElement> driver) {
        wait=new WebDriverWait(driver, 10);
    }


    public MobileElement find(By locator) {
        try {
           return (MobileElement) wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        }catch (Exception ex) {
            return null;
        }
    }
}
