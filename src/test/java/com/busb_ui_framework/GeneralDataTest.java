package com.busb_ui_framework;

import com.busb_ui_framework.driverConfig.DriverFactory;
import com.busb_ui_framework.general.Configuration;
import com.busb_ui_framework.pages.MainPage;
import io.qameta.allure.Description;
import org.openqa.selenium.*;
import org.openqa.selenium.io.FileHandler;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
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
        sourceInput.sendKeys("Berlin");
        Thread.sleep(1000);
        destinationInput.sendKeys(Keys.ENTER);
        destinationInput.sendKeys("Hamburg");
        Thread.sleep(1000);
        destinationInput.sendKeys(Keys.ENTER);

        // Select current date
        LocalDate currentDate = LocalDate.now();
        LocalDate futureDate = currentDate.plusDays(2);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String futureDateStr = formatter.format(futureDate);
        //String currentDateStr = formatter.format(currentDate);
        System.out.println("Taken data with 2 days in future : " + futureDateStr);

        /*// Splitting future string date
        String splitterCurrentDate[] = currentDateStr.split("-");
        String current_month_year = splitterCurrentDate[1];
        String currentDay = splitterCurrentDate[0];


        // Splitting future string date
        String splitterFuture[] = futureDateStr.split("-");
        String month_year = splitterFuture[1];
        String day = splitterFuture[0];*/

        WebElement dateInput = driver.findElement(By.id("outbound-date-input"));
        dateInput.click();
        WebElement cookiesAgree = driver.findElement(By.xpath("//*[@id='cc_dialog']/div/div[2]/button[1]"));
        cookiesAgree.click();
        Thread.sleep(4000);

        // Find all the date options
        List<WebElement> dayElements = driver.findElements(By.xpath("//*[@id='outbound-calendar']/div[3]/button"));

        //WebElement selectedDate = null;

        for (WebElement ele : dayElements) {
            String eleText = ele.getAttribute("aria-label");
            System.out.println(eleText);
            System.out.println(futureDateStr);
            if (eleText.equals(futureDateStr)) {
                ele.click();
                System.out.println(ele);
            }
        }

        /*while (true) {
            if (Objects.equals(month_year, current_month_year)) {
                WebElement futureDateElement = driver.findElement(By.xpath("//button[contains(@data-date, '\" + futureDateStr + \"')]"));
                futureDateElement.click();
            } else {
                WebElement nextMonth = driver.findElement(By.xpath("//*[@id='outbound-calendar']/div[1]/button[2]"));
                nextMonth.click();
                WebElement futureDateElement = driver.findElement(By.xpath("//td[@data-date='" + futureDateStr + "']"));
                futureDateElement.click();

            }*/

            Thread.sleep(1000);
            WebElement searchButton = driver.findElement(By.xpath("//*[@id='search-submit-button']"));
            searchButton.click();
            Thread.sleep(1000);

            // Perform assertions on the search results page
            String pageTitle = driver.getTitle();
            Assert.assertTrue(pageTitle.contains("Find the Cheapest Tickets on Busbud"), "Search results page title does not match");

            // Verify that the search results contain the connection information with the selected date
            /*WebElement connectionInfo = driver.findElement(By.xpath("//*[@id='departures-list-page']/div/div[@data-cy-type]"));
            String connectionText = connectionInfo.getText();
            System.out.println(connectionText);*/
            //Assert.assertTrue(connectionText.contains(futureDateStr), "Connection data for the selected date not found");

            // Find all the connection elements on the page
            WebElement currency = driver.findElement(By.xpath("//*[@id='currency-picker']"));
            currency.click();
            Thread.sleep(3000);
            WebElement currencyPicker = driver.findElement(By.xpath("//*[@id='currency-picker']/div/ul/li[3]/a"));
            currencyPicker.click();
            Thread.sleep(3000);

            List<WebElement> connectionElements = driver.findElements(By.xpath("//*[@id='departures-list-page']/div/div[@data-cy-type='departure-card']"));
            Thread.sleep(3000);
            System.out.println(connectionElements.size());
            Thread.sleep(3000);

            // Iterate over the connection elements to collect the desired information
            for (WebElement connectionElement : connectionElements) {
                // Extract the date from each connection element
                WebElement dateElement = connectionElement.findElement(By.xpath(".//*[@data-cy='departure-card-content']/div[3]/div/div/span"));
                String date = dateElement.getText(); //*[@id="departures-list-page"]/div/div[1]/div/div/div/div[3]/div[1]/div/span[1]
                                                     //*[@id="departures-list-page"]/div/div[2]/div/div/div/div[3]/div[1]/div/span[1]
                                                    //*[@id="departures-list-page"]/div/div[5]/div/div/div/div[6]/div/span/span[2]
                                                    //*[@id="departures-list-page"]/div/div[6]/div/div/div/div[6]/div/span/span[2]
                                                    //*[@id="departures-list-page"]/div/div[12]/div/div/div/div[6]/div/span/span[2]
                                                    //*[@id="departures-list-page"]/div
                //*[@id='departures-list-page']/div/div[@data-cy-type='departure-card']/div/div/div[@data-cy='departure-card-content']/div[3]/div/div/span  18 sztuk

                // Extract the price from each connection element
                WebElement priceElement = connectionElement.findElement(By.xpath("//*[@id='departures-list-page']/div/div/div/div/div/div[6]/div/span/span[2]"));
                String price = priceElement.getText();

                // Find the elements containing the price and date values
                //*[@id="departures-list-page"]/div/div[1]/div/div/div[1]/div/div/span
                //*[@id="departures-list-page"]/div/div[2]/div/div/div[1]/div/div/span
                //*[@id="departures-list-page"]/div/div[3]/div/div/div[1]/div/div/span
                //*[@id="departures-list-page"]/div/div[5]/div/div/div[1]/div/div/span
                List<WebElement> priceElements = driver.findElements(By.cssSelector(".price"));
                List<WebElement> dateElements = driver.findElements(By.cssSelector(".date"));

                // Create lists to store the extracted values
                List<String> prices = new ArrayList<>();
                List<String> dates = new ArrayList<>();

                // Extract the price values
                for (WebElement priceElement : priceElements) {
                    String price = priceElement.getText();
                    prices.add(price);
                }

                // Extract the date values
                for (WebElement dateElement : dateElements) {
                    String date = dateElement.getText();
                    dates.add(date);
                }


                // Print the collected data
                System.out.println("Date: " + date);
                System.out.println("Price: " + price);
                System.out.println("------------------");
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

        if (Objects.equals(month_year, current_month_year)) {
            WebElement futureDateElement = driver.findElement(By.xpath("//td[@data-date='" + futureDateStr + "']"));
            futureDateElement.click();
        } else {
            WebElement nextMonth = driver.findElement(By.xpath("//*[@id='outbound-calendar']/div[1]/button[2]"));
            nextMonth.click();
            WebElement futureDateElement = driver.findElement(By.xpath("//td[@data-date='" + futureDateStr + "']"));
            futureDateElement.click();
        }

        WebElement searchButton = driver.findElement(By.cssSelector(".search-form .search-button"));
        searchButton.click();

        // Perform assertions on the search results page
        String pageTitle = driver.getTitle();
        Assert.assertTrue(pageTitle.contains("Search Results"), "Search results page title does not match");

        // Verify that the search results contain the connection information with the selected date
        WebElement connectionInfo = driver.findElement(By.cssSelector(".connection-info"));
        String connectionText = connectionInfo.getText();
        Assert.assertTrue(connectionText.contains(futureDateStr), "Connection data for the selected date not found");

        // Find all the connection elements on the page
        List<WebElement> connectionElements = driver.findElements(By.cssSelector(".connection-info"));

        // Iterate over the connection elements to collect the desired information
        for (WebElement connectionElement : connectionElements) {
            // Extract the date from each connection element
            WebElement dateElement = connectionElement.findElement(By.cssSelector(".date"));
            String date = dateElement.getText();

            // Extract the price from each connection element
            WebElement priceElement = connectionElement.findElement(By.cssSelector(".price"));
            String price = priceElement.getText();

            // Print the collected data
            System.out.println("Date: " + date);
            System.out.println("Price: " + price);
            System.out.println("------------------");
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

        if (Objects.equals(month_year, current_month_year)) {
            WebElement futureDateElement = driver.findElement(By.xpath("//td[@data-date='" + futureDateStr + "']"));
            futureDateElement.click();
        } else {
            WebElement nextMonth = driver.findElement(By.xpath("//*[@id='outbound-calendar']/div[1]/button[2]"));
            nextMonth.click();
            WebElement futureDateElement = driver.findElement(By.xpath("//td[@data-date='" + futureDateStr + "']"));
            futureDateElement.click();
        }

        WebElement searchButton = driver.findElement(By.cssSelector(".search-form .search-button"));
        searchButton.click();

        // Perform assertions on the search results page
        String pageTitle = driver.getTitle();
        Assert.assertTrue(pageTitle.contains("Search Results"), "Search results page title does not match");

        // Verify that the search results contain the connection information with the selected date
        WebElement connectionInfo = driver.findElement(By.cssSelector(".connection-info"));
        String connectionText = connectionInfo.getText();
        Assert.assertTrue(connectionText.contains(futureDateStr), "Connection data for the selected date not found");

        // Find all the connection elements on the page
        List<WebElement> connectionElements = driver.findElements(By.cssSelector(".connection-info"));

        // Iterate over the connection elements to collect the desired information
        for (WebElement connectionElement : connectionElements) {
            // Extract the date from each connection element
            WebElement dateElement = connectionElement.findElement(By.cssSelector(".date"));
            String date = dateElement.getText();

            // Extract the price from each connection element
            WebElement priceElement = connectionElement.findElement(By.cssSelector(".price"));
            String price = priceElement.getText();

            // Print the collected data
            System.out.println("Date: " + date);
            System.out.println("Price: " + price);
            System.out.println("------------------");
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

        if (Objects.equals(month_year, current_month_year)) {
            WebElement futureDateElement = driver.findElement(By.xpath("//td[@data-date='" + futureDateStr + "']"));
            futureDateElement.click();
        } else {
            WebElement nextMonth = driver.findElement(By.xpath("//*[@id='outbound-calendar']/div[1]/button[2]"));
            nextMonth.click();
            WebElement futureDateElement = driver.findElement(By.xpath("//td[@data-date='" + futureDateStr + "']"));
            futureDateElement.click();
        }

        WebElement searchButton = driver.findElement(By.cssSelector(".search-form .search-button"));
        searchButton.click();

        // Perform assertions on the search results page
        String pageTitle = driver.getTitle();
        Assert.assertTrue(pageTitle.contains("Search Results"), "Search results page title does not match");

        // Verify that the search results contain the connection information with the selected date
        WebElement connectionInfo = driver.findElement(By.cssSelector(".connection-info"));
        String connectionText = connectionInfo.getText();
        Assert.assertTrue(connectionText.contains(futureDateStr), "Connection data for the selected date not found");

        // Find all the connection elements on the page
        List<WebElement> connectionElements = driver.findElements(By.cssSelector(".connection-info"));

        // Iterate over the connection elements to collect the desired information
        for (WebElement connectionElement : connectionElements) {
            // Extract the date from each connection element
            WebElement dateElement = connectionElement.findElement(By.cssSelector(".date"));
            String date = dateElement.getText();

            // Extract the price from each connection element
            WebElement priceElement = connectionElement.findElement(By.cssSelector(".price"));
            String price = priceElement.getText();

            // Print the collected data
            System.out.println("Date: " + date);
            System.out.println("Price: " + price);
            System.out.println("------------------");
        }

    }
}
