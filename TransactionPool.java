/**
 * The TransactionPool class manages a pool of transactions for bank clerks to process.
 * It provides methods to retrieve transactions, register clerks as active, 
 * and mark clerks as finished when they complete their work.
 * This class ensures thread safety when accessing and modifying the transaction pool
 * and the set of active clerks.
 */

import java.util.*;

public class TransactionPool {
    private final Queue<Transaction> transactions; // Queue holding the transactions to be processed
    private final Set<String> activeClerks; // Set tracking clerks currently working

    //Constructor initializes the transaction pool with a list of transactions.
    public TransactionPool(List<Transaction> transactions) {
        this.transactions = new LinkedList<>(transactions); // Use a linked list for efficient queue operations
        this.activeClerks = new HashSet<>(); // Track active clerks
    }

    //Retrieves a transaction from the pool. Returns null if there are no more transactions.
    public Transaction getTransaction() {
        synchronized(this) {
        	// Remove and return the head of the queue
            return transactions.isEmpty() ? null : transactions.poll();
        }
    }

    //Registers a clerk as active. Logs the clerk's start of work.
    public void registerClerk(String clerkName) {
        synchronized(this) {
            activeClerks.add(clerkName); // Add clerk to the active set
            System.out.printf("Clerk %s started working%n", clerkName);
        }
    }

    
    
     //Marks a clerk as finished and removes them from the active set.
    public void clerkFinished(String clerkName) {
        synchronized(this) {
            if (activeClerks.remove(clerkName)) { // Remove clerk if present
                System.out.printf("Clerk %s finished working%n", clerkName);
            }
        }
    }
}