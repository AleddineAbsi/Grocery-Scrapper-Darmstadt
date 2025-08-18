package scrapper.logic;


import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ShopScrapperJUnitTest {
    @Test
    void testAdd(){
        ShopScrapper scrapper = new ShopScrapper();
        int result = scrapper.addInt(1,2);
        assertEquals(3,result,"it must be equal to 5");
        System.out.println("true");
    }



}
