import exception.BookNotAvailableException;
import exception.MemberNotFoundException;
import model.Book;
import model.Librarian;
import model.Member;
import service.LibraryService;

import java.util.List;
import java.util.Scanner;

/**
 * Main application class providing a console-based menu interface.
 */
public class Main {
    private static LibraryService libraryService;
    private static Scanner scanner;
    private static Librarian admin;

    public static void main(String[] args) {
        scanner = new Scanner(System.in);
        libraryService = LibraryService.loadState();
        
        // Simple hardcoded admin for demonstration
        admin = new Librarian("A01", "Admin User", "admin123");

        System.out.println("===============================================");
        System.out.println("    Welcome to the Library Management System   ");
        System.out.println("===============================================");

        boolean exit = false;
        while (!exit) {
            printMenu();
            System.out.print("Enter your choice: ");
            String choice = scanner.nextLine().trim();

            try {
                switch (choice) {
                    case "1":
                        addBook();
                        break;
                    case "2":
                        registerMember();
                        break;
                    case "3":
                        issueBook();
                        break;
                    case "4":
                        returnBook();
                        break;
                    case "5":
                        searchBooks();
                        break;
                    case "6":
                        viewMember();
                        break;
                    case "7":
                        listAllBooks();
                        break;
                    case "8":
                        payFine();
                        break;
                    case "0":
                        exit = true;
                        System.out.println("Thank you for using the Library Management System. Goodbye!");
                        break;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            } catch (Exception e) {
                System.out.println("An unexpected error occurred: " + e.getMessage());
            }
            System.out.println();
        }
        scanner.close();
    }

    private static void printMenu() {
        System.out.println("\n--- Main Menu ---");
        System.out.println("1. Add Book (Admin)");
        System.out.println("2. Register Member (Admin)");
        System.out.println("3. Issue Book");
        System.out.println("4. Return Book");
        System.out.println("5. Search Books");
        System.out.println("6. View Member Profile");
        System.out.println("7. List All Books");
        System.out.println("8. Pay Fines");
        System.out.println("0. Exit");
    }

    private static boolean authenticate() {
        System.out.print("Enter admin password: ");
        String pwd = scanner.nextLine();
        if (admin.verifyPassword(pwd)) {
            return true;
        } else {
            System.out.println("Authentication failed!");
            return false;
        }
    }

    private static void addBook() {
        if (!authenticate()) return;
        
        System.out.print("Enter Book ID: ");
        String id = scanner.nextLine();
        System.out.print("Enter Book Title: ");
        String title = scanner.nextLine();
        System.out.print("Enter Book Author: ");
        String author = scanner.nextLine();
        System.out.print("Enter Total Copies: ");
        
        try {
            int copies = Integer.parseInt(scanner.nextLine());
            Book book = new Book(id, title, author, copies);
            libraryService.addBook(book);
            System.out.println("Book added successfully.");
        } catch (NumberFormatException e) {
            System.out.println("Invalid input for copies. Must be a number.");
        }
    }

    private static void registerMember() {
        if (!authenticate()) return;
        
        System.out.print("Enter Member ID: ");
        String id = scanner.nextLine();
        System.out.print("Enter Member Name: ");
        String name = scanner.nextLine();
        
        Member member = new Member(id, name);
        libraryService.registerMember(member);
        System.out.println("Member registered successfully.");
    }

    private static void issueBook() {
        System.out.print("Enter Member ID: ");
        String memberId = scanner.nextLine();
        System.out.print("Enter Book ID: ");
        String bookId = scanner.nextLine();
        System.out.print("Enter Days to Return (e.g., 14): ");
        
        try {
            int days = Integer.parseInt(scanner.nextLine());
            libraryService.issueBook(memberId, bookId, days);
            System.out.println("Book issued successfully.");
        } catch (NumberFormatException e) {
            System.out.println("Invalid input for days.");
        } catch (MemberNotFoundException | BookNotAvailableException | RuntimeException e) {
            System.out.println("Issue failed: " + e.getMessage());
        }
    }

    private static void returnBook() {
        System.out.print("Enter Book ID to return: ");
        String bookId = scanner.nextLine();
        try {
            libraryService.returnBook(bookId);
        } catch (Exception e) {
            System.out.println("Return failed: " + e.getMessage());
        }
    }

    private static void searchBooks() {
        System.out.println("Search by: 1. Title  2. Author");
        System.out.print("Choice: ");
        String choice = scanner.nextLine();
        
        List<Book> results = null;
        if ("1".equals(choice)) {
            System.out.print("Enter title keyword: ");
            results = libraryService.searchByTitle(scanner.nextLine());
        } else if ("2".equals(choice)) {
            System.out.print("Enter author keyword: ");
            results = libraryService.searchByAuthor(scanner.nextLine());
        } else {
            System.out.println("Invalid choice.");
            return;
        }
        
        if (results == null || results.isEmpty()) {
            System.out.println("No books found matching the criteria.");
        } else {
            System.out.println("--- Search Results ---");
            for (Book b : results) {
                b.displayDetails();
            }
        }
    }

    private static void viewMember() {
        System.out.print("Enter Member ID: ");
        String memberId = scanner.nextLine();
        try {
            Member m = libraryService.getMember(memberId);
            m.display();
        } catch (MemberNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void listAllBooks() {
        List<Book> allBooks = libraryService.getAllBooks();
        if (allBooks.isEmpty()) {
            System.out.println("No books in the library yet.");
        } else {
            System.out.println("--- All Books ---");
            for (Book b : allBooks) {
                b.displayDetails();
            }
        }
    }
    
    private static void payFine() {
        System.out.print("Enter Member ID: ");
        String memberId = scanner.nextLine();
        try {
            Member m = libraryService.getMember(memberId);
            if (m.getPendingFines() == 0) {
                System.out.println("Member has no pending fines.");
                return;
            }
            System.out.println("Current pending fine: $" + m.getPendingFines());
            System.out.print("Enter amount to pay: $");
            double amount = Double.parseDouble(scanner.nextLine());
            m.payFine(amount);
            
            // Force save by re-registering (since we mutated the object)
            // A more robust way would be adding payFine to LibraryService, but this works for simple OOP demo.
            libraryService.registerMember(m);
            System.out.println("Payment processed. Remaining fine: $" + String.format("%.2f", m.getPendingFines()));
            
        } catch (MemberNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("Invalid amount format.");
        }
    }
}
