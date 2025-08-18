package scrapper.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ProductDataJUnitTest {

    private ProductData productData;
    private List<Product> products;

    @BeforeEach
    void setUp() {
        productData = new ProductData();

        products = new ArrayList<>();
        products.add(new Product(1, "milk", 0.89, 0, "dairy", "2023-01-01", "Aldi"));
        products.add(new Product(2, "milk", 0.89, 0, "dairy", "2023-01-02", "Aldi")); // duplicate (same name+store)
        products.add(new Product(3, "bread", 1.20, 0, "bakery", "2023-01-01", "Lidl"));
        products.add(new Product(4, "apple", 0.50, 0, "fruit", "2023-01-01", "Rewe"));

        productData.setProductListing(new ArrayList<>(products));
    }

    @Test
    void testSuppressDoubles() {
        productData.supressDoubles();
        List<Product> result = productData.getProductListing();

        // Expect 3 unique products
        assertEquals(3, result.size());
        assertTrue(result.stream().anyMatch(p -> p.getName().equals("milk")));
        assertTrue(result.stream().anyMatch(p -> p.getName().equals("bread")));
        assertTrue(result.stream().anyMatch(p -> p.getName().equals("apple")));
        System.out.println("Test SupressDoubles passed");
    }

    @Test
    void testByPrice() {
        // filter products between 0.6 and 1.5
        List<Product> filtered = productData.byPrice(products, 0.6, 1.5);

        assertEquals(3, filtered.size());
        assertTrue(filtered.stream().anyMatch(p -> p.getName().equals("milk")));
        assertTrue(filtered.stream().anyMatch(p -> p.getName().equals("bread")));
        System.out.println("Test testByPrice passed");
    }

    @Test
    void testByCategory() {
        List<Product> filtered = productData.byCategory(products, "fruit");

        assertEquals(1, filtered.size());
        assertEquals("apple", filtered.get(0).getName());
        System.out.println("Test testByCategory passed");
    }
}