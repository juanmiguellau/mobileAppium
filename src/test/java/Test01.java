import com.aventstack.extentreports.util.Assert;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import org.example.TestReport;
import org.example.Utils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.io.IOException;

import static org.openqa.selenium.By.className;
import static org.openqa.selenium.By.id;

public class Test01 extends RunnerTest {

    private final Utils utils = getUtils();
    //private final TestReport testReport = getTestReport();
    By username = id("com.ziviello.AutomationTest:id/username");
    By password = id("com.ziviello.AutomationTest:id/password");
    By btnLogin = id("com.ziviello.AutomationTest:id/btnLogin");
    By welcomeText = id("com.ziviello.AutomationTest:id/txtWelcome");
    By testError = id("android:id/alertTitle");
    By btnOk = id("android:id/button1");
    By btnBack = className("android.widget.ImageButton");

    @Test
    public void test01() throws IOException {

        test = extent.createTest("Login Access");
        test.info("check login access");
        writeText("Username Element found","username Element not found",username, "admin");
        writeText("Password Element Found","Password Element Not Found",password,"admin");
        clickElement("Button login found","Button Login Not Found",btnLogin);
        assertionGetText("Benvenuto: admi",welcomeText);
        clickElement("Button Back found","Button back Not Found",btnBack);
        test.pass("test success");
        //Assertions.fail();
    }

    @Test
    public void test_02() throws IOException {
            test = extent.createTest("Login Error");
            test.info("start check login error");
            clickElement("Button login found","Button Login Not Found",btnLogin);
            assertionGetText("Errore", testError);
            clickElement("Ok Element Found", "Ok Element Not Found",btnOk);
            test.pass("test success");
    }
}
