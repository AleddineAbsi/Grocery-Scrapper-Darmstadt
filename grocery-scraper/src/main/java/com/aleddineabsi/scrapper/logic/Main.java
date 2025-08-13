package com.aleddineabsi.scrapper.logic;


import com.aleddineabsi.scrapper.model.Product;
import com.aleddineabsi.scrapper.model.ProductData;
import org.sqlite.core.DB;

import java.io.IOException;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hellääo world!");

        ShopScrapper myScrapper = new ShopScrapper();

        try {
            DatabaseManager.resetProductsTable(DriverManager.getConnection(DatabaseManager.DB_URL));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        DatabaseManager.manage();

        Thread t1 = new Thread  (() ->{
            System.out.println("starterd first thread");
            myScrapper.scrap(
                    "Penny",
                    "https://www.penny.de/angebote/15A-05",
                    "article.offer-tile, article.offer-tile.offer-tile--highlight, div.category-bar__texts",
                    "h3.h4.offer-tile__headline, h4.offer-tile__headline",
                    "span.bubble__price-value",
                    "span.bubble__price.ellipsis",
                    "div.category-bar__badge.badge.t-bg--white.t-color--grey-midnight",
                    "h2.category-bar__hdln.t-color--white.h5",
                    250
            );
            System.out.println("finished first thread");
        });
        Thread t2 = new Thread  (() ->{
            System.out.println("starterd second thread");
            myScrapper.scrap
                    (
                            "Rewe",
                            "https://www.rewe.de/angebote/darmstadt/240070/rewe-markt-heinrichstr-52/?icid=marktseiten_rewe-de%3Amarktseite-240070_int_angebote_rewe-de%3Aangebote_nn_nn_nn_nn",
                            "div.sos-offer, div.sos-category__content-title",
                            "a.cor-offer-information__title-link",
                            "div.cor-offer-price__tag-price",
                            "div.cor-loyalty-badge",
                            "span.sos-week-tabs__tab-subtitle",
                            "h2",
                            250
                    );
            System.out.println("finished second thread");
        });
        Thread t3 = new Thread  (() ->{
            System.out.println("starterd third thread");
            myScrapper.scrap("Edeka",
                    "https://www.edeka.de/eh/s%C3%BCdwest/e-center-patschull-eschollbr%C3%BCcker-stra%C3%9Fe-44/angebote.jsp",
                    "div.css-1uiiw0z, div.css-4z4xul",
                    "span.css-i72elb",
                    "span.css-111vupd",
                    "nichtgegeben",
                    "span.css-ikgidz",
                    "h2.css-1nojpee",
                    250
            );
            System.out.println("finished third thread");
        });
        t1.start();
        t2.start();
        t3.start();



        //test code -
        //waiting the completion of the threads
        try {
            t1.join();
            t2.join();
            t3.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        System.out.println("Started deleting -------------------------------------------------------------------------");



        ProductData myData = new ProductData();
        //get the product from the database
        myData.updateProductListing();
        myData.supressDoubles();
        List<Product> myProduct = myData.getProductListing();

        System.out.println("deleted doubles -------------------------------------------------------------------------");

        //reset the database
        try {
            DatabaseManager.resetProductsTable(DriverManager.getConnection(DatabaseManager.DB_URL));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        System.out.println("wrting ...-------------------------------------------------------------------------");

        //rewrite the database without duplicates
        for(Product p : myProduct){
            try {
                DatabaseManager.insertProduct(DriverManager.getConnection(DatabaseManager.DB_URL),p.getName(),p.getStore(),p.getCategory(),p.getPrice());
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        System.out.println("finished -------------------------------------------------------------------------");


        /*

        List<Product> myProduct = myData.byPriceFaster(myData.getProductListing(),2.5,5);
        for(Product p : myProduct){
            System.out.println(p.getName() + " " + p.getPrice());
        }

        try {
            DatabaseManager.exporterProduitsCSV(myProduct, "data/produits.csv");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
         */
    }
}