package com.busb_ui_framework;

import com.busb_ui_framework.driverConfig.DriverFactory;
import com.busb_ui_framework.general.Configuration;
import com.busb_ui_framework.pages.MainPage;
import io.qameta.allure.Description;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.io.FileHandler;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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

    @Description("Taken data with first date")
    @Test
    public void pricesFirstDate() {

        driver.findElement(By.id("affiliate_checkbox")).click();
        driver.findElement(By.id("origin-c1ty-input")).click();
        driver.findElement(By.cssSelector(".search-jss32")).click();
        driver.findElement(By.id("origin-c1ty-input")).sendKeys("MEININGER Hotel Berlin Central Station");
        driver.findElement(By.id("destination-c1ty-input")).click();
        driver.findElement(By.xpath("//div[@id=\'option-37206215\']/div")).click();
        driver.findElement(By.id("destination-c1ty-input")).sendKeys("Hamburg Airport");
        driver.findElement(By.id("outbound-date-input")).click();
        driver.findElement(By.xpath("//button[12]/div/div")).click();
        driver.findElement(By.xpath("//div[2]/button/span")).click();

    }
}
