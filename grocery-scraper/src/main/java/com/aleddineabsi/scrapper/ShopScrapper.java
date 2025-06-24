package com.aleddineabsi.scrapper;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import java.util.Locale;

/**
 * Scrapping of products and its details from each website using JavaScript
 * This Scrapper scrolls the page, waits till all the elements are Properly loaded and extract all Data.
 * Slower runtime for complete results.
 *
 * @author Aleddine
 * @version  1.0
 */
public class ShopScrapper {
    void scrap(){

        String browserRoot ="C:\\Users\\Aleddine\\Desktop\\chrome-win\\chrome.exe";
        String websiteUrl ="https://www.penny.de/angebote/15A-05";
        String productClass ="";
        String productNameClass ="";
        String productPriceClass ="";
        String productDiscountPriceClass ="";

        String boxClass="";
        String dateClass ="";
        String categoryClass ="";

        int scrollDelayTime = 250;

        //for testing purpose
        boolean runHeadless = false;


        // driver Setup
        WebDriverManager.chromedriver().setup();

        // Chromium config
        ChromeOptions options = new ChromeOptions();

        if(runHeadless) {
            options.addArguments("--headless=new");
            options.addArguments("--disable-gpu");
            options.addArguments("--window-size=1920,1080");
        }

        // root chromium
        options.setBinary(browserRoot);

        // load Chromium
        WebDriver driver = new ChromeDriver(options);

        try {
            // Load Website
            driver.get(websiteUrl);

            // Wait till the Website shows up
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div.ellipsis.bubble__price")));


            JavascriptExecutor js = (JavascriptExecutor) driver;

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



            // Collecting of Data
            List<WebElement> produktElements = driver.findElements(By.cssSelector("li.tile-list__item, div.l-container"));
            for (WebElement el : produktElements) {
                ///date
                try{
                    String date = el.findElements(By.cssSelector("div.category-bar__badge.badge.t-bg--white.t-color--grey-midnight")).getLast().getText();
                    String category = el.findElement(By.cssSelector("h2.category-bar__hdln.t-color--white.h5")).getText();
                    System.out.println("----------------");
                    System.out.println("Category :"+ category+ "/Date : " + date);
                }
                catch(java.util.NoSuchElementException e){
                }
                ///Products
                try {
                    String nom = el.findElement(By.cssSelector("h3.h4.offer-tile__headline")).getText();
                    String price = el.findElement(By.cssSelector("div.ellipsis.bubble__price")).getText();

                    //special by Penny for the app price
                    try{
                        String price2 = el.findElement(By.cssSelector("span.bubble__price.ellipsis")).getText();
                        System.out.println(nom + " : " + price2 + " : " + price);
                    }
                    catch (NoSuchElementException e){
                        System.out.println(nom + " : " + price);
                    }
                }
                //special by Penny some prices are images
                catch(NoSuchElementException e){
                    continue;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //close navigator
            driver.quit();
        }
    }

}
