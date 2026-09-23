import java.util.Scanner;

class BankAccount 
{
    private final String accountNumber;
    private final String ownerName;
    private double balance;
    
    private static int totalAccountsCreated = 0;

    public BankAccount(String accountNumber, String ownerName, double initialBalance) 
    {
        this.accountNumber = accountNumber;
        this.ownerName = ownerName;

        if (initialBalance >= 0) 
            {
            this.balance = initialBalance;
            } else 
            {
            this.balance = 0;
            System.out.println("Warning: Initial balance cannot be negative. Set to 0.0.");
            }
        totalAccountsCreated++;
    }

    // Getters
    public String getAccountNumber() 
    {
        return accountNumber;
    }

    public String getOwnerName() 
    {
        return ownerName;
    }

    public double getBalance() 
    {
        return balance;
    }

    public static int getTotalAccountsCreated() 
    {
        return totalAccountsCreated;
    }

    // Deposit method
    public boolean deposit(double amount) 
    {
        if (amount > 0)
        {
            this.balance += amount;
            return true;
        }
        return false;
    }

    public boolean withdraw(double amount) 
    {
        if (amount > 0 && amount <= this.balance) 
        {
            this.balance -= amount;
            return true;
        }
        return false;
    }
}

public class Exer2Jian2 
{
    public static void main(String[] args) 
    {

        try (Scanner scanner = new Scanner(System.in)) 
        {
            int numAccounts = 0;
            while (numAccounts < 1 || numAccounts > 5) 
                {
                System.out.print("Enter number of accounts to create (1-5): ");
                if (scanner.hasNextInt()) 
                    {
                    numAccounts = scanner.nextInt();
                    if (numAccounts < 1 || numAccounts > 5) 
                        {
                        System.out.println("Invalid amount. Please enter a value between 1 and 5.");
                        }
                    } else 
                {
                    System.out.println("Please enter a valid integer.");
                    scanner.next(); // Clear invalid input
                }
            }
            
            BankAccount[] accounts = new BankAccount[numAccounts];
            
            for (int i = 0; i < numAccounts; i++) 
                {
                System.out.println("\n--- Entering Account " + (i + 1) + " ---");
                System.out.print("Enter Account Number: ");
                String accNum = scanner.next();
                System.out.print("Enter Owner Name: ");
                String owner = scanner.next();
                
                double balance = -1;
                while (balance < 0) 
                    {
                    System.out.print("Enter Opening Balance: ");
                    if (scanner.hasNextDouble()) 
                        {
                        balance = scanner.nextDouble();
                        if (balance < 0) 
                            {
                                System.out.println("Opening balance cannot be negative!");
                            }
                        } else 
                    {
                        System.out.println("Invalid input. Please enter a valid number.");
                        scanner.next();
                    }
                }
                
                accounts[i] = new BankAccount(accNum, owner, balance);
            }
            
            System.out.print("\nEnter number of transactions to process: ");
            int numTransactions = scanner.nextInt();
            
            for (int i = 0; i < numTransactions; i++) 
                {
                System.out.println("\n--- Transaction " + (i + 1) + " ---");
                System.out.print("Enter Target Account Number: ");
                String targetAcc = scanner.next();
                System.out.print("Enter Transaction Type (D for Deposit / W for Withdrawal): ");
                char type = Character.toUpperCase(scanner.next().charAt(0));
                System.out.print("Enter Amount: ");
                double amount = scanner.nextDouble();
                
                BankAccount foundAccount = null;
                for (BankAccount acc : accounts) 
                    {
                    if (acc.getAccountNumber().equalsIgnoreCase(targetAcc)) 
                    {
                        foundAccount = acc;
                        break;
                    }
                }
                
                if (foundAccount == null) 
                {
                    System.out.println("Transaction Rejected: Account " + targetAcc + " not found.");
                } else 
                    {
                    switch (type) {
                        case 'D' -> 
                        {
                            if (foundAccount.deposit(amount)) 
                            {
                                System.out.println("Transaction Successful: Deposited PHP " + String.format("%.2f", amount) + " to Account " + targetAcc);
                            } else 
                            {
                                System.out.println("Transaction Rejected: Deposit amount must be positive.");
                            }
                        }
                        case 'W' -> 
                        {
                            if (foundAccount.withdraw(amount)) 
                            {
                                System.out.println("Transaction Successful: Withdrew PHP " + String.format("%.2f", amount) + " from Account " + targetAcc);
                            } else 
                            {
                                System.out.println("Transaction Rejected: Insufficient funds or invalid withdrawal amount.");
                            }
                        }
                        default -> System.out.println("Transaction Rejected: Invalid transaction type (Use D or W).");
                    }
                }
            }
            
            System.out.println("\n================ FINAL BALANCES ================");
            for (BankAccount acc : accounts) 
            {
                System.out.println("Account Number: " + acc.getAccountNumber() +
                        " | Owner: " + acc.getOwnerName() +
                        " | Final Balance: PHP " + String.format("%.2f", acc.getBalance()));
            }
            System.out.println("Total Accounts Created: " + BankAccount.getTotalAccountsCreated());
            System.out.println("================================================");
        }
    }
}
