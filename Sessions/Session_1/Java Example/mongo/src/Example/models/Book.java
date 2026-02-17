package Example.models;

import java.util.List;
import java.util.ArrayList;

public class Book {
    private String id;              // MongoDB _id
    private String bookName;
    private String Author;
    private String publishingDate;
    private String category;
    private int numberOfPages;
    private List<Rating> Rates;

    // Inner class for ratings (matches your Rates array)
    public static class Rating {
        private String userName;
        private int Rate;  // min 1, max 5

        public Rating() {}

        public Rating(String userName, int rate) {
            this.userName = userName;
            this.Rate = rate;
        }

        public String getUserName() { return userName; }
        public void setUserName(String userName) { this.userName = userName; }

        public int getRate() { return Rate; }
        public void setRate(int rate) { this.Rate = rate; }

        @Override
        public String toString() {
            return userName + ": " + Rate + "⭐";
        }
    }

    // Constructors
    public Book() {}

    public Book(String bookName, String author, String publishingDate,
                String category, int numberOfPages, List<Rating> rates) {
        this.bookName = bookName;
        this.Author = author;
        this.publishingDate = publishingDate;
        this.category = category;
        this.numberOfPages = numberOfPages;
        this.Rates = rates != null ? rates : new ArrayList<>();
    }

    // Getters and Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getBookName() { return bookName; }
    public void setBookName(String bookName) { this.bookName = bookName; }

    public String getAuthor() { return Author; }
    public void setAuthor(String author) { Author = author; }

    public String getPublishingDate() { return publishingDate; }
    public void setPublishingDate(String publishingDate) { this.publishingDate = publishingDate; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public int getNumberOfPages() { return numberOfPages; }
    public void setNumberOfPages(int numberOfPages) { this.numberOfPages = numberOfPages; }

    public List<Rating> getRates() { return Rates; }
    public void setRates(List<Rating> rates) { Rates = rates; }

    // Same as your JS code: calculate average rating
    public double getAverageRate() {
        if (Rates == null || Rates.isEmpty()) return 0;
        double sum = 0;
        for (Rating r : Rates) {
            sum += r.getRate();
        }
        // Same as your .toFixed(1)
        return Math.round((sum / Rates.size()) * 10) / 10.0;
    }
}