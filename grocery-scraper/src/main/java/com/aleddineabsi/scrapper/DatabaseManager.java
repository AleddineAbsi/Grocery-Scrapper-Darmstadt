package com.aleddineabsi.scrapper;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Test class for managing the SQLite Database
 */
public class DatabaseManager {
    static final String DB_URL = "jdbc:sqlite:data/groceriesDatabase.db";


    public static void manage(){
        try (Connection conn = DriverManager.getConnection(DB_URL)) {
            if (conn != null) {
                System.out.println("Found the Database File");

                // Read data
                if (!hasData(conn)) {
                    System.out.println("empty Table");
                }

                //Insert Product
                //insertProduct(conn, "Lait entier", "Aldi", 0.89);
                //listProducts(conn);

                resetProductsTable(conn);
                // Delete Product
                //deleteProductByName(conn, "Lait entier");

            }

        } catch (SQLException e) {
            System.out.println("error" + e.getMessage());
        }
    }

    static boolean hasData(Connection conn) throws SQLException {
        String sql = "SELECT COUNT(*) AS total FROM products";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            int count = rs.getInt("total");
            return count > 0;
        }
    }

    static void insertProduct(Connection conn, String name, String store, String category,double price) throws SQLException {
        String sql = "INSERT INTO products(name, store, price, category,updated_at) VALUES (?, ?, ?, ?,datetime('now'))";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, name);
            pstmt.setString(2, store);
            pstmt.setDouble(3, price);
            pstmt.setString(4, category);
            pstmt.executeUpdate();
        }
    }
    /**
     * for testing purposes
     */
    static void listProducts(Connection conn) throws SQLException {
        String sql = "SELECT * FROM products";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            System.out.println("List of Product :");
            while (rs.next()) {
                System.out.printf("- %d | %s | %.2f€ | %s | %s\n",
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getDouble("price"),
                        rs.getString("store"),
                        rs.getString("updated_at"));
            }
        }
    }


    /**
     * extract Data from the SQLite data base and parse it in a List<Product> element
     */
    static List<Product> getProductListing(Connection conn) throws SQLException {
        List<Product> productListing = new ArrayList<>();
        String sql = "SELECT * FROM products";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Product localProduct = new Product(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getDouble("price"),
                        0,
                        rs.getString("store"),
                        rs.getString("updated_at"),
                        rs.getString("store")
                        );
                productListing.add(localProduct);
            }
            return productListing;
        }
    }

    static void deleteProductByName(Connection conn, String name) throws SQLException {
        String sql = "DELETE FROM products WHERE name = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, name);
            int rows = pstmt.executeUpdate();
            System.out.println("Deleted Product: " + rows + " levels");
        }
    }

    public static void resetProductsTable(Connection conn) {
        try (Statement stmt = conn.createStatement()) {
            // Supprimer toutes les lignes
            stmt.executeUpdate("DELETE FROM products");

            // Réinitialiser l'autoincrement de l'id
            stmt.executeUpdate("DELETE FROM sqlite_sequence WHERE name='products'");

            System.out.println("Database emptied and id resetted");
        } catch (SQLException e) {
            System.out.println("Error during deletion of Database" + e.getMessage());
        }
    }

}
