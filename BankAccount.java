/**
 * The BankAccount class represents a bank account with account number and balance.
 * It provides methods to perform transactions (deposits and withdrawals) and to retrieve the current balance.
 * The class ensures thread safety by synchronizing access to the balance and prevents negative balances by
 * making threads wait until sufficient funds are available.
 */
public class BankAccount {
    private final int accountNumber;
    private double balance;
    private final Object lock = new Object(); // Lock object for synchronization

    //Constructor initializes the account with a unique number and the balance.
    public BankAccount(int accountNumber) {
        this.accountNumber = accountNumber;
        this.balance = 0;
    }
    
    
    /**
     * Handles a transaction (deposit or withdrawal) on the account.
     * Deposits are performed immediately, while withdrawals that would cause
     * a negative balance wait until sufficient funds are available.
     */
    public void transaction(Transaction transaction) {
        synchronized(lock) {
        	// Handling withdrawals
            if (transaction.getAmount() < 0) { 
            	// Print if withdrawal cannot be performed immediately
                if (balance + transaction.getAmount() < 0) {
                    System.out.printf("%s is stuck trying to withdraw %.1f from account %d (balance: %.1f)%n",
                        Thread.currentThread().getName(),
                        -transaction.getAmount(),
                        accountNumber,
                        balance);
                }
                
                //Wait until there is enough money.
                while (balance + transaction.getAmount() < 0) {
                    try {
                        lock.wait();  // Release lock and wait
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt(); // Restore interrupted status
                        return;
                    }
                }
            }
            
            // Perform the transaction
            balance += transaction.getAmount();
            System.out.printf("%s processed: Account %d amount: %.1f (New balance: %.1f)%n",
                Thread.currentThread().getName(),
                accountNumber,
                transaction.getAmount(),
                balance);
            
            // Notify waiting threads that the balance has changed
            lock.notifyAll();
        }
    }

    //Retrieves the current balance of the account.
    public double getBalance() {
        synchronized(lock) {
            return balance;
        }
    }
}