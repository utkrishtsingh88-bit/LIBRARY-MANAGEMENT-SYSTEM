package model;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a library Member who can borrow books.
 */
public class Member extends Person {
    private static final long serialVersionUID = 1L;
    
    private double pendingFines;
    private List<String> borrowingHistory;
    
    public Member(String id, String name) {
        super(id, name);
        this.pendingFines = 0.0;
        this.borrowingHistory = new ArrayList<>();
    }
    
    public double getPendingFines() {
        return pendingFines;
    }
    
    public void addFine(double amount) {
        this.pendingFines += amount;
    }
    
    public void payFine(double amount) {
        this.pendingFines = Math.max(0, this.pendingFines - amount);
    }
    
    public List<String> getBorrowingHistory() {
        return borrowingHistory;
    }
    
    public void addToHistory(String bookTitle) {
        this.borrowingHistory.add(bookTitle);
    }
    
    @Override
    public void display() {
        System.out.println("Member Profile:");
        System.out.println(this.toString());
        System.out.println("Pending Fines: $" + String.format("%.2f", pendingFines));
        System.out.println("Borrowing History: " + borrowingHistory);
    }
    
    @Override
    public String toString() {
        return super.toString() + " (Member)";
    }
}
