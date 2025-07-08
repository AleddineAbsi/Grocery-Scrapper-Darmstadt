package com.aleddineabsi.scrapper;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import javax.xml.crypto.Data;
import java.sql.DriverManager;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;




public class Main {
    public static void main(String[] args) {
        System.out.println("Hellääüo world!");
        ShopScrapper myScrapper = new ShopScrapper();

        DatabaseManager.manage();
        /// Scrap Penny
        myScrapper.scrap(
                "Penny",
                "C:\\Users\\aledd\\Desktop\\Chromium\\chrome.exe",
                "https://www.penny.de/angebote/15A-05",
                "div.l-container,li.tile-list__item",
                "h3.h4.offer-tile__headline",
                "div.ellipsis.bubble__price",
                "span.bubble__price.ellipsis",
                "div.category-bar__badge.badge.t-bg--white.t-color--grey-midnight",
                "h2.category-bar__hdln.t-color--white.h5",
                250
                );

        myScrapper.scrap
                (
                        "Rewe",
                        "C:\\Users\\aledd\\Desktop\\Chromium\\chrome.exe",
                        "https://www.rewe.de/angebote/darmstadt/240070/rewe-markt-heinrichstr-52/?icid=marktseiten_rewe-de%3Amarktseite-240070_int_angebote_rewe-de%3Aangebote_nn_nn_nn_nn",
                        "div.sos-offer",
                        "a.cor-offer-information__title-link",
                        "div.cor-offer-price__tag-price",
                        "div.cor-loyalty-badge",
                        "span.sos-week-tabs__tab-subtitle",
                        "div.sos-category__content-title",
                        250
                        );







    }

}