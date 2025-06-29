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
        System.out.println("Hellääüo world!");
        ShopScrapper myScrapper = new ShopScrapper();

        /// Scrap Penny
        /*myScrapper.scrap(
                250,
                "C:\\Users\\aledd\\Desktop\\Chromium\\chrome.exe",
                "https://www.penny.de/angebote/15A-05",
                "div.l-container",
                "h3.h4.offer-tile__headline",
                "div.ellipsis.bubble__price",
                "span.bubble__price.ellipsis",
                "li.tile-list__item",
                "div.category-bar__badge.badge.t-bg--white.t-color--grey-midnight",
                "h2.category-bar__hdln.t-color--white.h5"
                );
         */

        DatabaseManager myManager = new DatabaseManager();
        myManager.manage();


    }

}