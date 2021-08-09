package eu.senla.shabalin;

import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.provider.Arguments;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Properties;
import java.util.stream.Stream;

import static com.codeborne.selenide.Selenide.*;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class DataFixture {
    protected static String propertyFileDirectory;
    protected static String correctEmail;
    protected static String correctPassword;
    protected static String incorrectPassword;
    protected static String baseUrl;
    protected static String authenticationFailedUrl = "http://automationpractice.com/index.php?controller=authentication";
    protected static String accountPage = "http://automationpractice.com/index.php?controller=my-account";

    @BeforeAll
    public static void beforeAllTest() {
        if (System.getProperty("os.name").equals("Linux")) {
            propertyFileDirectory = "src/main/resources/";
        } else {
            propertyFileDirectory = "src\\main\\resources\\";
        }
        Properties properties = new Properties();
        try(FileInputStream fis = new FileInputStream(propertyFileDirectory+"testdata.properties")) {
            properties.load(fis);
        } catch (IOException e) {
            System.err.println("Property file not found!");
            e.printStackTrace();
        }
        correctEmail = properties.getProperty("correctEmail");
        correctPassword = properties.getProperty("correctPassword");
        incorrectPassword = properties.getProperty("incorrectPassword");
        baseUrl = properties.getProperty("baseUrl");

        Configuration.headless = true;
        Configuration.timeout = 8000;
        Configuration.remote = "http://localhost:4444/wd/hub/";
        Configuration.browserSize = "1920x1080";
    }

    public void setCapabilitiesByArguments(String browserName, String browserVersion) {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("browserName", browserName);
        capabilities.setCapability("browserVersion", browserVersion);
        capabilities.setCapability("enableVNC", true);
        Configuration.browser = browserName;
        Configuration.browserCapabilities = capabilities;
        open(baseUrl);
        while(!title().equals("My Store")) {
            refresh();
        }
    }

    static Stream<Arguments> browserArguments() {
        Properties properties = new Properties();
        try(FileInputStream fis = new FileInputStream(propertyFileDirectory+"browsers.properties")) {
            properties.load(fis);
        } catch (IOException e) {
            System.err.println("Property file not found!");
            e.printStackTrace();
        }

        return properties.entrySet().stream().map((entry -> arguments(entry.getKey(), entry.getValue())));
    }

    @AfterEach
    public void afterTest() {
        closeWindow();
    }
}
