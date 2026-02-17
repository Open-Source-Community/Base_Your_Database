import com.mongodb.client.*;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import org.bson.Document;
import org.bson.types.ObjectId;
import Example.models.Book;
import Example.models.Book.Rating;
import Example.models.User;
import java.util.*;

public class Main {
    private static MongoCollection<Document> bookCollection;
    private static MongoCollection<Document> userCollection;

    public static void main(String[] args) throws Exception {
        MongoClient mongoClient = MongoClients.create("mongodb+srv://USERNAME_db_user:YOUR_PASSWORD@osc.u7txp6n.mongodb.net/");
        MongoDatabase database = mongoClient.getDatabase("test");
        bookCollection = database.getCollection("books");
        userCollection = database.getCollection("users");

        bookCollection.deleteMany(new Document());
        userCollection.deleteMany(new Document());

        // Create 3 users
        String uid1 = createUser("john_doe", "john@email.com", "pass123");
        String uid2 = createUser("jane_smith", "jane@email.com", "pass456");
        String uid3 = createUser("bob_wilson", "bob@email.com", "pass789");
        System.out.println("✅ Created users: " + uid1 + ", " + uid2 + ", " + uid3);

        // Get user by ID
        User user = getUserById(uid1);
        System.out.println("✅ Found user: " + user.getUsername());

        // Create 3 books
        List<Rating> r1 = Arrays.asList(new Rating("john_doe",5), new Rating("jane_smith",4));
        String bid1 = createBook("The Great Gatsby","F. Scott Fitzgerald","1925-04-10","Fiction",180,r1);

        List<Rating> r2 = Arrays.asList(new Rating("bob_wilson",5), new Rating("john_doe",5));
        String bid2 = createBook("1984","George Orwell","1949-06-08","Dystopian",328,r2);

        String bid3 = createBook("To Kill a Mockingbird","Harper Lee","1960-07-11","Classic",281,new ArrayList<>());
        System.out.println("✅ Created books: " + bid1 + ", " + bid2 + ", " + bid3);

        // Get all books
        System.out.println("\n ALL BOOKS:");
        for (Book b : getAllBooks())
            System.out.println("   " + b.getBookName() + " by " + b.getAuthor());

        // Get book by ID with average rating
        Book book = getBookById(bid1);
        System.out.println("\n " + book.getBookName() + " - Avg Rating: " + book.getAverageRate());

        // Update book
        updateBook(bid2, "category", "Science Fiction");
        System.out.println("✅ Updated book category");

        // Delete book
        deleteBook(bid3);
        System.out.println("✅ Deleted book");

        // Final counts
        System.out.println("\n Total Users: " + bookCollection.countDocuments() + ", Total Books: " + userCollection.countDocuments());
        mongoClient.close();
    }

    private static String createUser(String username, String email, String password) {
        Document doc = new Document("username",username).append("email",email).append("password",password)
                .append("readBooks",Arrays.asList()).append("booksToRead",Arrays.asList());
        userCollection.insertOne(doc);
        return doc.getObjectId("_id").toString();
    }

    private static User getUserById(String id) {
        Document doc = userCollection.find(Filters.eq("_id", new ObjectId(id))).first();
        if (doc == null) return null;
        User u = new User();
        u.setId(doc.getObjectId("_id").toString());
        u.setUsername(doc.getString("username"));
        u.setEmail(doc.getString("email"));
        u.setPassword(doc.getString("password"));
        u.setReadBooks(doc.getList("readBooks", String.class));
        u.setBooksToRead(doc.getList("booksToRead", String.class));
        return u;
    }

    private static String createBook(String name, String author, String date, String cat, int pages, List<Rating> rates) {
        List<Document> rateDocs = new ArrayList<>();
        for (Rating r : rates) rateDocs.add(new Document("userName",r.getUserName()).append("Rate",r.getRate()));

        Document doc = new Document("bookName",name).append("Author",author).append("publishingDate",date)
                .append("category",cat).append("numberOfPages",pages).append("Rates",rateDocs);
        bookCollection.insertOne(doc);
        return doc.getObjectId("_id").toString();
    }

    private static List<Book> getAllBooks() {
        List<Book> books = new ArrayList<>();
        for (Document doc : bookCollection.find()) {
            Book b = new Book();
            b.setId(doc.getObjectId("_id").toString());
            b.setBookName(doc.getString("bookName"));
            b.setAuthor(doc.getString("Author"));
            b.setPublishingDate(doc.getString("publishingDate"));
            b.setCategory(doc.getString("category"));
            b.setNumberOfPages(doc.getInteger("numberOfPages"));

            List<Document> rateDocs = (List<Document>) doc.get("Rates");
            if (rateDocs != null) {
                List<Rating> rates = new ArrayList<>();
                for (Document r : rateDocs) rates.add(new Rating(r.getString("userName"), r.getInteger("Rate")));
                b.setRates(rates);
            }
            books.add(b);
        }
        return books;
    }

    private static Book getBookById(String id) {
        Document doc = bookCollection.find(Filters.eq("_id", new ObjectId(id))).first();
        if (doc == null) return null;
        Book b = new Book();
        b.setId(doc.getObjectId("_id").toString());
        b.setBookName(doc.getString("bookName"));
        b.setAuthor(doc.getString("Author"));
        b.setPublishingDate(doc.getString("publishingDate"));
        b.setCategory(doc.getString("category"));
        b.setNumberOfPages(doc.getInteger("numberOfPages"));

        List<Document> rateDocs = (List<Document>) doc.get("Rates");
        if (rateDocs != null) {
            List<Rating> rates = new ArrayList<>();
            for (Document r : rateDocs) rates.add(new Rating(r.getString("userName"), r.getInteger("Rate")));
            b.setRates(rates);
        }
        return b;
    }

    private static boolean updateBook(String id, String field, Object value) {
        return bookCollection.updateOne(Filters.eq("_id", new ObjectId(id)), Updates.set(field, value)).getModifiedCount() > 0;
    }

    private static boolean deleteBook(String id) {
        return bookCollection.deleteOne(Filters.eq("_id", new ObjectId(id))).getDeletedCount() > 0;
    }


}