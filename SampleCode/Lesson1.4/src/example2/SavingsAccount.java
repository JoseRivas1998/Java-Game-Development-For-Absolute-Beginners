package example2;

public class SavingsAccount extends BankAccount {

    private double interestRate;

    // Notice no default constructor, this is ok!

    public SavingsAccount(double interestRate) {
        super();
        this.interestRate  = interestRate;
    }

    public SavingsAccount(long initialBalance, double interestRate) {
        super(initialBalance); // Call the constructor of the super class
        this.interestRate = interestRate;
    }


    public void addInterest() {
        long interest = (long) (getBalance() * (interestRate / 100.0));
        deposit(interest);
    }

    @Override
    public String toString() {
        return "Interest rate: " + this.interestRate + "% " + super.toString();
    }
}
