package example2;

public class CheckingAccount extends BankAccount {

    private static final long TRANSACTION_FEE = 2_00; // This is a constant, and a class variable, not an instance variable
    private static final int FREE_TRANSACTIONS = 3;

    // This is an instance variable
    private int transactions;

    public CheckingAccount() {
        super();
        transactions = 0;
    }

    public CheckingAccount(long initialBalance) {
        super(initialBalance);
        transactions = 0;
    }

    @Override
    public void deposit(long amount) {
        transactions++;
        super.deposit(amount);
    }

    @Override
    public void withdraw(long amount) {
        transactions++;
        super.withdraw(amount);
    }

    @Override
    public void transfer(BankAccount dest, long amount) {
        transactions++;
        super.transfer(dest, amount);
    }

    public void applyFees() {
        if(transactions > FREE_TRANSACTIONS) {
            long fee = TRANSACTION_FEE * (transactions - FREE_TRANSACTIONS);
            super.withdraw(fee); // We want to use super, because this is not a transaction!
            transactions = 0;
        }
    }

}
