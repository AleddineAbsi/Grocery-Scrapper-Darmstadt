package scrapper.logic;

import scrapper.model.AVLNode;
import scrapper.model.Product;

import java.util.ArrayList;
import java.util.List;

// AVL Tree class
public class AVLTree {
    private AVLNode root;

    public void insert(Product p) {
        root = insert(root, p);
    }

    private AVLNode insert(AVLNode node, Product p) {
        if (node == null) return new AVLNode(p);

        if (p.getPrice() < node.getKey()) {
            System.out.println("left");
            node.setLeft(insert(node.getLeft(), p));
        } else if (p.getPrice() > node.getKey()) {
            System.out.println("right");
            node.setRight(insert(node.getRight(), p));
        } else {
            node.getProducts().add(p);
        }

        updateHeight(node);
        return balance(node);
    }

    private void updateHeight(AVLNode node) {
        node.setHeight(1 + Math.max(height(node.getLeft()), height(node.getRight())));
    }

    private int height(AVLNode node) {
        return node == null ? 0 : node.getHeight();
    }

    private int getBalance(AVLNode node) {
        return node == null ? 0 : height(node.getLeft()) - height(node.getRight());
    }

    private AVLNode balance(AVLNode node) {
        int balance = getBalance(node);

        if (balance > 1) {
            if (getBalance(node.getLeft()) < 0) {
                node.setLeft(rotateLeft(node.getLeft()));
            }
            return rotateRight(node);
        }

        if (balance < -1) {
            if (getBalance(node.getRight()) > 0) {
                node.setRight(rotateRight(node.getRight()));
            }
            return rotateLeft(node);
        }

        System.out.println("balanced");
        return node;
    }

    private AVLNode rotateRight(AVLNode y) {
        AVLNode x = y.getLeft();
        AVLNode T2 = x.getRight();

        x.setRight(y);
        y.setLeft(T2);

        System.out.println("updating y height");
        updateHeight(y);
        System.out.println("updating x height");
        updateHeight(x);

        return x;
    }

    private AVLNode rotateLeft(AVLNode x) {
        AVLNode y = x.getRight();
        AVLNode T2 = y.getLeft();

        y.setLeft(x);
        x.setRight(T2);

        updateHeight(x);
        updateHeight(y);

        return y;
    }

    private void rangeSearch(AVLNode node, double min, double max, List<Product> result) {
        if (node == null) return;

        if (min < node.getKey()) {
            System.out.println("Range search left");
            rangeSearch(node.getLeft(), min, max, result);
        }

        if (min <= node.getKey() && node.getKey() <= max) {
            System.out.println("add all");
            result.addAll(node.getProducts());
        }

        if (node.getKey() < max) {
            System.out.println("Range search right");
            rangeSearch(node.getRight(), min, max, result);
        }
    }

    public List<Product> rangeSearch(double min, double max) {
        List<Product> result = new ArrayList<>();
        rangeSearch(root, min, max, result);
        return result;
    }
}

