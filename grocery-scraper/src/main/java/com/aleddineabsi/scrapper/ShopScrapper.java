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
 */

public class ShopScrapper {
    void scrap(
            int scrollDelayTime,
            String browserRoot,
            String websiteUrl,
            String productClass,
            String productNameClass,
            String productPriceClass,
            String productDiscountPriceClass,
            String boxClass,
            String dateClass,
            String categoryClass){

         browserRoot ="C:\\Users\\aledd\\Desktop\\Chromium\\chrome.exe";
         websiteUrl ="https://www.penny.de/angebote/15A-05";
         productClass ="div.l-container";
         productNameClass ="h3.h4.offer-tile__headline";
         productPriceClass ="div.ellipsis.bubble__price";
         productDiscountPriceClass ="span.bubble__price.ellipsis";

         boxClass="li.tile-list__item";
         dateClass ="div.category-bar__badge.badge.t-bg--white.t-color--grey-midnight";
         categoryClass ="h2.category-bar__hdln.t-color--white.h5";

         scrollDelayTime = 250;

        // load Chromium
        WebDriver driver = setupDriver(false,browserRoot);
        fillDriver(driver,websiteUrl,scrollDelayTime);
        fillDataBase(driver,boxClass,dateClass,productClass,productNameClass,categoryClass,productPriceClass,productDiscountPriceClass);
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
     * @param boxClass,dateClass,ProductClass,productNameClass,categoryClass,productPriceClass,productDiscountPriceClass
     * the String HTML Tag name of each element searched
     */
    private void fillDataBase(
            WebDriver driver,
            String boxClass,
            String dateClass,
            String productClass,
            String productNameClass,
            String categoryClass,
            String productPriceClass,
            String productDiscountPriceClass){
        String date = "null";
        String category ="null";
        String name = "null";
        double price =  0;
        String price2 = "null";

        List<WebElement> produktElements = driver.findElements(By.cssSelector(boxClass +", " + productClass));
        for (WebElement el : produktElements) {
            ///date
            try {
                date = el.findElements(By.cssSelector(dateClass)).getLast().getText();
                category = el.findElement(By.cssSelector(categoryClass)).getText();
                System.out.println("----------------");
                System.out.println("Category :" + category + "/Date : " + date);
            } catch (java.util.NoSuchElementException e) {
            }
            ///Products
            try {
                name = el.findElement(By.cssSelector(productNameClass)).getAttribute("innerText");
                //remove euro sign
                String wholePrice = el.findElement(By.cssSelector(productPriceClass)).getText();
                try {
                    price = Double.parseDouble(wholePrice.substring(0, wholePrice.length() - 1));
                } catch (NumberFormatException e) {
                    continue;
                }


                //special by Penny for the app price
                try {
                    price2 = el.findElement(By.cssSelector(productDiscountPriceClass)).getText();
                    System.out.println(name + " : " + price2 + " : " + price);
                } catch (NoSuchElementException e) {
                    System.out.println(name + " : " + price);
                }
            }
            //special by Penny some prices are images
            catch (NoSuchElementException e) {
                continue;
            }
            try {
                DatabaseManager.insertProduct(DriverManager.getConnection("jdbc:sqlite:data/groceriesDatabase.db"), name, "Penny", price);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

}
