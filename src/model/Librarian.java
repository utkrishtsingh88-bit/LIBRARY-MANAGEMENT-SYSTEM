package model;

/**
 * Represents a Librarian who manages the library.
 */
public class Librarian extends Person {
    private static final long serialVersionUID = 1L;
    
    private String password; // Simple security implementation
    
    public Librarian(String id, String name, String password) {
        super(id, name);
        this.password = password;
    }
    
    public boolean verifyPassword(String password) {
        return this.password.equals(password);
    }
    
    @Override
    public void display() {
        System.out.println("Librarian Profile:");
        System.out.println(this.toString());
    }
    
    @Override
    public String toString() {
        return super.toString() + " (Admin)";
    }
}
