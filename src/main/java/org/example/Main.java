package org.example;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.time.Duration;

public class Main {

    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {

        var credentials = readCredentialsFromFile();

        // Create ChromeOptions instance and add the --disable-notifications argument
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-notifications");
        options.addArguments("--start-maximized");

        // Set the path to the ChromeDriver executable
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\maria\\.cache\\selenium\\chromedriver\\win32\\112.0.5615.49\\chromedriver.exe");

        // Launch the Chrome browser
        WebDriver driver = new ChromeDriver(options);

        // Navigate to the Facebook login page
        driver.get("https://sv-se.facebook.com/");

        // Accept only necessary cookies
        WebElement button = driver.findElement(By.xpath("//button[text()='Tillåt endast nödvändiga cookies']"));
        button.click();

        WebElement emailInput = driver.findElement(By.id("email"));
        emailInput.sendKeys(credentials.email());

        WebElement passwordInput = driver.findElement(By.id("pass"));
        passwordInput.sendKeys(credentials.password());

        WebElement loginButton = driver.findElement(By.name("login"));
        loginButton.click();

        sleepForSeconds(3);

        // Click on the search bar, write "Java" and click enter to search
        var sok = driver.findElement(By.xpath("/html/body/div[1]/div/div[1]/div/div[2]/div[3]/div/div/div/div/div/label"));
        sok.click();
        sok.sendKeys("Java");
        sok.sendKeys(Keys.ENTER);

        sleepForSeconds(4);

        // Close the browser
        driver.quit();
    }

    private static void sleepForSeconds(int s) {
        try {
            Thread.sleep(Duration.ofSeconds(s));
        } catch (InterruptedException e) {
            logger.error("Kunde inte pausa", e);
        }
    }

    private static Credentials readCredentialsFromFile() {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(new File("src/main/resources/Facebook.json"), Credentials.class);
        } catch (IOException e) {
            logger.error("Kunde inte öppna filen", e);
            throw new RuntimeException(e);
        }
    }
}