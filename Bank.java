/**
 * The Bank class simulates a multi-threaded banking system.
 * It initializes bank accounts, generates random transactions, 
 * and assigns clerks to process these transactions concurrently.
 * After the simulation, it prints the final balances of all accounts.
 */

import java.util.*;

public class Bank {
    private final Map<Integer, BankAccount> accounts; // Map of account numbers to BankAccount objects
    private final List<BankClerk> clerks; // List of bank clerks
    private static final int NUM_CLERKS = 10; // Number of clerks
    private static final int NUM_ACCOUNTS = 5; // Number of bank accounts
    private static final int NUM_TRANSACTIONS = 50; // Number of random transactions to generate

    //Constructor initializes the bank with accounts, random transactions,
    //and clerks to process the transactions.
    public Bank() {
        accounts = new HashMap<>();
        clerks = new ArrayList<>();

        // Initialize bank accounts
        for (int i = 0; i < NUM_ACCOUNTS; i++) {
            accounts.put(i, new BankAccount(i));
        }

        // Generate random transactions
        Random random = new Random();
        List<Transaction> transactions = new ArrayList<>();
        for (int i = 0; i < NUM_TRANSACTIONS; i++) {
            int accountNum = random.nextInt(NUM_ACCOUNTS); // Random account number
            double amount = random.nextInt(2001) - 1000; // Random amount between -1000 and 1000
            transactions.add(new Transaction(accountNum, amount));
        }

        TransactionPool transactionPool = new TransactionPool(transactions);

        // Initialize clerks
        for (int i = 0; i < NUM_CLERKS; i++) {
            clerks.add(new BankClerk(i, transactionPool, accounts));
        }
    }

    
    //Starts the simulation by running all clerks as threads and waiting for
    //them to process the transactions.
    public void startSimulation() {
        System.out.println("Starting simulation...");
        
        // Start all clerks
        for (BankClerk clerk : clerks) {
            clerk.start();
        }

        // Wait for the simulation to finish
        try {
            Thread.sleep(5000); // Sleep for 5 seconds to allow all threads to complete
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return; // Exit if interrupted
        }

        // Print final account balances
        System.out.println("\nFinal account balances:");
        accounts.entrySet().stream()
            .sorted(Map.Entry.comparingByKey()) // Sort accounts by account number
            .forEach(entry -> System.out.printf("Account %d: %.2f%n", 
                entry.getKey(), 
                entry.getValue().getBalance()));
    }

    //Main method to run the banking simulation.
    public static void main(String[] args) {
        Bank bank = new Bank(); // Create a new Bank instance
        bank.startSimulation(); // Start the simulation
    } 
}