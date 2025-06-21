package com.aleddineabsi.scrapper;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");
        try{
            String url="https://books.toscrape.com/";

            Document doc = Jsoup.connect(url).get();

            Elements Items = doc.select(".product_pod");
            System.out.println("Livres trouvees :");

            for(Element item : Items){
                String bookTitle = item.selectFirst("h3 a").attr("title");
                String bookPrice = item.selectFirst(".price_color").text();
                System.out.println("- "+ bookTitle + " = " + bookPrice);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}