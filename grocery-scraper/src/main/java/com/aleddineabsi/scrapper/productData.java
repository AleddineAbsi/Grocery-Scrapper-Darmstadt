package com.aleddineabsi.scrapper;

import javax.print.DocFlavor;
import javax.xml.crypto.Data;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.*;

public class productData {

    public productData(){

    }

    private List<Product> productListing;


    public List<Product> getProductListing(){
        return productListing;
    }

    public void setProductListing(List<Product> param){
        productListing = param;
    }

    public void updateProductListing(){
        try {
            productListing = DatabaseManager.getProductListing(DriverManager.getConnection(DatabaseManager.DB_URL));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void showProductListing(){
        for(int i = 0;i<productListing.size();i++){
            System.out.println(productListing.get(i).getId() + " : " + productListing.get(i).getName());
        }
    }

    /**
     * remove duplicates from the data list using a Set
     */
    public void supressDoubles(){
        Set<String> doubleProduct = new HashSet<>();
        List<Product> newProductListing = new ArrayList<>();
        int counter = 0;
        String hash = "";
        for(int i =0; i < productListing.size();i++){
            hash = productListing.get(i).getName() + "::" + productListing.get(i).getStore();
            if (!doubleProduct.contains(hash)){
                System.out.println("added");
                newProductListing.add(productListing.get(i));
                doubleProduct.add(hash);
                counter++;
            }
            else{
                System.out.println("removed");
            }
        }
        System.out.println(counter +"/"+productListing.size() + "product are unique");
        productListing = newProductListing;
    }

    public void sortToElement(ProductProperty property){

    }



}
