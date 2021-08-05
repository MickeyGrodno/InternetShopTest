package eu.senla.shabalin;

import com.codeborne.selenide.Configuration;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import static com.codeborne.selenide.Selenide.*;

public class DataFixture {
    protected static String correctEmail;
    protected static String correctPassword;
    protected static String incorrectPassword;
    protected static String baseUrl;
    protected static String authenticationFailedUrl = "http://automationpractice.com/index.php?controller=authentication";
    protected static String accountPage = "http://automationpractice.com/index.php?controller=my-account";

    @BeforeSuite
    public static void beforeAllTest() {
        String propertyFileDirectory;
        if (System.getProperty("os.name").equals("Linux")) {
            propertyFileDirectory = "src/main/resources/testdata.properties";
        } else {
            propertyFileDirectory = "src\\main\\resources\\testdata.properties";
        }
        Properties properties = new Properties();
        try {
            properties.load(new FileInputStream(propertyFileDirectory));
            correctEmail = properties.getProperty("correctEmail");
            correctPassword = properties.getProperty("correctPassword");
            incorrectPassword = properties.getProperty("incorrectPassword");
            baseUrl = properties.getProperty("baseUrl");
        } catch (IOException e) {
            System.err.println("Property file not found!");
            e.printStackTrace();
        }
//        Configuration.headless = true;
        Configuration.timeout = 8000;
//        Configuration.remote = "http://localhost:4444/wd/hub/";
        Configuration.browserSize = "1920x1080";
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("browserName", "chrome");
        capabilities.setCapability("browserVersion", "91");
        capabilities.setCapability("enableVNC", true);
        Configuration.browser = "chrome";
        Configuration.browserCapabilities = capabilities;

    }

    @BeforeMethod
    public void setCapabilitiesByArguments() {
        open(baseUrl);
        while(!title().equals("My Store")) {
            refresh();
        }
    }

    @AfterMethod
    public void afterTest() {
        closeWindow();
    }
}
