import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;

public class Account {
    int userID;
    String firstName;
    String lastName;
    String password;
    float balance;

    // used for signing up user
    public Account(String firstName, String lastName, String password, float balance) {

        // get highest userID and set userID to that + 1
        try {
            this.userID = Integer.parseInt(FileUtility.readLineFromFile("accounts.txt", 1));
            this.userID++;
            FileUtility.changeLineInFile("accounts.txt", 1, String.valueOf(this.userID));
        } catch (NumberFormatException e) {
            e.getStackTrace();
        }

        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.balance = balance;
        this.balance = (float) (Math.floor(this.balance * 100) / 100);

        // write the Account objects data into a .txt file.
        FileUtility.writeNewUser(userID, firstName, lastName, password, balance);
    }

    public Account() {
        // used for creating initial account;
    }

    // used for logging in function
    public Account(int userID, String password) {
        this.userID = userID;
        this.password = password;

        // must read from file.
        this.firstName = FileUtility.readLineFromFile(userID + ".txt", 2);
        this.lastName = FileUtility.readLineFromFile(userID + ".txt", 3);
        this.balance = Float.parseFloat(FileUtility.readLineFromFile(userID + ".txt", 5));
        this.balance = (float) (Math.floor(this.balance * 100) / 100);
    }

    public String getTransactionList() {
        String transactionList = FileUtility.readFileIndex(userID + ".txt", 6);
        return transactionList;
    }

    public void depositFunds(float amount) {
        // For time stamping the transaction.
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();

        // Updating the balance from file
        updateReadBalance();

        System.out.println("Before Deposit: " + this.balance);
        this.balance += amount;
        this.balance = (float) (Math.floor(this.balance * 100) / 100);
        System.out.println("After Deposit: " + this.balance);

        // write the new updates into the .txt file
        updateWriteBalance(this.userID + ".txt", this.balance);
        FileUtility.writeToEndOfFile(this.userID + ".txt", "Deposit: " + amount + " Time: " + dtf.format(now));

    }

    private void updateReadBalance() {
        // read the balance from the file to get most accurate
        try {
            this.balance = Float.parseFloat(FileUtility.readLineFromFile(this.userID + ".txt", 5));
            this.balance = (float) (Math.floor(this.balance * 100) / 100);
        } catch (Exception e) {
            System.out.println("Could not grab correct balance");
        }
    }

    private void updateWriteBalance(String file, float newBalance) {
        // write a new balance to the .txt file
        FileUtility.changeLineInFile(file, 5, String.valueOf(newBalance));
    }

    public void withdrawFunds(float amount) {
        // For time stamping the transaction.
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();

        // truncating at 2 decimal places
        amount = (float) (Math.floor(amount * 100) / 100);
        updateReadBalance();
        this.balance = (float) (Math.floor(this.balance * 100) / 100);

        if (this.balance >= amount) {
            this.balance -= amount;

            updateWriteBalance(this.userID + ".txt", this.balance);

            FileUtility.writeToEndOfFile(this.userID + ".txt", "Withdrawal: -" + amount + " Time: " + dtf.format(now));
        } else {
            System.out.println("Not enough funds to withdraw");
        }

        
    }

    public float getBalance(int userID) {
        float x;

        // get balance of different user
        if (userID != this.userID) {
            try {
                x = Float.parseFloat(FileUtility.readLineFromFile(userID + ".txt", 5));
                x = (float) (Math.floor(x * 100) / 100);
                return x;
            } catch (Exception e) {
                System.out.println("Could not grab correct balance");
            }
        }
        return this.balance;
    }

    public void sendFunds(float amount, int benefiterID) {
        if (amount < 0) {
            System.out.println("Amount less than 0");
            return;
        }
        
        // time stamping transaction
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();

        amount = (float) (Math.floor(amount * 100) / 100);
        updateReadBalance();
        this.balance = (float) (Math.floor(this.balance * 100) / 100);

        if (!FileUtility.fileExists(benefiterID+".txt")) {
            System.out.println("UserID does not exist");
            return;
        }
        if (this.balance >= amount) {
            // clientID actions
            this.balance -= amount;
            updateWriteBalance(this.userID + ".txt", this.balance);
            FileUtility.writeToEndOfFile(this.userID + ".txt",
                    "Transfer: -" + amount + " Receiver: " + benefiterID + " Time: " + dtf.format(now));

            // benefiterID actions
            float benefiterBalance = getBalance(benefiterID);
            benefiterBalance += amount;
            benefiterBalance = (float) (Math.floor(benefiterBalance * 100) / 100);

            updateWriteBalance(benefiterID + ".txt", benefiterBalance);
            FileUtility.writeToEndOfFile(benefiterID + ".txt",
                    "Transfer: " + amount + " Sender: " + this.userID + " Time: " + dtf.format(now));
            System.out.println("Sent!");
        } else {
            System.out.println("Not enough funds");
        }

    }

}
