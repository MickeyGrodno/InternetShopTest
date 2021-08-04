package eu.senla.shabalin;

import com.codeborne.selenide.Browser;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
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
    }

    @BeforeEach
    public void beforeTest() {
        open(baseUrl);
        while(!title().equals("My Store")) {
            refresh();
        }
    }

    @AfterEach
    public void afterTest() {
        closeWindow();
    }
}
