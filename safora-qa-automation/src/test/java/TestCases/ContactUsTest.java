package TestCases;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class ContactUsTest {

    WebDriver driver;
    WebDriverWait wait;

    @BeforeMethod
    public void setup() {

        driver = new ChromeDriver();
        driver.manage().window().maximize();

        wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        driver.get("https://safora.se/en/contact.html");

    }

    public void fillForm(String name, String email,
                         String phone, String message) {

        driver.findElement(By.name("name")).clear();
        driver.findElement(By.name("name")).sendKeys(name);

        driver.findElement(By.name("email")).clear();
        driver.findElement(By.name("email")).sendKeys(email);

        driver.findElement(By.name("phone")).clear();
        driver.findElement(By.name("phone")).sendKeys(phone);

        driver.findElement(By.name("message")).clear();
        driver.findElement(By.name("message")).sendKeys(message);
    }

    public void handleCaptcha() throws InterruptedException {

        wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(
                By.xpath("//iframe[contains(@title,'reCAPTCHA')]")));

        driver.findElement(By.id("recaptcha-anchor")).click();

        driver.switchTo().defaultContent();

        System.out.println("Solve CAPTCHA manually if image challenge appears...");

        Thread.sleep(30000);
    }

    public void clickSubmit() {

        driver.findElement(
                By.xpath("//button[@type='submit']")).click();
    }

    @Test(priority = 1)
    public void validFormSubmission() throws InterruptedException {

       fillForm(
                "Test User",
                "test@gmail.com",
                "0764812345",
                "Hello support team, this is a test message."
        );

        handleCaptcha();

        clickSubmit();

        WebElement successPopup = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.id("swal2-title")));

        Assert.assertEquals(
                successPopup.getText(),
                "Success!");

        WebElement successMessage = driver.findElement(
                By.id("swal2-html-container"));

        Assert.assertEquals(
                successMessage.getText(),
                "Your message has been sent successfully.");

        driver.findElement(
                        By.xpath("//button[contains(@class,'swal2-confirm')]"))
                .click();
    }


    @Test(priority = 2)
    public void invalidEmailValidation() throws InterruptedException {

        fillForm(
                "Test User",
                "invalidemail.com",
                "0764812345",
                "Test message"
        );

        handleCaptcha();

        clickSubmit();

        WebElement error = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.xpath("//*[contains(text(),'valid email')]")));

        Assert.assertTrue(error.isDisplayed());
    }

    @Test(priority = 3)
    public void emptyFormValidation() throws InterruptedException {

        handleCaptcha();

        clickSubmit();

        Assert.assertTrue(
                driver.getPageSource().contains("Full name is required."));

        Assert.assertTrue(
                driver.getPageSource().contains("Email address is required."));

        Assert.assertTrue(
                driver.getPageSource().contains("Phone number is required."));

        Assert.assertTrue(
                driver.getPageSource().contains("Message is required."));
    }

    @Test(priority = 4)
    public void invalidPhoneValidation() throws InterruptedException {

        fillForm(
                "Test User",
                "test@gmail.com",
                "123",
                "This is valid message"
        );

        handleCaptcha();

        clickSubmit();

        Assert.assertTrue(
                driver.getPageSource()
                        .contains("Please enter a valid phone number"));
    }

    @Test(priority = 5)
    public void shortMessageValidation() throws InterruptedException {
//        scrollToForm();
        fillForm(
                "Test User",
                "test@gmail.com",
                "0764812345",
                "short"
        );

        handleCaptcha();

        clickSubmit();

        Assert.assertTrue(
                driver.getPageSource()
                        .contains("Message must be at least 10 characters."));
    }

    @Test(priority = 6)
    public void specialCharacterNameValidation() throws InterruptedException {

        fillForm(
                "@@@###",
                "test@gmail.com",
                "0764812345",
                "This is valid message"
        );

        handleCaptcha();

        clickSubmit();

        Assert.assertTrue(
                driver.getPageSource().contains("name")
                        || driver.getPageSource().contains("invalid"));
    }

    @Test(priority = 7)
    public void singleCharacterNameTest() throws InterruptedException {

        fillForm(
                "a",
                "test@gmail.com",
                "0764812345",
                "This is valid message"
        );

        handleCaptcha();

        clickSubmit();

        WebElement popup = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.id("swal2-title")));

        Assert.assertEquals(
                popup.getText(),
                "Success!");
    }

    @AfterMethod
    public void tearDown() {

        driver.quit();
    }
}