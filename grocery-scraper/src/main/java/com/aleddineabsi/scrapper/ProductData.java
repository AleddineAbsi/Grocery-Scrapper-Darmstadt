package com.aleddineabsi.scrapper;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class ProductData {

    public ProductData(){

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

    /**
     * general filter function
     */
    public List<Product> filter(List<Product> productsToSort, Predicate<Product> condition){
        return productsToSort.stream().filter(condition).collect(Collectors.toList());
    }

    /**
     * filter prices (not specifically in order)
     * when lowerLimit/upperLimit is equal to -1, we ignore the limit in the filterin
     */
    public List<Product> byPrice(List<Product> productsToSort,double minimum,double maximum) throws IllegalArgumentException{
        if (minimum>maximum){
            throw new IllegalArgumentException();
        }
        return filter(productsToSort,p -> (maximum == -1 || p.getPrice() < maximum) && (minimum == -1 || p.getPrice() > minimum));
    }

    /**
     * filter prices with faster execute time for a large amount of Product
     */
    public List<Product> byPriceFaster(List<Product> productToSort,double minimum,double maximum) throws IllegalArgumentException{
        if(minimum >maximum){
            throw new IllegalArgumentException();
        }
        AVLTree mytree = new AVLTree();
        //fill the AVLTree
        for(int i = 0; i< productToSort.size();i++){
            mytree.insert(productToSort.get(i));
        }
        return mytree.rangeSearch(minimum,maximum);
    }

    public List<Product> byCategory(List<Product> productsToSort,String Category){
        return filter(productsToSort,p -> Objects.equals(p.getCategory(), Category));
    }

    public List<Product> byName(List<Product> productsToSort,String Name){
        return filter(productsToSort,p -> Objects.equals(p.getName(), Name));
    }

    public List<Product> byStore(List<Product> productsToSort,String Store){
        return filter(productsToSort,p -> Objects.equals(p.getStore(), Store));
    }









}
