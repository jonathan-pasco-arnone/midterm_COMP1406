public class Bank {
    private String name;
    private BankAccount[] bankAccounts = new BankAccount[10];

    public Bank(String name) {
        this.name = name;
    }

    public String toString() {
        double sum = 0;
        for (BankAccount account : bankAccounts) {
            if (account == null) {
                break;
            }
            sum += account.getBalance();
        }
        return "Name: " + name + "\nTotal balances: $ " + sum;
    }

    public boolean addAccount(BankAccount account) {
        int counter = 0;
        boolean added = false;
        while (counter < bankAccounts.length) {

            if (bankAccounts[counter] == null) {
                bankAccounts[counter] = account;
                bankAccounts[counter].setInstitution(this);
                added = true;
                break;
            }
            if (account.getAccountNumber() == bankAccounts[counter].getAccountNumber()) {
                break;
            }

            counter++;
        }
        return added;
    }

    public double getLargestBalance() {
        double largestBalance;

        // Checks if the initial value exists
        if (bankAccounts[0] != null) {
            largestBalance = bankAccounts[0].getBalance();
            for (BankAccount account : bankAccounts) {
                if (account == null) {
                    break;
                }
                if (account.getBalance() > largestBalance) {
                    largestBalance = account.getBalance();
                }
            }
        } else {
            largestBalance = -1;
        }
        return largestBalance;
    }

    public void performMonthlyUpkeep() {
        for(BankAccount account : bankAccounts) {
            if (account == null) {
                break;
            }
            account.monthlyUpkeep();
        }
    }

    public boolean transfer(int source, int destination, double amount) {
        int sourceIndex = -1;
        int destinationIndex = -1;
        int counter = 0;
        boolean transactionCompleted = false;

        // If the amount is negative or if the transfer is with the same ID
        if (amount < 0 || source == destination) {
            return transactionCompleted;
        }

        while (counter < bankAccounts.length) {
            if (bankAccounts[counter] == null) {
                break;
            }
            if (bankAccounts[counter].getAccountNumber() == source) {
                sourceIndex = counter;

                // If the source balance is too small
                if (bankAccounts[counter].getBalance() < amount) {
                    break;
                }
            } else if (bankAccounts[counter].getAccountNumber() == destination) {
                destinationIndex = counter;
            }

            // Completes the transaction
            if (sourceIndex != -1 && destinationIndex != -1) {
                bankAccounts[sourceIndex].withdraw(amount);
                bankAccounts[destinationIndex].deposit(amount);
                transactionCompleted = true;
                break;
            }

            counter++;
        }
        return transactionCompleted;
    }

    public static void main(String[] args) {
        // Instantiating variables
        Bank bank1 = new Bank("Scotiabank");
        Bank bank2 = new Bank("TD");

        /*
        * The 2nd parameter is the same as the bank that I will add it to later on, however it does not matter what
        * I put into this parameter as it will be set once I add it to a bank anyway
        */
        ChequingAccount myCheq1 = new ChequingAccount("Jonathan", bank1, 375345);
        ChequingAccount myCheq2 = new ChequingAccount("Albert", bank1, 290736);
        SavingsAccount mySav1 = new SavingsAccount("Juan", bank1,694344);
        SavingsAccount mySav2 = new SavingsAccount("I love cheese", bank1, 3923988);

        ChequingAccount myCheq3 = new ChequingAccount("Rodney", bank2, 107562);
        SavingsAccount mySav3 = new SavingsAccount("Olivia", bank2, 372035);

        // Adding accounts
        bank1.addAccount(myCheq1);
        bank1.addAccount(myCheq2);
        bank1.addAccount(mySav1);
        bank1.addAccount(mySav2);

        bank2.addAccount(myCheq3);
        bank2.addAccount(mySav3);

        // Adding money
        myCheq1.deposit(700);
        myCheq2.deposit(30);
        myCheq3.deposit(5001);

        mySav1.deposit(5);
        mySav2.deposit(15);
        mySav3.deposit(100);

        // Testing toString
        System.out.println("\n" + bank1.toString());
        System.out.println("\n" + bank2.toString());

        // Largest account balances
        System.out.println("\nBank1's largest balance is: " + bank1.getLargestBalance()
                + "\nBank2's largest balance is: " + bank2.getLargestBalance());

        // Monthly keep up
        System.out.println("\nJonathan's chequing account balance BEFORE monthly keep up: " + myCheq1.getBalance());
        System.out.println("Olivia's savings account balance BEFORE monthly keep up: " + mySav3.getBalance());
        bank1.performMonthlyUpkeep();
        bank2.performMonthlyUpkeep();
        System.out.println("Jonathan's chequing account balance AFTER monthly keep up (should be $1 less): " + myCheq1.getBalance());
        System.out.println("Olivia's savings account balance After monthly keep up (should be 1% higher): " + mySav3.getBalance());

        // Initializing extra accounts to check for add account errors
        ChequingAccount myCheq4 = new ChequingAccount("Ava", bank1, 709243);
        ChequingAccount myCheq5 = new ChequingAccount("Alex", bank1, 732323);
        ChequingAccount myCheq6 = new ChequingAccount("Mariam", bank1, 926457);
        ChequingAccount myCheq7 = new ChequingAccount("Jean", bank1, 280467);
        ChequingAccount myCheq8 = new ChequingAccount("Sarah", bank1, 936483);
        ChequingAccount myCheq9 = new ChequingAccount("Pam", bank1, 555839);
        ChequingAccount myCheq10 = new ChequingAccount("Trent", bank1, 867222);

        System.out.println("\nAttempting to add the 5th account (should be true): " + bank1.addAccount(myCheq4));
        System.out.println("Attempting to add the 6th account (should be true): " + bank1.addAccount(myCheq5));
        System.out.println("Attempting to add the 7th account (should be true): " + bank1.addAccount(myCheq6));
        System.out.println("Attempting to add the 8th account (should be true): " + bank1.addAccount(myCheq7));
        System.out.println("Attempting to add the 9th account (should be true): " + bank1.addAccount(myCheq8));
        System.out.println("Attempting to add the 10th account (should be true): " + bank1.addAccount(myCheq9));
        System.out.println("Attempting to add the 11th account (should be false): " + bank1.addAccount(myCheq10));

        // Attempting to add an account with identical ID to the same bank
        ChequingAccount mySav4 = new ChequingAccount("My Man", bank2, 107562); // Rodney's ID is identical
        System.out.println("\nAttempting to add an account with identical ID to the same bank (should be false): " + bank1.addAccount(myCheq10));

        /*
        *
        * Transaction tests
        *
        * */

        // Transaction test #1
        System.out.println("\nOlivia's balance before transaction: " + mySav3.getBalance());
        System.out.println("Rodney's balance before transaction: " + myCheq3.getBalance());

        System.out.println("Attempting to move $20 from Olivia's account to Rodney's (should be true): "
                + bank2.transfer(mySav3.getAccountNumber(), myCheq3.getAccountNumber(), 20));

        System.out.println("Olivia's balance after transaction (should be $20 less and $1 less for withdrawal fee): "
                + mySav3.getBalance());
        System.out.println("Rodney's balance after transaction (should be $20 more): " + myCheq3.getBalance());

        // Transaction test #2
        System.out.println("\nOlivia's balance before transaction: " + mySav3.getBalance());
        System.out.println("Rodney's balance before transaction: " + myCheq3.getBalance());

        System.out.println("Attempting to transfer a negative amount of money (should be false): "
                + bank2.transfer(mySav3.getAccountNumber(), myCheq3.getAccountNumber(), -5));


        System.out.println("Olivia's balance after transaction (should be the same): " + mySav3.getBalance());
        System.out.println("Rodney's balance after transaction (should be the same): " + myCheq3.getBalance());

        // Transaction test #3
        System.out.println("\nAlbert's balance before transaction: " + myCheq2.getBalance());
        System.out.println("Jonathan's balance before transaction: " + myCheq1.getBalance());

        System.out.println("Attempting to transfer $100000, more money than available (should be false): "
                + bank1.transfer(myCheq2.getAccountNumber(), myCheq1.getAccountNumber(), 100000));

        System.out.println("Albert's balance after transaction (should be the same): " + myCheq2.getBalance());
        System.out.println("Jonathan's balance after transaction (should be the same): " + myCheq1.getBalance());

        // Transaction test #4
        System.out.println("\nAlbert's balance before transaction: " + myCheq2.getBalance());

        System.out.println("Attempting to transfer money from Albert to Albert (should be false): "
                + bank1.transfer(myCheq2.getAccountNumber(), myCheq2.getAccountNumber(), 100000));

        System.out.println("Albert's balance after transaction (should be the same): " + myCheq2.getBalance());

        // Transaction test #5
        System.out.println("\nAlbert's balance before transaction: " + myCheq2.getBalance());
        System.out.println("Olivia's balance before transaction: " + mySav3.getBalance());


        System.out.println("Attempting to transfer $5 from Albert to Olivia who is at another bank (should be false): "
                + bank1.transfer(myCheq2.getAccountNumber(), mySav3.getAccountNumber(), 5));

        System.out.println("Albert's balance after transaction (should be the same): " + myCheq2.getBalance());
        System.out.println("Olivia's balance after transaction (should be the same): " + mySav3.getBalance());


    }
}