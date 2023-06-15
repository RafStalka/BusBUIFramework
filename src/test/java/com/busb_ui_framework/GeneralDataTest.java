package com.busb_ui_framework;

import com.busb_ui_framework.driverConfig.DriverFactory;
import com.busb_ui_framework.general.Configuration;
import com.busb_ui_framework.pages.MainPage;
import io.qameta.allure.Description;
import org.openqa.selenium.*;
import org.openqa.selenium.io.FileHandler;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import static com.busb_ui_framework.general.ConfigurationManager.configuration;
import static com.busb_ui_framework.pages.MainPage.getMainPage;

public class GeneralDataTest {

    protected WebDriver driver;
    protected Configuration configuration;
    MainPage mainPage;

    @BeforeMethod
    public void setUp() {
        Reporter.log("=====Browser Session Started=====", true);
        configuration = configuration();
        driver = new DriverFactory().createInstance(configuration().browser());
        driver.navigate().to(configuration().url());
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(10));
        Reporter.log("=====Application Started=====", true);

        mainPage = getMainPage(driver);
    }

    @AfterMethod
    public void tearDown(ITestResult testResult) {
        if(ITestResult.FAILURE == testResult.getStatus()) {
            TakesScreenshot screenshot = (TakesScreenshot) driver;
            File source = screenshot.getScreenshotAs(OutputType.FILE);
            LocalDateTime timeNow = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH-mm-ss");
            File destination = new File(System.getProperty("user.dir") +
                    "/resources/screenshots/" +
                    testResult.getName() + " ==> " + formatter.format(timeNow) + ".png");

            try {
                FileHandler.copy(source, destination);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
        driver.quit();
    }

    @Description("Taken data with 2 days in future")
    @Test
    public void pricesFirstDate() throws InterruptedException {

        driver.findElement(By.id("affiliate_checkbox")).click();

        WebElement sourceInput = driver.findElement(By.id("origin-c1ty-input"));
        WebElement destinationInput = driver.findElement(By.id("destination-c1ty-input"));

        // Enter source and destination values
        sourceInput.sendKeys("Source City");
        destinationInput.click();
        destinationInput.sendKeys("Destination City");

        // Select current date
        LocalDate currentDate = LocalDate.now();
        LocalDate futureDate = currentDate.plusDays(2);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String futureDateStr = formatter.format(futureDate);
        String currentDateStr = formatter.format(currentDate);
        System.out.println("Taken data with 2 days in future : " + futureDateStr);

        // Splitting future string date
        String splitterCurrentDate[] = currentDateStr.split("-");
        String current_month_year = splitterCurrentDate[1];
        String currentDay = splitterCurrentDate[0];


        // Splitting future string date
        String splitterFuture[] = futureDateStr.split("-");
        String month_year = splitterFuture[1];
        String day = splitterFuture[0];

        WebElement dateInput = driver.findElement(By.id("outbound-date-input"));
        dateInput.click();
        Thread.sleep(3000);

        List<WebElement> dayElements = driver.findElements(By.xpath("//*[@id='outbound-calendar']/div[3]/button[@tabindex='0']"));


        //*[@id='outbound-calendar']/div[3][contains(@data-month,'month-')]
        if (Objects.equals(month_year, current_month_year)) {
            //wyszukaj w tablicy dzien i kliknij
        } else {
            // wyszukaj element do zminy miesiąca i kliknij
            //wyszukaj w tablicy dzień i kliknij
        }

    }

    @Description("Taken data with 5 days in future")
    @Test
    public void pricesSecondDate() throws InterruptedException {

        driver.findElement(By.id("affiliate_checkbox")).click();

        WebElement sourceInput = driver.findElement(By.id("origin-c1ty-input"));
        WebElement destinationInput = driver.findElement(By.id("destination-c1ty-input"));

        // Enter source and destination values
        sourceInput.sendKeys("Source City");
        destinationInput.click();
        destinationInput.sendKeys("Destination City");

        // Select the date five days in the future
        LocalDate currentDate = LocalDate.now();
        LocalDate futureDate = currentDate.plusDays(5);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String futureDateStr = formatter.format(futureDate);
        String currentDateStr = formatter.format(currentDate);

        System.out.println("Taken data with 5 days in future : " + futureDateStr);

        // Splitting future string date
        String splitterCurrentDate[] = currentDateStr.split("-");
        String current_month_year = splitterCurrentDate[1];
        String currentDay = splitterCurrentDate[0];

        // Splitting string date
        String splitter[] = futureDateStr.split("-");
        String month_year = splitter[1];
        String day = splitter[0];

        WebElement dateInput = driver.findElement(By.id("outbound-date-input"));
        dateInput.click();
        Thread.sleep(3000);

        List<WebElement> dayElements = driver.findElements(By.xpath("//*[@id='outbound-calendar']/div[3]/button[@tabindex='0']"));


        //*[@id='outbound-calendar']/div[3][contains(@data-month,'month-')]
        if (Objects.equals(month_year, current_month_year)) {
            //wyszukaj w tablicy dzien i kliknij
        } else {
            // wyszukaj element do zminy miesiąca i kliknij
            //wyszukaj w tablicy dzień i kliknij
        }

    }
    @Description("Taken data with 20 days in future")
    @Test
    public void pricesThirdDate() throws InterruptedException {

        driver.findElement(By.id("affiliate_checkbox")).click();

        WebElement sourceInput = driver.findElement(By.id("origin-c1ty-input"));
        WebElement destinationInput = driver.findElement(By.id("destination-c1ty-input"));

        // Enter source and destination values
        sourceInput.sendKeys("Source City");
        destinationInput.click();
        destinationInput.sendKeys("Destination City");

        // Select the date twenty days in the future
        LocalDate currentDate = LocalDate.now();
        LocalDate futureDate = currentDate.plusDays(20);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String futureDateStr = formatter.format(futureDate);
        String currentDateStr = formatter.format(currentDate);

        System.out.println("Taken data with 20 days in future : " + futureDateStr);

        // Splitting future string date
        String splitterCurrentDate[] = currentDateStr.split("-");
        String current_month_year = splitterCurrentDate[1];
        String currentDay = splitterCurrentDate[0];

        // Splitting string date
        String splitter[] = futureDateStr.split("-");
        String month_year = splitter[1];
        String day = splitter[0];

        WebElement dateInput = driver.findElement(By.id("outbound-date-input"));
        dateInput.click();
        Thread.sleep(3000);

        List<WebElement> dayElements = driver.findElements(By.xpath("//*[@id='outbound-calendar']/div[3]/button[@tabindex='0']"));


        //*[@id='outbound-calendar']/div[3][contains(@data-month,'month-')]
        if (Objects.equals(month_year, current_month_year)) {
            //wyszukaj w tablicy dzien i kliknij
        } else {
            // wyszukaj element do zminy miesiąca i kliknij
            //wyszukaj w tablicy dzień i kliknij
        }

    }
    @Description("Taken data with 40 days in future")
    @Test
    public void pricesFourthDate() throws InterruptedException {

        driver.findElement(By.id("affiliate_checkbox")).click();

        WebElement sourceInput = driver.findElement(By.id("origin-c1ty-input"));
        WebElement destinationInput = driver.findElement(By.id("destination-c1ty-input"));

        // Enter source and destination values
        sourceInput.sendKeys("Source City");
        destinationInput.click();
        destinationInput.sendKeys("Destination City");

        // Select the date forty days in the future
        LocalDate currentDate = LocalDate.now();
        LocalDate futureDate = currentDate.plusDays(40);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String futureDateStr = formatter.format(futureDate);
        String currentDateStr = formatter.format(currentDate);

        System.out.println("Taken data with 40 days in future : " + futureDateStr);

        // Splitting future string date
        String splitterCurrentDate[] = currentDateStr.split("-");
        String current_month_year = splitterCurrentDate[1];
        String currentDay = splitterCurrentDate[0];

        // Splitting string date
        String splitter[] = futureDateStr.split("-");
        String month_year = splitter[1];
        String day = splitter[0];

        WebElement dateInput = driver.findElement(By.id("outbound-date-input"));
        dateInput.click();
        Thread.sleep(3000);

        List<WebElement> dayElements = driver.findElements(By.xpath("//*[@id='outbound-calendar']/div[3]/button[@tabindex='0']"));


        //*[@id='outbound-calendar']/div[3][contains(@data-month,'month-')]
        if (Objects.equals(month_year, current_month_year)) {
            //wyszukaj w tablicy dzien i kliknij
        } else {
            // wyszukaj element do zminy miesiąca i kliknij
            //wyszukaj w tablicy dzień i kliknij
        }

    }
}
