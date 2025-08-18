package scrapper.logic;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import scrapper.model.Product;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DatabaseManagerJUnitTest {

    private Connection conn;

    @BeforeEach
    void setUp() throws SQLException {
        //Temporary Database
        conn = DriverManager.getConnection("jdbc:sqlite::memory:");

        // Creation of the Table
        try (Statement stmt = conn.createStatement()) {
            stmt.execute("CREATE TABLE products (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "name TEXT," +
                    "store TEXT," +
                    "price REAL," +
                    "category TEXT," +
                    "updated_at TEXT)");
        }
    }

    @Test
    void testInsertAndGetProductListing() throws SQLException {
        //Insertion of Production
        DatabaseManager.insertProduct(conn, "milch", "Aldi", "dairy", 1.69);

        //Reading of Product
        List<Product> products = DatabaseManager.getProductListing(conn);

        assertEquals(1, products.size(),"it must contain only 1 product");

        Product p = products.get(0);
        assertEquals("MILCH", p.getName());
        assertEquals("ALDI", p.getStore());
        assertEquals(1.69, p.getPrice());
        System.out.println("Test Insert and GetProductListing passed");
    }

    @Test
    void testResetProductsTable() throws SQLException {
        // Insert products
        DatabaseManager.insertProduct(conn, "milk", "Aldi", "dairy", 1.69);
        DatabaseManager.insertProduct(conn, "bread", "Lidl", "bakery", 1.20);

        assertEquals(2, DatabaseManager.getProductListing(conn).size());

        // Reset the table
        DatabaseManager.resetProductsTable(conn);

        // Verify the table is empty
        assertEquals(0, DatabaseManager.getProductListing(conn).size());

        System.out.println("Test ResetProductsTable passed");
    }

    @Test
    void testDeleteProductByName() throws SQLException {
        DatabaseManager.insertProduct(conn, "miLch", "Aldi", "dairy", 1.69);
        DatabaseManager.insertProduct(conn, "bread", "Lidl", "bakery", 1.20);

        assertEquals(2, DatabaseManager.getProductListing(conn).size());

        // Delete one product
        DatabaseManager.deleteProductByName(conn, "milch");

        List<Product> products = DatabaseManager.getProductListing(conn);
        assertEquals(1, products.size());
        //testing also the lower/upper case
        assertEquals("BREAD", products.get(0).getName());

        System.out.println("Test DeleteProductByName passed");
    }

    @Test
    void testHasData() throws SQLException {
        assertFalse(DatabaseManager.hasData(conn));

        DatabaseManager.insertProduct(conn, "milch", "Aldi", "dairy", 1.69);

        assertTrue(DatabaseManager.hasData(conn));

        System.out.println("Test HasData passed");
    }

}
