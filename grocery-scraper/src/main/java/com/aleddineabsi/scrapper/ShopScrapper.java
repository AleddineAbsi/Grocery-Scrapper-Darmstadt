package com.aleddineabsi.scrapper;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.Duration;
import java.util.List;
import java.util.Locale;

/**
 * Scrapping of products and its details from each website using JavaScript
 * This Scrapper scrolls the page, waits till all the elements are Properly loaded and extract all Data.
 * Slower runtime for complete results.
 *
 * @author Aleddine
 * @version  2.0
 *
 */

public class ShopScrapper {
    /**
     * Set up the Driver with given Parameter
     *
     * @param browserRoot root of the Browser app
     */
    void scrap(
            String storeName,
            String browserRoot,
            String websiteUrl,
            String informationsClasses,
            String productNameClass,
            String productPriceClass,
            String productDiscountPriceClass,
            String dateClass,
            String categoryClass,
            int scrollDelayTime
    ){


        // load Chromium
        WebDriver driver = setupDriver(false,browserRoot);
        fillDriver(driver,websiteUrl,scrollDelayTime);
        fillDataBase(driver,dateClass,informationsClasses,productNameClass,categoryClass,productPriceClass,productDiscountPriceClass,storeName);
        driver.close();
    }

    /**
     * Setup the Dreiver with given Parameter
     *
     * @param runHeadless enable/disable headless for testing
     * @param browserRoot root of the Browser app
     */
    private WebDriver setupDriver(boolean runHeadless,String browserRoot){
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        if(runHeadless) {
            options.addArguments("--headless=new");
            options.addArguments("--disable-gpu");
            options.addArguments("--window-size=1920,1080");
        }
        options.setBinary(browserRoot);
        return new ChromeDriver(options);
    }

    /**
     * Scrolls website long and slowly enough to load all html data and store it inside
     * the driverToFill WebDriver
     *
     * @param driverToFill WebDriver to fill
     * @param websiteUrl Url from the Website to extract data from
     * @param scrollDelayTime Scroll delay till the element appears in the screen
     */
    private void fillDriver(WebDriver driverToFill,String websiteUrl,int scrollDelayTime){
        try{
            // Load Website
            driverToFill.get(websiteUrl);

            // Wait till the Website shows up
            WebDriverWait wait = new WebDriverWait(driverToFill, Duration.ofSeconds(30));
            JavascriptExecutor js = (JavascriptExecutor) driverToFill;

            //y position in the page
            Number result = (Number) js.executeScript("return window.scrollY;");
            Long newHeight =  result.longValue();
            Long lastHeight =newHeight +1;

            while(!lastHeight.equals(newHeight)){
                js.executeScript("window.scrollBy(0, 1000);");
                //Obligatory wait to load JavaScript Elements
                Thread.sleep(scrollDelayTime);
                lastHeight = newHeight;
                result = (Number) js.executeScript("return window.scrollY;");
                newHeight = result.longValue();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * extract infromation from the HTML Tags and save them together in a database
     *
     * @param driver Webdriver that contains the Websites information
     * @param dateClass,ProductClass,productNameClass,categoryClass,productPriceClass,productDiscountPriceClass
     * the String HTML Tag name of each element searched
     */
    private void fillDataBase(
            WebDriver driver,
            String dateClass,
            String productClass,
            String productNameClass,
            String categoryClass,
            String productPriceClass,
            String productDiscountPriceClass,
            String storeName) {
        String date = "null";
        String category = "null";
        String name = "null";
        double price = 0;
        String price2 = "null";
        String localCategory = "no Category";

        List<WebElement> produktElements = driver.findElements(By.cssSelector(productClass));
        for (WebElement el : produktElements) {
            ///date
            try {
                date = el.findElements(By.cssSelector(dateClass)).getLast().getText();
            } catch (java.util.NoSuchElementException e) {}

            ///Category
            try {
                category = el.findElement(By.cssSelector(categoryClass)).getAttribute("innerText");
                localCategory = category;
                System.out.println("----------------");
                System.out.println("Category :" + category + "/Date : " + date);
            } catch (NoSuchElementException e) {}

            ///Name
            try {
                name = el.findElement(By.cssSelector(productNameClass)).getAttribute("innerText");
            } catch (NoSuchElementException e) {}

            /// Price
            try {
                String wholePrice = el.findElement(By.cssSelector(productPriceClass)).getAttribute("innerText");
                //fix Format before converting
                wholePrice = wholePrice.replace(",", ".");
                wholePrice = wholePrice.replaceAll("[* €]", "");
                try {
                    if (!wholePrice.isEmpty()) {
                        price = Double.parseDouble(wholePrice);
                    }
                } catch (NumberFormatException e) {
                    continue;
                }
            } catch (NoSuchElementException e) {}

            //special by Penny for the app price
            try {
                price2 = el.findElement(By.cssSelector(productDiscountPriceClass)).getText();
                System.out.println(name + " : " + price2 + " : " + price + " : " + localCategory);
            } catch (NoSuchElementException e) {
                System.out.println(name + " : " + price + " : " + localCategory);
            }
            try {
                if(name != "null")
                    DatabaseManager.insertProduct(DriverManager.getConnection("jdbc:sqlite:data/groceriesDatabase.db"), name, storeName,localCategory, price);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
