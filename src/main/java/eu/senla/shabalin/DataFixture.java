package eu.senla.shabalin;

import com.codeborne.selenide.Browser;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.junit.jupiter.params.provider.Arguments;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.stream.Stream;

import static com.codeborne.selenide.Selenide.*;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class DataFixture {
    protected static String correctEmail;
    protected static String correctPassword;
    protected static String incorrectPassword;
    protected static String baseUrl;
    protected static String authenticationFailedUrl = "http://automationpractice.com/index.php?controller=authentication";
    protected static String accountPage = "http://automationpractice.com/index.php?controller=my-account";

    @BeforeAll
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
        return Stream.of(
                arguments("chrome", "91.0"),
                arguments("firefox", "90.0")
        );
    }

    @AfterEach
    public void afterTest() {
        closeWindow();
    }
}
