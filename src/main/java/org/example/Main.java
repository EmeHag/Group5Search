package org.example;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import java.io.FileReader;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.openqa.selenium.chrome.ChromeOptions;

public class Main {

    public static void main(String[] args) throws InterruptedException {

        JSONParser parser = new JSONParser();
        String username = "";
        String password = "";
        try {
            // Read the JSON file
            Object obj = parser.parse(new FileReader("Facebook.json"));
            JSONObject jsonObject = (JSONObject) obj;

            // Extract the username and password values
            username = (String) jsonObject.get("username");
            password = (String) jsonObject.get("password");
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        // Create ChromeOptions instance and add the --disable-notifications argument
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-notifications");
        options.addArguments("--start-maximized");

        // Set the path to the ChromeDriver executable
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\emeli\\chromedriver_win32\\chromedriver.exe");

        // Launch the Chrome browser
        WebDriver driver = new ChromeDriver(options);

        // Navigate to the Facebook login page
        driver.get("https://sv-se.facebook.com/");

        // Accept only necessary cookies
        WebElement button = driver.findElement(By.xpath("//button[text()='Tillåt endast nödvändiga cookies']"));
        button.click();

        // Enter the email address and password
        WebElement emailInput = driver.findElement(By.id("email"));
        emailInput.sendKeys(username);

        WebElement passwordInput = driver.findElement(By.id("pass"));
        passwordInput.sendKeys(password);

        // Click the login button
        WebElement loginButton = driver.findElement(By.name("login"));
        loginButton.click();

        Thread.sleep(4000);

        // Click on the search bar, write "Java" and click enter to search
        var sok = driver.findElement(By.xpath("/html/body/div[1]/div/div[1]/div/div[2]/div[3]/div/div/div/div/div/label"));
        sok.click();
        sok.sendKeys("Java");
        sok.sendKeys(Keys.ENTER);

        Thread.sleep(4000);

        // Close the browser
        driver.quit();
    }
}