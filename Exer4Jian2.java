package com.mycompany.exer4jian2;

import java.util.Scanner;

class Book 
{
    private final String bookId;
    private final String title;
    private boolean available;

    public Book(String bookId, String title) 
    {
        this.bookId = bookId;
        this.title = title;
        this.available = true;
    }

    public String getBookId() 
    {
        return this.bookId;
    }

    public String getTitle() 
    {
        return this.title;
    }

    public boolean isAvailable() 
    {
        return this.available;
    }

    public void setAvailable(boolean available) 
    {
        this.available = available;
    }
}

class Member 
{
    private final String memberId;
    private final String name;
    private final Book[] borrowedBooks;
    private int borrowCount;

    public Member(String memberId, String name) 
    {
        this.memberId = memberId;
        this.name = name;
        this.borrowedBooks = new Book[3];
        this.borrowCount = 0;
    }

    public String getMemberId() 
    {
        return this.memberId;
    }

    public String getName() 
    {
        return this.name;
    }

    public int getBorrowCount() 
    {
        return this.borrowCount;
    }

    public boolean borrowBook(Book book) 
    {
        if (book == null) 
        {
            System.out.println("Action failed: Invalid book.");
            return false;
        }
        if (!book.isAvailable()) 
        {
            System.out.println("Second borrow rejected because " + book.getBookId() + " is unavailable.");
            return false;
        }
        if (this.borrowCount >= 3) 
        {
            System.out.println("Fourth borrow rejected: " + this.memberId + " already reached maximum of 3 books.");
            return false;
        }

        this.borrowedBooks[this.borrowCount] = book;
        this.borrowCount++;
        book.setAvailable(false);
        System.out.println(this.memberId + " borrowed " + book.getBookId());
        return true;
    }

    public boolean returnBook(String bookId) 
    {
        for (int i = 0; i < this.borrowCount; i++) 
        {
            if (this.borrowedBooks[i] != null && this.borrowedBooks[i].getBookId().equals(bookId)) {
                Book returnedBook = this.borrowedBooks[i];
                returnedBook.setAvailable(true);

                for (int j = i; j < this.borrowCount - 1; j++) 
                {
                    this.borrowedBooks[j] = this.borrowedBooks[j + 1];
                }
                this.borrowedBooks[this.borrowCount - 1] = null;
                this.borrowCount--;

                System.out.println(this.memberId + " returned " + bookId);
                return true;
            }
        }
        System.out.println("Return failed: Book " + bookId + " not found in " + this.memberId + "'s list.");
        return false;
    }

    public void printSummary() 
    {
        System.out.print(this.memberId + " " + this.name + " ends with: ");
        if (this.borrowCount == 0) 
        {
            System.out.println("no borrowed books");
        } else 
        {
            for (int i = 0; i < this.borrowCount; i++) 
            {
                System.out.print(this.borrowedBooks[i].getBookId() + " " + this.borrowedBooks[i].getTitle());
                if (i < this.borrowCount - 1) System.out.print(", ");
            }
            System.out.println(" (" + this.borrowCount + " borrowed book(s))");
        }
    }
}

public class Exer4Jian2 
{
    public static void main(String[] args) 
    {
        try (Scanner scanner = new Scanner(System.in)) 
        {
            System.out.println("=== Library Borrowing System ===");
            System.out.print("Enter number of books: ");
            int numBooks = Integer.parseInt(scanner.nextLine().trim());
            Book[] books = new Book[numBooks];
            for (int i = 0; i < numBooks; i++) 
            {
                System.out.print("Book " + (i+1) + " (ID Title): ");
                String line = scanner.nextLine().trim();
                String[] parts = line.split("\\s+", 2);
                books[i] = new Book(parts[0], parts.length > 1 ? parts[1] : "");
            }
            
            System.out.print("Enter number of members: ");
            int numMembers = Integer.parseInt(scanner.nextLine().trim());
            Member[] members = new Member[numMembers];
            for (int i = 0; i < numMembers; i++) 
            {
                System.out.print("Member " + (i+1) + " (ID Name): ");
                String line = scanner.nextLine().trim();
                String[] parts = line.split("\\s+", 2);
                members[i] = new Member(parts[0], parts.length > 1 ? parts[1] : "");
            }
            
            System.out.print("Enter action count: ");
            int actionCount = Integer.parseInt(scanner.nextLine().trim());
            for (int i = 0; i < actionCount; i++) 
            {
                System.out.print("Action " + (i+1)  + " (MemberID B/R BookID): ");
                String line = scanner.nextLine().trim();
                String[] parts = line.split("\\s+");
                
                if (parts.length < 3) 
                {
                    System.out.println("Invalid format. Expected format: M1 B B1");
                    i--;
                    continue;
                }
                
                String mId = parts[0];
                char action = parts[1].charAt(0);
                String bId = parts[2];
                
                Member targetMember = findMember(members, mId);
                Book targetBook = findBook(books, bId);
                
                if (targetMember != null && targetBook != null) 
                {
                    if (action == 'B' || action == 'b') 
                    {
                        targetMember.borrowBook(targetBook);
                    } else if (action == 'R' || action == 'r') 
                    {
                        targetMember.returnBook(bId);
                    }
                } else 
                {
                    System.out.println("Action failed: Member or Book ID not found.");
                }
            }
            
            System.out.println("\n--- Final Summaries ---");
            for (Member m : members) 
            {
                m.printSummary();
            }
            
            System.out.print("Available books at the end: ");
            boolean anyAvailable = false;
            for (Book b : books) 
            {
                if (b.isAvailable()) 
                {
                    System.out.print(b.getBookId() + " " + b.getTitle() + "; ");
                    anyAvailable = true;
                }
            }
            if (!anyAvailable) 
            {
                System.out.println("no available books remaining.");
            } else 
            {
                System.out.println();
            }
        }
    }

    private static Member findMember(Member[] members, String id) 
    {
        for (Member m : members) 
        {
            if (m.getMemberId().equals(id)) return m;
        }
        return null;
    }

    private static Book findBook(Book[] books, String id) 
    {
        for (Book b : books) 
        {
            if (b.getBookId().equals(id)) return b;
        }
        return null;
    }
}