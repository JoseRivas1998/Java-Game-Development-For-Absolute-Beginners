package example2;

public class Example2 {

    public static void main(String[] args) {
        BankAccount b1 = new BankAccount(1000_98);
        System.out.println(b1);

        BankAccount b2 = new BankAccount(40_10);
        System.out.println(b2);
        b2.deposit(30_00);
        System.out.println(b2);
        b2.withdraw(5_50);
        System.out.println(b2);

        b1.transfer(b2, 100_98);
        System.out.println(b1);
        System.out.println(b2);

        SavingsAccount s1 = new SavingsAccount(500_00, 50);
        System.out.println(s1);
        s1.addInterest();
        System.out.println(s1);
        s1.addInterest();
        System.out.println(s1);
        s1.addInterest();
        System.out.println(s1);

        CheckingAccount c1 = new CheckingAccount(100_00);
        System.out.println(c1);
        c1.deposit(10_00);
        c1.withdraw(20_00);
        c1.withdraw(30_00);
        c1.deposit(50_00);
        c1.deposit(40_00);
        c1.withdraw(25_00);
        System.out.println(c1);
        c1.applyFees();
        System.out.println(c1);

    }

}
