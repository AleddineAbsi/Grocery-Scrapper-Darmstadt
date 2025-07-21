package com.aleddineabsi.scrapper.model;

import java.util.ArrayList;
import java.util.List;

// AVL Node Class
public class AVLNode {
    //we use the price as a key
    double key;
    List<Product> produits = new ArrayList<>();
    int height;
    AVLNode left, right;

    //constructor
    public AVLNode(Product p) {
        this.key = p.getPrice();
        this.produits.add(p);
        this.height = 1;
    }

    public double getKey(){
        return this.key;
    }

    public int getHeight(){
        return this.height;
    }

    public List<Product> getProducts(){
        return this.produits;
    }

    public AVLNode getLeft(){
        return left;
    }

    public AVLNode getRight(){
        return right;
    }

    /// //////////////

    public void setKey(double var){
        this.key = var;
    }

    public void setHeight(int var){
        this.height = var;
    }

    public void setProducts(List<Product> var){
        this.produits = var;
    }

    public void setLeft(AVLNode var){
        this.left = var;
    }

    public void setRight(AVLNode var){
        this.right = var;
    }

}

