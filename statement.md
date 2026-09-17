# Problem Statement & Design

## Problem Statement
Libraries require an efficient way to keep track of their book inventory, member registrations, and book lending transactions. Traditional manual ledger systems are prone to error, difficult to search, and time-consuming when calculating late fees. 

**Scope:** This project provides a robust, purely console-based system to automate core library operations. It is designed to be lightweight, requiring zero third-party installations beyond a standard Java Development Kit (JDK).

**Target Users:** 
- **Librarians (Admins):** Can add books, register members, and manage the system.
- **Library Staff:** Can process book issues, returns, and accept fine payments.

## Design Diagrams (Textual Description)

### Use Case Diagram
- **Actor (Librarian/Admin)** interacts with:
  - Add Book
  - Register Member
- **Actor (Staff/User)** interacts with:
  - Search Book (by title or author)
  - View Member Profile
  - Issue Book
  - Return Book
  - Pay Fine

### Class Diagram
- **`Person` (Abstract)**: Contains `id`, `name`. Methods: `display()`.
  - **`Member` (extends Person)**: Adds `pendingFines`, `borrowingHistory`.
  - **`Librarian` (extends Person)**: Adds `password`.
- **`Item` (Abstract)**: Contains `id`, `title`.
  - **`Book` (extends Item, implements Searchable)**: Adds `author`, `totalCopies`, `availableCopies`.
- **`Searchable` (Interface)**: Defines `searchByTitle(String)`, `searchByAuthor(String)`.
- **`LibraryService` (implements Searchable)**: Composes `Book`s and `Member`s. Manages system state (`currentIssues`, `dueDates`).
- **`FileStorage` (Utility)**: Static methods for Object Input/Output streams.
- **Exceptions**: `MemberNotFoundException`, `BookNotAvailableException`.

### Sequence Diagram: "Issue Book" Flow
1. **User** selects "Issue Book" in CLI (`Main`).
2. `Main` prompts for `memberId`, `bookId`, `daysToReturn`.
3. `Main` calls `libraryService.issueBook(memberId, bookId, daysToReturn)`.
4. `LibraryService` calls `getMember(memberId)`. Throws `MemberNotFoundException` if missing.
5. `LibraryService` checks if `member.getPendingFines() > 0`. If yes, throws `RuntimeException`.
6. `LibraryService` calls `getBook(bookId)`.
7. `LibraryService` calls `book.issueCopy()`. Throws `BookNotAvailableException` if unavailable.
8. `LibraryService` updates `currentIssues` and `dueDates` maps.
9. `LibraryService` calls `member.addToHistory(...)`.
10. `LibraryService` calls `saveState()` -> `FileStorage.saveToFile()`.
11. Returns success message to `Main`, which displays it to the **User**.

### Data Schema / Storage Design
Instead of a relational database or CSV, this application uses **Java Object Serialization** to persist the entire `LibraryService` state. 
- **File:** `library_data.ser`
- **Stored Data Structure:**
  - `List<Book>` books
  - `Map<String, Member>` members
  - `Map<String, String>` currentIssues (maps bookId to memberId)
  - `Map<String, LocalDate>` dueDates (maps bookId to dueDate)
- **Advantage:** Highly performant for in-memory operations and automatically maintains all object relationships and complex data types (like `LocalDate` and `List<String>`) without manual parsing logic.
