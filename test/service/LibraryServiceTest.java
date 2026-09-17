package service;

import exception.BookNotAvailableException;
import exception.MemberNotFoundException;
import model.Book;
import model.Member;
import org.junit.Before;
import org.junit.Test;

import java.io.File;

import static org.junit.Assert.*;

public class LibraryServiceTest {

    private LibraryService libraryService;

    @Before
    public void setUp() {
        // Delete the serialized file to ensure a clean state for testing
        File file = new File("library_data.ser");
        if (file.exists()) {
            file.delete();
        }
        libraryService = new LibraryService();
    }

    @Test
    public void testAddBookAndSearch() {
        Book book = new Book("B1", "Effective Java", "Joshua Bloch", 5);
        libraryService.addBook(book);

        assertEquals(1, libraryService.getAllBooks().size());
        assertEquals("Effective Java", libraryService.searchByTitle("Effective").get(0).getTitle());
    }

    @Test
    public void testRegisterMember() throws MemberNotFoundException {
        Member member = new Member("M1", "Alice");
        libraryService.registerMember(member);

        Member retrieved = libraryService.getMember("M1");
        assertNotNull(retrieved);
        assertEquals("Alice", retrieved.getName());
    }

    @Test(expected = MemberNotFoundException.class)
    public void testGetNonExistentMember() throws MemberNotFoundException {
        libraryService.getMember("M999");
    }

    @Test
    public void testIssueBook_Success() throws MemberNotFoundException, BookNotAvailableException {
        Book book = new Book("B1", "Test Book", "Author", 1);
        libraryService.addBook(book);
        Member member = new Member("M1", "Alice");
        libraryService.registerMember(member);

        libraryService.issueBook("M1", "B1", 14);
        
        // Assert copy was decremented
        Book updatedBook = libraryService.getBook("B1");
        assertEquals(0, updatedBook.getAvailableCopies());
    }
    
    @Test(expected = BookNotAvailableException.class)
    public void testIssueBook_NoCopiesAvailable() throws MemberNotFoundException, BookNotAvailableException {
        Book book = new Book("B1", "Test Book", "Author", 0);
        libraryService.addBook(book);
        Member member = new Member("M1", "Alice");
        libraryService.registerMember(member);

        libraryService.issueBook("M1", "B1", 14);
    }
    
    @Test(expected = RuntimeException.class)
    public void testIssueBook_MemberHasFines() throws MemberNotFoundException, BookNotAvailableException {
        Book book = new Book("B1", "Test Book", "Author", 5);
        libraryService.addBook(book);
        
        Member member = new Member("M1", "Alice");
        member.addFine(10.0);
        libraryService.registerMember(member);

        libraryService.issueBook("M1", "B1", 14);
    }
}
