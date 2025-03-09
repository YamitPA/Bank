# Multi-Threaded Banking Simulation

## Description
This Java application simulates a multi-threaded banking system with bank accounts, clerks, and a transaction pool. The system demonstrates concurrent transaction processing with thread synchronization to ensure data integrity. Key features include:

- **Bank Accounts**: 5 accounts (numbered 0-4) initialized with a zero balance, supporting deposits (positive amounts) and withdrawals (negative amounts).
- **Transaction Pool**: A queue of 50 random transactions, each specifying an account number and an amount between -1000 and 1000.
- **Bank Clerks**: 10 concurrent threads that process transactions from the pool, with random delays (up to 100ms) between actions.
- **Thread Safety**: Prevents negative balances by making clerks wait for sufficient funds using `wait()` and `notifyAll()`.

The simulation runs for 5 seconds, after which the final balances of all accounts are printed, sorted by account number.

## Project Structure
The project uses Object-Oriented Programming (OOP) and multi-threading with the following classes:
- `Bank`: Initializes accounts, generates transactions, and manages clerks; serves as the main simulation driver.
- `BankAccount`: Represents an account with thread-safe transaction processing and balance management.
- `Transaction`: Encapsulates a single transaction with an account number and amount.
- `TransactionPool`: Manages a thread-safe queue of transactions and tracks active clerks.
- `BankClerk`: A thread that retrieves and processes transactions, simulating clerk work.
