import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

// User class to store user details
class User {
    private String userId;
    private String pin;
    private double balance;
    
    // Constructor
    public User(String userId, String pin, double initialBalance) {
        this.userId = userId;
        this.pin = pin;
        this.balance = initialBalance;
    }

    // Getters
    public String getUserId() {
        return userId;
    }

    public String getPin() {
        return pin;
    }

    public double getBalance() {
        return balance;
    }

    // Method to perform withdrawal
    public boolean withdraw(double amount) {
        if (amount > 0 && amount <= balance) {
            balance -= amount;
            return true;
        }
        return false;
    }

    // Method to perform deposit
    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
        }
    }

    // Method to perform transfer
    public boolean transfer(User recipient, double amount) {
        if (withdraw(amount)) {
            recipient.deposit(amount);
            return true;
        }
        return false;
    }
}

// ATM class to handle user interactions
public class AtmInterface {
    private Map<String, User> users;

    // Constructor
    public AtmInterface() {
        users = new HashMap<>();
        // Adding a dummy user for demonstration
        users.put("123456", new User("123456", "1234", 1000.0));
    }

    // Method to start the ATM
    public void start() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Welcome to the ATM!");

        // Prompt user for user id and pin
        System.out.print("Enter User ID: ");
        String userId = scanner.nextLine();
        System.out.print("Enter PIN: ");
        String pin = scanner.nextLine();

        // Check if user exists and pin is correct
        User user = users.get(userId);
        if (user != null && user.getPin().equals(pin)) {
            System.out.println("Login successful!");

            // Unlock ATM functionalities
            performTransactions(user);
        } else {
            System.out.println("Login failed. Invalid credentials.");
        }

        scanner.close();
    }

    // Method to perform ATM transactions
    private void performTransactions(User user) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            // Display menu
            System.out.println("\nATM Menu:");
            System.out.println("1. View Transactions History");
            System.out.println("2. Withdraw");
            System.out.println("3. Deposit");
            System.out.println("4. Transfer");
            System.out.println("5. Quit");

            // Get user choice
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    // View Transactions History (Dummy implementation)
                    System.out.println("Transaction History: No transactions available.");
                    break;
                case 2:
                    // Withdraw
                    System.out.print("Enter withdrawal amount: ");
                    double withdrawAmount = scanner.nextDouble();
                    if (user.withdraw(withdrawAmount)) {
                        System.out.println("Withdrawal successful. Remaining balance: " + user.getBalance());
                    } else {
                        System.out.println("Withdrawal failed. Insufficient funds or invalid amount.");
                    }
                    break;
                case 3:
                    // Deposit
                    System.out.print("Enter deposit amount: ");
                    double depositAmount = scanner.nextDouble();
                    user.deposit(depositAmount);
                    System.out.println("Deposit successful. Updated balance: " + user.getBalance());
                    break;
                case 4:
                    // Transfer
                    System.out.print("Enter recipient's User ID: ");
                    String recipientId = scanner.next();
                    User recipient = users.get(recipientId);
                    if (recipient != null && !recipient.equals(user)) {
                        System.out.print("Enter transfer amount: ");
                        double transferAmount = scanner.nextDouble();
                        if (user.transfer(recipient, transferAmount)) {
                            System.out.println("Transfer successful. Remaining balance: " + user.getBalance());
                        } else {
                            System.out.println("Transfer failed. Insufficient funds or invalid amount.");
                        }
                    } else {
                        System.out.println("Invalid recipient.");
                    }
                    break;
                case 5:
                    // Quit
                    System.out.println("Thank you for using the ATM. Goodbye!");
                    return;
                default:
                    System.out.println("Invalid choice. Please enter a valid option.");
            }
        }
}

    // Main method to run the ATM
    public static void main(String[] args) {
        AtmInterface atm = new AtmInterface();
        atm.start();
    }
}
