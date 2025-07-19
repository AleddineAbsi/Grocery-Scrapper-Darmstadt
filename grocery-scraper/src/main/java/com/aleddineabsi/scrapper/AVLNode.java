package com.aleddineabsi.scrapper;

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
}

// AVL Tree class
class AVLTree {
    private AVLNode root;

    //when Tree empty
    public void insert(Product p) {
        root = insert(root, p);
    }

    //when tree already filled -> inser and balance+
    private AVLNode insert(AVLNode node, Product p) {
        if (node == null) return new AVLNode(p);
        if (p.getPrice() < node.key) {
            System.out.println("left");
            node.left = insert(node.left, p);
        }
        else if (p.getPrice() > node.key) {
            System.out.println("right");
            node.right = insert(node.right, p);
        }
        else {
            node.produits.add(p);
        }
        updateHeight(node);
        return balance(node);
    }

    private void updateHeight(AVLNode node) {
        node.height = 1 + Math.max(height(node.left), height(node.right));
    }

    private int height(AVLNode node) {
        return node == null ? 0 : node.height;
    }

    private int getBalance(AVLNode node) {
        return node == null ? 0 : height(node.left) - height(node.right);
    }


    //balance the tree after insertion
    private AVLNode balance(AVLNode node) {
        int balance = getBalance(node);
        if (balance > 1) {
            if (getBalance(node.left) < 0)
                node.left = rotateLeft(node.left);
            return rotateRight(node);
        }
        if (balance < -1) {
            if (getBalance(node.right) > 0)
                node.right = rotateRight(node.right);
            return rotateLeft(node);
        }
        System.out.println("balanced");
        return node;
    }

    private AVLNode rotateRight(AVLNode y) {
        AVLNode x = y.left;
        AVLNode T2 = x.right;
        x.right = y;
        y.left = T2;
        System.out.println("updating y height");
        updateHeight(y);
        System.out.println("updating x height");
        updateHeight(x);
        return x;
    }

    private AVLNode rotateLeft(AVLNode x) {
        AVLNode y = x.right;
        AVLNode T2 = y.left;
        y.left = x;
        x.right = T2;
        updateHeight(x);
        updateHeight(y);
        return y;
    }


    private void rangeSearch(AVLNode node, double min, double max, List<Product> result) {
        if (node == null) return;
        if (min < node.key) {
            System.out.println("Range search left");
            rangeSearch(node.left, min, max, result);
        }
        if (min <= node.key && node.key <= max) {
            System.out.println("add all");
            result.addAll(node.produits);
        }
        if (node.key < max) {
            System.out.println("Range search right");
            rangeSearch(node.right, min, max, result);
        }
    }

    public List<Product> rangeSearch(double min, double max) {
        List<Product> result = new ArrayList<>();
        rangeSearch(root, min, max, result);
        return result;
    }


}
