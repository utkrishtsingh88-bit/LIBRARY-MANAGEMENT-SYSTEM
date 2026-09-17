package service;

import exception.BookNotAvailableException;
import exception.MemberNotFoundException;
import model.Book;
import model.Member;
import model.Searchable;
import util.FileStorage;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Service class that manages the core logic of the library system.
 */
public class LibraryService implements Searchable<Book>, Serializable {
    private static final long serialVersionUID = 1L;
    
    private List<Book> books;
    private Map<String, Member> members;
    
    // Tracks which member borrowed which book (simplified: bookId -> memberId)
    private Map<String, String> currentIssues;
    // Tracks due dates (bookId -> dueDate)
    private Map<String, LocalDate> dueDates;
    
    private static final String DATA_FILE = "library_data.ser";

    public LibraryService() {
        this.books = new ArrayList<>();
        this.members = new HashMap<>();
        this.currentIssues = new HashMap<>();
        this.dueDates = new HashMap<>();
    }

    // --- Book Management ---
    public void addBook(Book book) {
        books.add(book);
        saveState();
    }
    
    public void removeBook(String bookId) {
        books.removeIf(b -> b.getId().equals(bookId));
        saveState();
    }
    
    public List<Book> getAllBooks() {
        return books;
    }
    
    public Book getBook(String id) {
        return books.stream().filter(b -> b.getId().equals(id)).findFirst().orElse(null);
    }

    // --- Member Management ---
    public void registerMember(Member member) {
        members.put(member.getId(), member);
        saveState();
    }
    
    public Member getMember(String id) throws MemberNotFoundException {
        Member m = members.get(id);
        if (m == null) {
            throw new MemberNotFoundException("Member with ID " + id + " not found.");
        }
        return m;
    }
    
    public void removeMember(String id) {
        members.remove(id);
        saveState();
    }

    // --- Issue and Return ---
    public void issueBook(String memberId, String bookId, int daysToReturn) throws MemberNotFoundException, BookNotAvailableException {
        Member member = getMember(memberId);
        
        if (member.getPendingFines() > 0) {
            throw new RuntimeException("Cannot issue book. Member has unpaid fines: $" + member.getPendingFines());
        }
        
        Book book = getBook(bookId);
        if (book == null) {
            throw new RuntimeException("Book not found.");
        }
        
        if (!book.issueCopy()) {
            throw new BookNotAvailableException("No copies of '" + book.getTitle() + "' are currently available.");
        }
        
        currentIssues.put(bookId, memberId);
        dueDates.put(bookId, LocalDate.now().plusDays(daysToReturn));
        member.addToHistory(book.getTitle() + " (Issued on " + LocalDate.now() + ")");
        
        saveState();
    }
    
    public void returnBook(String bookId) {
        String memberId = currentIssues.get(bookId);
        if (memberId == null) {
            throw new RuntimeException("Book is not currently issued to anyone.");
        }
        
        try {
            Member member = getMember(memberId);
            LocalDate dueDate = dueDates.get(bookId);
            LocalDate returnDate = LocalDate.now();
            
            FineCalculator calculator = new FineCalculator();
            double fine = calculator.calculateFine(dueDate, returnDate);
            
            if (fine > 0) {
                member.addFine(fine);
                System.out.println("Book returned late. A fine of $" + fine + " has been added.");
            } else {
                System.out.println("Book returned on time.");
            }
            
            Book book = getBook(bookId);
            if (book != null) {
                book.returnCopy();
            }
            
            currentIssues.remove(bookId);
            dueDates.remove(bookId);
            
            saveState();
            
        } catch (MemberNotFoundException e) {
            System.err.println("Error processing return: " + e.getMessage());
        }
    }

    // --- Searchable Implementation ---
    @Override
    public List<Book> searchByTitle(String title) {
        return books.stream()
                .filter(b -> b.getTitle().toLowerCase().contains(title.toLowerCase()))
                .collect(Collectors.toList());
    }

    @Override
    public List<Book> searchByAuthor(String author) {
        return books.stream()
                .filter(b -> b.getAuthor().toLowerCase().contains(author.toLowerCase()))
                .collect(Collectors.toList());
    }
    
    // --- Data Persistence ---
    private void saveState() {
        FileStorage.saveToFile(this, DATA_FILE);
    }
    
    public static LibraryService loadState() {
        LibraryService loadedService = (LibraryService) FileStorage.loadFromFile(DATA_FILE);
        return loadedService != null ? loadedService : new LibraryService();
    }
}
