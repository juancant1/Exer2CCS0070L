#Answers to Open-Ended Questions

PROBLEM 2
1. Why is balance declared private?   
Declaring balance private ensures proper encapsulation. It protects the internal state of the object from arbitrary modifications or external manipulation directly from main(), forcing all changes to go through validated operations like deposit() and withdraw().

2. Why is withdraw() better than changing balance directly in main()?   
withdraw() encapsulates business rules and validation logic (e.g., checking for negative amounts or insufficient funds) within the class itself. If main() directly modified balance, invalid operations (like overdrafts or negative amounts) could easily slip through and violate data integrity.

3. What condition prevents an overdraft?   
The guard condition amount <= this.balance inside the withdraw() method ensures that a withdrawal only occurs if the requested amount does not exceed the available balance.

4. How would the design change if every account needed its own transaction history?
Each BankAccount object would need a new instance array (or data structure) to hold Transaction objects (e.g., Transaction[] transactions or String[] history) alongside a tracker variable for the count. 

PROBLEM 4
1. Why is the relationship between Member and Book better described as association/has-a rather than is-a?
An is-a relationship represents inheritance (e.g., a Member is a type of Person). A Member is not a subtype of Book. Instead, a Member temporarily borrows or holds references to Book objects, which represents an association / has-a relationship.

2. Why should the borrowedBooks array remain private?
Keeping borrowedBooks private enforces encapsulation. It prevents external classes like main() from directly adding, replacing, or clearing books without applying business rules—such as checking book availability or enforcing the 3-book maximum limit.

3. What responsibilities belong to Member instead of main()?   The Member class is responsible for managing its own internal collection of borrowed books, validating whether it can accept a new book (checking maximum capacity), and handling the logic to find and remove a book when returning it. main() should only coordinate user input/output and invoke these controlled methods.

4. What additional class could be introduced if the library must track due dates and fines?   A Loan or BorrowTransaction class could be introduced. This class would act as a bridge connecting a Member and a Book, storing relationship-specific metadata such as issueDate, dueDate, returnDate, and fineAmount. 
