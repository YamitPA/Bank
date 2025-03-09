/**
 * The Transaction class represents a single banking transaction.
 * Each transaction specifies an account number and an amount to deposit or withdraw.
 * A positive amount indicates a deposit, while a negative amount indicates a withdrawal.
 */

public class Transaction {
    private int accountNumber; 
    private double amount;

    //Constructor initializes a transaction with the account number and  the amount.
    public Transaction(int accountNumber, double amount) {
        this.accountNumber = accountNumber;
        this.amount = amount;
    }

    //Returns the account number associated with this transaction.
    public int getAccountNumber() {
    	return accountNumber; 
    }
    
    //Returns the amount of the transaction.
    public double getAmount() {
    	return amount;
    }
}