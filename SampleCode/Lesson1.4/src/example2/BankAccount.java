package example2;

public class BankAccount {

    private long balance; // MONEY SHOULD NEVER USE FLOATING POINT!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!

    public BankAccount() {
        this.balance = 0;
    }

    public BankAccount(long initialBalance) {
        this.balance = initialBalance;
    }

    public void deposit(long amount) {
        this.balance += amount;
    }

    public void withdraw(long amount) {
        this.balance -= amount;
    }

    public void transfer(BankAccount dest, long amount) {
        withdraw(amount);
        dest.deposit(amount);
    }

    public long getBalance() {
        return this.balance;
    }

    @Override
    public String toString() {
        double dollars = balance / 100.0;
        return String.format("Balance: $%.2f", dollars);
    }
}
