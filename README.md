# Grocery-Scrapper-Darmstadt
A Java application that compares grocery prices across different supermarkets in Darmstadt.  
It scrapes product data, stores it in a SQLite database, and provides a simple JavaFX UI to launch the process.  
The goal is to help students and individuals find the best available deals.

## Features
- Scrape grocery prices from multiple supermarket websites (only Rewe, Edeka and Penny in Darmstadt atm)
- Store results in a **SQLite** database (`data/groceriesDatabase.db`)  
- Launch scraping from a simple **JavaFX** UI  
- File chooser integration (optional)  
- Product comparison and price sorting  

##  Tech Stack

- **Java 21+**  
- **JavaFX** (UI)  
- **Selenium + WebDriverManager** (scraping)  
- **SQLite JDBC** (database)  
- **Gradle** (build system)  

## Usage
1. Start the app (MainGUI)
2. Click **Choose File** and select the executable of a Web Browser on your system ( Only Works with Chrominium atm ) 
3. Click Start Scrapping to launch scraping
4. Results are stored in data/groceriesDatabase.db


## Project Structure

- **GUI (`gui/`)** → JavaFX interface with buttons to select Chromium/Chrome executable and start scraping.  
- **Logic (`logic/`)** → Scraping orchestration and database management.  
- **Model (`model/`)** → Data models like `Product`.  


