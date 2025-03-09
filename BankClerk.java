/**
 * The BankClerk class represents a bank clerk working as a separate thread.
 * Each clerk retrieves transactions from the TransactionPool, processes them on the
 * appropriate BankAccount, and waits a random time between transactions.
 * Clerks stop working once there are no more transactions in the pool.
 */

import java.util.Map;
import java.util.Random;

public class BankClerk extends Thread {
    private final TransactionPool pool; // Reference to the pool of transactions
    private final Map<Integer, BankAccount> accounts; // Map of account numbers to BankAccount objects
    private final Random random; // Random generator for simulating processing delays


    //Constructor initializes the clerk with an ID, a reference to the transaction pool,
    //and a map of accounts.
    public BankClerk(int id, TransactionPool pool, Map<Integer, BankAccount> accounts) {
        super("Clerk-" + id); // Set the thread name to "Clerk-ID"
        this.pool = pool;
        this.accounts = accounts;
        this.random = new Random();
    }

    /**
     * The run method executes the clerk's work.
     * Clerks register themselves, process transactions from the pool,
     * and simulate a delay between transactions. When no transactions remain,
     * the clerk stops working.
     */
    public void run() {
        pool.registerClerk(getName()); // Register the clerk at the start     
        try {
            while (!Thread.currentThread().isInterrupted()) {
                Transaction transaction = pool.getTransaction(); // Retrieve the next transaction
                if (transaction == null) break; // Exit if no transactions are left
 
                BankAccount account = accounts.get(transaction.getAccountNumber());
                if (account != null) {
                    account.transaction(transaction); // Process the transaction on the account
                    Thread.sleep(random.nextInt(100));  // Simulate a short delay
                }
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt(); // Restore interrupted status and exit gracefully
        } finally {
            pool.clerkFinished(getName()); // Mark the clerk as finished
        }
    }
}