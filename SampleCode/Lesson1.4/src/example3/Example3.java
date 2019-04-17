package example3;

import example2.*;

public class Example3 {

    public static void main(String[] args) {

        BankAccount b = new BankAccount(100_00);
        BankAccount s = new SavingsAccount(200_00, 20);
        BankAccount c = new CheckingAccount(300_00);

        printAccounts(b, s, c);

        // s.addInterest(); this does not work!
        SavingsAccount s1 = (SavingsAccount) s;
        s1.addInterest();

        System.out.println();
        printAccounts(b, s, c);

        // Inline cast:
        c.deposit(10_00);
        c.deposit(10_00);
        c.deposit(10_00);
        c.deposit(10_00);
        System.out.println();
        printAccounts(b, s, c);

        ((CheckingAccount) c).applyFees();

        System.out.println();
        printAccounts(b, s, c);

        b.transfer(c, 30_00);

        System.out.println();
        printAccounts(b, s, c);

    }

    private static void printAccounts(BankAccount b1, BankAccount b2, BankAccount b3) {

        System.out.println(b1.getClass().getSimpleName() + ":\t\t" + b1);
        System.out.println(b2.getClass().getSimpleName() + ":\t\t" + b2);
        System.out.println(b3.getClass().getSimpleName() + ":\t" + b3);

    }

}
