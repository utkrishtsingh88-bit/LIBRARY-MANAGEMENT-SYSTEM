package model;

/**
 * Represents a Book in the library.
 */
public class Book extends Item {
    private static final long serialVersionUID = 1L;
    
    private String author;
    private int totalCopies;
    private int availableCopies;
    
    public Book(String id, String title, String author, int totalCopies) {
        super(id, title);
        this.author = author;
        this.totalCopies = totalCopies;
        this.availableCopies = totalCopies;
    }
    
    public String getAuthor() {
        return author;
    }
    
    public void setAuthor(String author) {
        this.author = author;
    }
    
    public int getTotalCopies() {
        return totalCopies;
    }
    
    public void setTotalCopies(int totalCopies) {
        this.totalCopies = totalCopies;
    }
    
    public int getAvailableCopies() {
        return availableCopies;
    }
    
    public void setAvailableCopies(int availableCopies) {
        this.availableCopies = availableCopies;
    }
    
    public boolean issueCopy() {
        if (availableCopies > 0) {
            availableCopies--;
            return true;
        }
        return false;
    }
    
    public void returnCopy() {
        if (availableCopies < totalCopies) {
            availableCopies++;
        }
    }
    
    @Override
    public void displayDetails() {
        System.out.println(this.toString());
    }
    
    @Override
    public String toString() {
        return "Book [ID=" + id + ", Title=" + title + ", Author=" + author 
               + ", Available=" + availableCopies + "/" + totalCopies + "]";
    }
}
