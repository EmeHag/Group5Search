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

        var driver = openPageInChrome("https://sv-se.facebook.com/");

        //Calls the logIn method that is used to log in to Facebook
        logIn(credentials, driver);

        sleepForSeconds(3);

        //Calling the search method
        search("Java", driver);

        //Calling the sleep method
        sleepForSeconds(4);

        // Close the browser
        driver.quit();
    }

    //Reading the facebook credentails from the json file
    private static Credentials readCredentialsFromFile() {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(new File("src/main/resources/Facebook.json"), Credentials.class);
        } catch (IOException e) {
            logger.error("Kunde inte öppna filen", e);
            throw new RuntimeException(e);
        }
    }

    //Open the choosen page in Chrome Webdriver
    private static WebDriver openPageInChrome(String url) {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-notifications");
        options.addArguments("--start-maximized");

        //in second argument, insert path to executable chromedriver file
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\maria\\.cache\\selenium\\chromedriver\\win32\\112.0.5615.49\\chromedriver.exe");
        WebDriver driver = new ChromeDriver(options);
        driver.get(url);
        WebElement button = driver.findElement(By.xpath("//button[text()='Tillåt endast nödvändiga cookies']"));
        button.click();
        return driver;
    }


    //Logging in using the facebook credentials from the json file
    private static void logIn(Credentials credentials, WebDriver driver) {
        WebElement emailInput = driver.findElement(By.id("email"));
        emailInput.sendKeys(credentials.email());

        WebElement passwordInput = driver.findElement(By.id("pass"));
        passwordInput.sendKeys(credentials.password());

        WebElement loginButton = driver.findElement(By.name("login"));
        loginButton.click();
    }

    //Searching based on the input argument
    private static void search(String searchPhrase, WebDriver driver) {
        try {
            var search = driver.findElement(By.xpath("/html/body/div[1]/div/div[1]/div/div[2]/div[3]/div/div/div/div/div/label"));
            search.click();
            search.sendKeys(searchPhrase);
            search.sendKeys(Keys.ENTER);
        } catch (Exception e) {
            logger.error("Sökning kunde inte genomföras korrekt ", e);
            e.printStackTrace();
        }
    }

    //Pausing the program for chosen amount of seconds
    private static void sleepForSeconds(int s) {
        try {
            Thread.sleep(Duration.ofSeconds(s));
        } catch (InterruptedException e) {
            logger.error("Kunde inte pausa", e);
        }
    }
}