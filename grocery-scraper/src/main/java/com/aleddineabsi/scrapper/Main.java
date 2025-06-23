package com.aleddineabsi.scrapper;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");

        // driver Setup
        WebDriverManager.chromedriver().setup();

        // Chromium config
        ChromeOptions options = new ChromeOptions();


        //headless option
        /**
        options.addArguments("--headless=new");
        options.addArguments("--disable-gpu");
        options.addArguments("--window-size=1920,1080");
        **/

        // root chromium
        options.setBinary("C:\\Users\\aledd\\AppData\\Local\\Chromium\\Application\\chrome.exe");

        // load Chrominium
        WebDriver driver = new ChromeDriver(options);

        try {
            // Load Penny
            driver.get("https://www.penny.de/angebote/15A-05");

            // Wait till the elements von javascript load
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div.ellipsis.bubble__price")));


            JavascriptExecutor js = (JavascriptExecutor) driver;

            //y position in the page
            Number result = (Number) js.executeScript("return window.scrollY;");
            Long newHeight =  result.longValue();
            Long lastHeight =newHeight +1;

            while(!lastHeight.equals(newHeight)){
                js.executeScript("window.scrollBy(0, 1000);");
                Thread.sleep(20);
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
                    System.out.println("Date : " + date);
                }
                catch(java.util.NoSuchElementException e){


                }


                ///Products
                try {
                    String nom = el.findElement(By.cssSelector("h3.h4.offer-tile__headline")).getText();
                    String prix = el.findElement(By.cssSelector("div.ellipsis.bubble__price")).getText();

                    //special by Penny for the app price
                    try{
                        String prix2 = el.findElement(By.cssSelector("span.bubble__price.ellipsis")).getText();
                        System.out.println(nom + " : " + prix2 + " : " + prix);

                    }
                    catch (NoSuchElementException e){
                        System.out.println(nom + " : " + prix);
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