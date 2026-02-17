package Example.models;

import java.util.List;
import java.util.ArrayList;

public class User {
    private String id;              // MongoDB _id
    private String username;
    private String email;
    private String password;
    private List<String> readBooks;      // Book IDs (same as ObjectId ref)
    private List<String> booksToRead;    // Book IDs (same as ObjectId ref)

    public User() {}

    public User(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.readBooks = new ArrayList<>();
        this.booksToRead = new ArrayList<>();
    }

    // Getters and Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public List<String> getReadBooks() { return readBooks; }
    public void setReadBooks(List<String> readBooks) { this.readBooks = readBooks; }

    public List<String> getBooksToRead() { return booksToRead; }
    public void setBooksToRead(List<String> booksToRead) { this.booksToRead = booksToRead; }
}